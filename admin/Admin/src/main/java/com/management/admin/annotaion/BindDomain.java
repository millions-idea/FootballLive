/***
 * @pName proback
 * @name FinanceToken
 * @user DF
 * @date 2018/8/5
 * @desc
 */
package com.management.admin.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 绑定域名
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BindDomain {

}
