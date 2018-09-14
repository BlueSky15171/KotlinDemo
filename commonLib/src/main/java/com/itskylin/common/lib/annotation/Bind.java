package com.itskylin.common.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author BlueSKy
 * @version V1.0
 * @Title: ${FILE_NAME}
 * @Package com.itskylin.android.app.blueskyim.utils.annotation
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @email admin@itksylin.com
 * @date 3/2/2016 23:16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bind {
    int value();
}
