package com.itskylin.common.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sky Lin,QQ:345066543
 * @version 1.0
 * @Package com.itskylin.android.budejie.utils.annotation
 * @Description:
 * @email: admin@itskylin.com
 * @date 2016.6.22 16:20
 */
@SuppressWarnings("all")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindLayout {
	int value() default 0;
}
