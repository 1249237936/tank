package com.jason.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    static Properties pros = new Properties();

    static {
        try {
            pros.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        return pros.getProperty(key);
    }

    public static Integer getInt(String key) {
        return Integer.parseInt(pros.getProperty(key));
    }
}
