/***
 * @pName Admin
 * @name UserServiceImpl
 * @user HongWei
 * @date 2018/11/29
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IUserService;
import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.User;
import com.management.admin.entity.enums.UserRoleEnum;
import com.management.admin.entity.resp.NASignIn;
import com.management.admin.entity.resp.UserInfo;
import com.management.admin.entity.template.Constant;
import com.management.admin.exception.InfoException;
import com.management.admin.repository.AdminUserMapper;
import com.management.admin.repository.UserMapper;
import com.management.admin.repository.utils.ConditionUtil;
import com.management.admin.utils.*;
import com.management.admin.utils.http.NeteaseImUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 用户业务接口实现类 DF 2018年11月29日00:49:10
 */
@Service
public class UserServiceImpl implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final AdminUserMapper adminUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, AdminUserMapper adminUserMapper) {
        this.userMapper = userMapper;
        this.adminUserMapper = adminUserMapper;
    }

    /**
     * 添加新用户 DF 2018年11月29日00:47:48
     *
     * @param phone    1500000000 必填
     * @param password 限制6-32位字母+数字 MD5(MD5(salt + password)) 必填
     * @param smsCode  限制6位字母+数字 必填
     * @param ip
     * @return 事务提交失败会抛出MsgException异常, 成功返回用户信息
     */
    @Override
    public UserInfo addUser(String phone, String password, String smsCode, String ip) {
        // 校验短信验证码是否正确或过期
        String dbSmsCode = (String) redisTemplate.opsForValue().get("sms-" + phone);
        if(dbSmsCode == null || !dbSmsCode.equalsIgnoreCase(smsCode)) throw new InfoException("短信验证码不正确");

        // 加盐加密用户数据
        String encryptPassword = MD5Util.md5(MD5Util.md5(phone + password));

        // 提交数据库事务
        User user = new User();
        user.setPhone(phone);
        user.setPassword(encryptPassword);
        user.setNickName(PhoneUtil.getEncrypt(phone));
        user.setAddDate(new Date());
        user.setIp(ip);
        user.setType(0);

        // 完善平台用户数据
        boolean result = userMapper.insert(user) > 0;
        if(!result) throw new InfoException("注册失败");

        // 同步网易云信数据
        String response = NeteaseImUtil.post("nimserver/user/create.action", "accid=" + phone);
        NASignIn model = JsonUtil.getModel(response, NASignIn.class);
        if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        example.and(criteria);
        User userInfo = userMapper.selectOneByExample(example);

        userInfo.setCloudAccid(model.getInfo().getAccId());
        userInfo.setCloudToken(model.getInfo().getToken());

        result =  userMapper.updateByPrimaryKey(userInfo) > 0;
        if(!result) throw new InfoException("补全云端信息失败");

        // 添加系统账户好友
        response = NeteaseImUtil.post("nimserver/friend/add.action", "accid=" + phone + "&faccid=" + Constant.HotAccId + "&type=1");
        model = JsonUtil.getModel(response, NASignIn.class);
        if (!model.getCode().equals(200)) throw new InfoException("补全好友信息失败");

        // 注册成功
        UserInfo userSampleInfo = new UserInfo();
        PropertyUtil.clone(userInfo, userSampleInfo);

        return userSampleInfo;
    }

    /**
     * 编辑用户档案 DF 2018年11月29日01:48:55
     *
     * @param uid       用户id
     * @param photo     用户头像 默认constant/defaultProfilePhoto.png 选填
     * @param nickName  用户昵称(同编码) 选填
     * @param signature 个性签名 选填
     */
    @Override
    public void editProfile(Integer uid, String photo,String nickName, String signature) {
        if(photo.isEmpty()) photo = "images/head-default.png";

        User userInfo = userMapper.selectByPrimaryKey(uid);

        userInfo.setUserId(uid);
        userInfo.setPhoto(photo);
        userInfo.setNickName(nickName);
        userInfo.setSignature(signature);

        boolean result = userMapper.updateBasicInfo(userInfo) > 0;

        if(!result) throw new InfoException("设置失败");

        // 更新云信数据库
        String response = NeteaseImUtil.post("nimserver/user/updateUinfo.action",
                "accid=" + userInfo.getPhone() + "&name=" + userInfo.getNickName() + "&icon=" + userInfo.getPhoto() + "&sign=" + userInfo.getSignature());
        NASignIn model = JsonUtil.getModel(response, NASignIn.class);
        if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");
    }

    /**
     * 登录验证 DF 2018年11月29日02:30:18
     *
     * @param phone 1500000000 必填
     * @param password 限制6-32位字母+数字 MD5(MD5(salt + password)) 必填
     * @return
     */
    @Override
    public User login(String phone, String password) {
        User user = getUser(phone, password);
        if(user != null){
            // 更新并获取新token
            String response = NeteaseImUtil.post("nimserver/user/refreshToken.action", "accid=" + user.getPhone());
            NASignIn model = JsonUtil.getModel(response, NASignIn.class);
            if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");

            // 更新数据库信息
            user.setEditDate(new Date());
            user.setCloudToken(model.getInfo().getToken());
            if(userMapper.updateByPrimaryKey(user) <= 0) logger.error("UserServiceImpl_login()_登录验证_更新最后一次登录时间失败");
        }
        return user;
    }

    /**
     * 重置密码 DF 2018年12月7日02:09:17
     *
     * @param phone
     * @param password
     * @param smsCode
     * @return
     */
    @Override
    public Boolean resetPassword(String phone, String password, String smsCode) {
        // 校验短信验证码是否正确或过期
        String dbSmsCode = (String) redisTemplate.opsForValue().get("sms-" + phone);
        if(dbSmsCode == null || !dbSmsCode.equalsIgnoreCase(smsCode)) throw new InfoException("短信验证码不正确");

        // 加盐加密用户数据
        String encryptPassword = MD5Util.md5(MD5Util.md5(phone + password));

        // 更新数据库
        Boolean result = userMapper.resetPassword(phone, encryptPassword) > 0;

        // 更新云信数据库
        String response = NeteaseImUtil.post("nimserver/user/refreshToken.action", "accid=" + phone);
        NASignIn model = JsonUtil.getModel(response, NASignIn.class);
        if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");

        // 同步本地数据库
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        example.and(criteria);
        User user = new User();
        user.setCloudToken(model.getInfo().getToken());
        Boolean updateResult = userMapper.updateByExample(user, example) > 0;
        if(!updateResult) throw new InfoException("同步本地数据失败");

        return result;
    }


    /**
     * 更新个人名片 DF 2018年12月11日04:26:20
     *
     * @param phone     手机号 必填
     * @param nickname  昵称 选填
     * @param signature 个性签名 选填
     * @return
     */
    @Override
    public Boolean updateInfo(String phone, String nickname, String signature) {
        StringBuffer buffer = new StringBuffer();
        if(nickname != null && !nickname.isEmpty()) buffer.append("nick_name=#{nickname}");
        if(signature != null && !signature.isEmpty()) buffer.append("signature=#{signature}");
        Boolean result = userMapper.updateInfo(phone, buffer.toString(), nickname, signature) > 0;

        //同步云端数据
        if(result){
            Map<String, String> params = new HashMap<>();
            if(nickname != null && !nickname.isEmpty()) params.put("name", nickname);
            if(signature != null && !signature.isEmpty()) params.put("sign", signature);
            if(phone != null && !phone.isEmpty()) params.put("mobile", phone);

            String data = "accid=" + phone + "&";
            for(Map.Entry<String, String> entry : params.entrySet())
            {
                data += entry.getKey() + "=" + entry.getValue() + "&";
            }
            data = data.substring(0, data.length() - 1);

            String response = NeteaseImUtil.post("nimserver/user/updateUinfo.action", data);
            NASignIn model = JsonUtil.getModel(response, NASignIn.class);
            if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");
        }
        return result;
    }

    /**
     * 更新个人头像 DF 2018年12月11日06:51:56
     *
     * @param phone
     * @param upload
     * @return
     */
    @Override
    public Boolean updatePhoto(String phone, String upload) {
        return userMapper.updatePhoto(phone, upload) > 0;
    }

    /**
     * 查询用户资料 DF 2018年12月11日15:43:48
     *
     * @param accid
     * @return
     */
    @Override
    public List<UserInfo> getUserInfo(String accid) {
        return userMapper.selectByAccid(accid);
    }

    /**
     * 员工登录验证--用户名 DF 2018年12月16日16:02:52
     *
     * @param username
     * @return
     */
    @Override
    public AdminUser staffLoginByUname(String username) {
        return adminUserMapper.selectByUsername(username);
    }

    /**
     * 注销登录 DF 2018年12月16日17:58:26
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        Map<String, String> map = TokenUtil.validate(token);
        if(!map.isEmpty()){
            String key  = "token:" + MD5Util.encrypt16(map.get("phone") + map.get("userId"));
            redisTemplate.delete(key);
        }
    }


    /**
     * 员工账号登录验证 DF 2018年12月16日15:52:42
     * @param username 1000000000 必填
     * @param password 限制6-32位字母+数字 MD5(MD5(salt + password)) 必填
     * @return
     */
    @Override
    public UserInfo staffLogin(String username, String password) {
        String newPassword = MD5Util.md5(MD5Util.md5(username + password));
        UserInfo userInfo = adminUserMapper.selectStaff(username, newPassword);
        if(userInfo != null){
            // 更新数据库信息
            if(userMapper.updateLastLoginTime(userInfo.getUserId()) <= 0) logger.error("UserServiceImpl_login()_登录验证_更新最后一次登录时间失败");
        }
        return userInfo;
    }

    /**
     * 获取用户信息 DF 2018年12月16日15:54:35
     * @param username
     * @param password
     * @return
     */
    private User getUser(String username, String password) {
        String newPassword = MD5Util.md5(MD5Util.md5(username + password));

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", username);
        criteria.andEqualTo("password", newPassword);
        example.and(criteria);
        return userMapper.selectOneByExample(example);
    }


    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     * @param page
     * @param limit
     * @param condition
     * @param userRole
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<User> getLimit(Integer page, String limit, String condition, UserRoleEnum userRole, Integer state, String beginTime, String endTime) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition, userRole,state, beginTime, endTime);
        List<User> list = userMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    @Override
    public List<AdminUser> getAdminLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition, UserRoleEnum.SuperAdmin, state, beginTime, endTime);
        List<AdminUser> list = adminUserMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return userMapper.selectCount(new User());
    }

    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     * @param condition
     * @param userRole
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Integer getLimitCount(String condition, UserRoleEnum userRole, Integer state, String beginTime, String endTime) {
        String where = extractLimitWhere(condition, userRole, state, beginTime, endTime);
        return userMapper.selectLimitCount(state, beginTime, endTime, where);
    }

    /**
     * 分页加载管理员信息列表总数 DF 2018年12月17日14:43:053
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Integer getAdminLimitCount(String condition,Integer state, String beginTime, String endTime) {
        String where = extractLimitWhere(condition, UserRoleEnum.SuperAdmin, state, beginTime, endTime);
        return adminUserMapper.selectLimitCount(state, beginTime, endTime, where);
    }

    /**
     * 获取用户信息--用户id DF 2018年12月16日19:11:09
     *
     * @param userId
     * @return
     */
    @Override
    public User getUserInfoById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 查询管理员用户表记录总数 DF 2018-12-17 14:43:462
     *
     * @return
     */
    @Override
    public Integer getAdminCount() {
        return adminUserMapper.selectCount(new AdminUser());
    }

    /**
     * 获取管理员用户信息--用户id DF 2018年12月17日14:45:30
     *
     * @param userId
     * @return
     */
    @Override
    public AdminUser getAdminUserInfoById(Integer userId) {
        return adminUserMapper.selectByPrimaryKey(userId);
    }

    /**
     * 提取分页条件
     * @return
     */
    private String extractLimitWhere(String condition, UserRoleEnum userRole, Integer isEnable,  String beginTime, String endTime) {
        // 查询模糊条件
        String where = " 1=1";
        if(condition != null) {
            condition = condition.trim();
            where += " AND (" + ConditionUtil.like("user_id", condition, true, "t1");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("add_date", condition, true, "t1");
                where += " OR " + ConditionUtil.like("update_date", condition, true, "t1");
            }
            where += " OR " + ConditionUtil.like("nick_name", condition, true, "t1");
            where += " OR " + ConditionUtil.like("phone", condition, true, "t1");
            where += " OR " + ConditionUtil.like("ip", condition, true, "t1") + ")";
        }

        if(userRole.equals(UserRoleEnum.SuperAdmin)){
            where +=" AND (" +ConditionUtil.match("type", "1", true, "t1");
            where +=" OR " +ConditionUtil.match("type", "2", true, "t1") + ")";
        }else{
            where +=" AND " +ConditionUtil.match("type", userRole.ordinal() + "", true, "t1");
        }

        // 取两个日期之间或查询指定日期
        where = extractBetweenTime(beginTime, endTime, where);
        return where.trim();
    }


    /**
     * 提取两个日期之间的条件
     * @return
     */
    private String extractBetweenTime(String beginTime, String endTime, String where) {
        if ((beginTime != null && beginTime.contains("-")) &&
                endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (beginTime != null && beginTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }
        return where;
    }


    /**
     * 查询全部数据或者只有一类数据
     * @return
     */
    private String extractQueryAllOrOne(Integer isEnable, String where) {
        if (isEnable != null && isEnable != 0){
            where += " AND t1.is_enable = #{isEnable}";
        }
        return where;
    }
}
