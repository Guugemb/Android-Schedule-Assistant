package com.example.diy_assistant.Thread;

import android.os.Looper;

/**
 * Created by GeeGumb on 14:33 2019/5/12
 */
public class ServiceNotification extends Thread {
    private static ServiceNotification notification;
    private Runnable runnable;

    private ServiceNotification(Runnable runnable){
        this.runnable = runnable;
    }

    public static ServiceNotification getInstance(Runnable runnable){
        if(notification == null){
            notification = new ServiceNotification(runnable);
        }
        return notification;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
