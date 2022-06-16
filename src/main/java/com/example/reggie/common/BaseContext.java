package com.example.reggie.common;

/**
 * Based on ThreadLocal:
 *              every request are in the same thread
 *              therefore, we can use the set() and get() to store and use the user ID
 */

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurID(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurID() {
        return threadLocal.get();
    }
}
