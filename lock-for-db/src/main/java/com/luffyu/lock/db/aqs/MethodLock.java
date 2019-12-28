package com.luffyu.lock.db.aqs;

import java.lang.annotation.*;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-28 16:36
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface MethodLock {

}
