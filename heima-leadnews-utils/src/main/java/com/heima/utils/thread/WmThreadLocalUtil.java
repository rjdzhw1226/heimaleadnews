package com.heima.utils.thread;

import com.heima.model.wemedia.pojos.WmUser;

public class WmThreadLocalUtil {
    private final static ThreadLocal<WmUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();

    //存入本地线程
    public static void setUser(WmUser wmUser){
        WM_USER_THREAD_LOCAL.set(wmUser);
    }

    //取出
    public static WmUser getUser(){
        return WM_USER_THREAD_LOCAL.get();
    }

    //清除
    public static void clearUser(){
        WM_USER_THREAD_LOCAL.remove();
    }

}
