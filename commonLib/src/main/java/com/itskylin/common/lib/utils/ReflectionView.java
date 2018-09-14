package com.itskylin.common.lib.utils;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;

import com.itskylin.common.lib.R;
import com.itskylin.common.lib.annotation.Bind;
import com.itskylin.common.lib.annotation.BindLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author BlueSKy
 * @version V1.0
 * @Title: ${FILE_NAME}
 * @Package com.itskylin.android.app.blueskyim.utils
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @email admin@itksylin.com
 * @date 3/2/2016 23:20
 */
@SuppressWarnings("all")
public class ReflectionView {
	/**
	 * 自动加载findViewById
	 *
	 * @param activity
	 */
	public static void initView(@Nullable Activity activity) {
		initView(activity, null);
	}

	public static void initView(@Nullable Activity activity, @Nullable View view) {
		Class<? extends Activity> cls = activity.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Class clsType = field.getType();
			if (View.class.isAssignableFrom(clsType)) {
				try {
					// 调试打印
					// LogUtils.d(ReflectionView.class.getSimpleName(),"View:" +
					// clsType.getName() + ":" + field.getName());
					Method method = cls.getMethod("findViewById", int.class);
					Bind annotation = field.getAnnotation(Bind.class);
					Object value;
					if (annotation != null) {
						value = annotation.value();
					} else {
						Class idCls = R.id.class;
						String fieldName = field.getName();
						Field idField = idCls.getDeclaredField(fieldName);
						value = idField.getInt(field);
					}
					Object setValue = null;
					setValue = method.invoke(activity, value);
					if (view != null) {
						field.set(view, setValue);
					} else {
						field.set(activity, setValue);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void bindLayout(@Nullable Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		BindLayout bindLayout = clazz.getAnnotation(BindLayout.class);
		int layoutId = bindLayout.value();
		if (bindLayout == null | layoutId == 0) {
			throw new RuntimeException("Layout file not found!");
		}
		activity.setContentView(layoutId);
	}
}