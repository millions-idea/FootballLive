/***
 * @pName management
 * @name ExceptionService
 * @user HongWei
 * @date 2018/8/13
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.Exceptions;

/**
 * 日志上报逻辑接口  2018年8月13日13:17:05
 */
public interface IExceptionService extends IBaseService<Exceptions> {
    /**
     * 异步插入记录  2018年8月13日13:33:29
     * @param exceptions
     * @return
     */
    void asyncInsert(Exceptions exceptions);
}
