package com.jason.tank;

import java.lang.reflect.Method;

public class ReflectUtil {
    private ReflectUtil() {}

    private static final String GET_INSTANCE = "getInstance";

    public static <T> T getSingleInstance(String classConfigKey, Class<T> classType) {
        try {
            String classFullName = PropertyMgr.getString(classConfigKey);
            Class fsClazz = Class.forName(classFullName);
            Method method = fsClazz.getDeclaredMethod(GET_INSTANCE);
            return (T) method.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
