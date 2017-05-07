package com.baifan.cleandemo.util;

import java.io.File;

/**
 * Created by baifan on 2017/5/7.
 */

public class FileUtil {
    private static final long BIG_FILE = 10 * 1024 * 1024;

    public static boolean isApk(File f) {
        String path = getFileName(f);
        return path.endsWith(".apk");
    }

    public static boolean isMusic(File f) {
        final String REGEX = "(.*/)*.+\\.(mp3|m4a|ogg|wav|aac)$";
        return f.getName().matches(REGEX);
    }

    public static boolean isBigFile(File f) {
        return f.length() > BIG_FILE;
    }

    public static boolean isTempFile(File f) {
        String name = getFileName(f);
        return name.endsWith(".tmp") || name.endsWith(".temp");
    }

    public static boolean isLog(File f) {
        String name = getFileName(f);
        return name.endsWith(".log");
    }

    public static String getFileName(File f){
        return f.getAbsolutePath();
    }
}
