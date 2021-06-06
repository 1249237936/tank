package com.jason.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    static Properties pros = new Properties();

    static {
        try {
            pros.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        if (pros == null) return null;
        return pros.getProperty(key);
    }

}
