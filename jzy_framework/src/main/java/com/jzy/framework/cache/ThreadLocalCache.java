package com.jzy.framework.cache;

public class ThreadLocalCache {

    public static ThreadLocal<ContextHolder> context = new ThreadLocal<>();

    public static ThreadLocal<ContextHolder> getContext() {
        return context;
    }

    public static void remove(){
        context.remove();
    }

}
