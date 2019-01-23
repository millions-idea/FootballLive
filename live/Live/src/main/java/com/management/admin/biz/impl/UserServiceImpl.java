/***
 * @pName Live
 * @name UserServiceImpl
 * @user HongWei
 * @date 2019/1/23
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IUserService;
import com.management.admin.entity.db.User;
import com.management.admin.repository.UserMapper;
import com.management.admin.utils.JsonUtil;
import com.management.admin.utils.MD5Util;
import com.management.admin.utils.PhoneUtil;
import com.management.admin.utils.http.NeteaseImUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements IUserService{
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 登录验证 DF 2018年11月29日02:30:18
     *
     * @param phone    1500000000 必填
     * @param password 限制6-32位字母+数字 MD5(MD5(salt + password)) 必填
     * @return
     */
    @Override
    @Transactional
    public User login(String phone, String password) {
        User user = getUser(phone, password);
        if(user != null){
            // 更新数据库信息
            user.setEditDate(new Date());
            if(userMapper.updateByPrimaryKey(user) <= 0) logger.error("UserServiceImpl_login()_登录验证_更新最后一次登录时间失败");
        }
        return user;
    }

    /**
     * 通过用户名验证 DF 2019年1月23日15:27:23
     *
     * @param username
     * @return
     */
    @Override
    public User loginByUname(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 获取用户档案 DF 2019年1月23日23:23:16
     *
     * @param userId
     * @return
     */
    @Override
    public User getProfile(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user != null){
            if(user.getPhoto() == null || user.getPhoto().length() == 0) user.setPhoto("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
            user.setPhone(PhoneUtil.getEncrypt(user.getPhone()));
        }
        return user;
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

}
