package com.example.diy_assistant.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.diy_assistant.Bean.ScheduleBean;
import com.example.diy_assistant.Dao.ScheduleDao;
import com.example.diy_assistant.MainActivity;
import com.example.diy_assistant.R;
import com.example.diy_assistant.Thread.ServiceNotification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by GeeGumb on 13:37 2019/5/11
 */
public class PushService extends Service {

    private static final String TAG = "zhujin";

    private Thread thread;
    private List<ScheduleBean> scheduleBeanList = new ArrayList<>();


    @Override
    public boolean stopService(Intent name) {
        Log.i(TAG, "stopService: service stoped");
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: Service destroyed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        thread = ServiceNotification.getInstance(initThread());

        if (!thread.isAlive() || thread.isInterrupted()) {
            thread.start();
            Log.i("zhujin", "onStartCommand: service thread started");
        }
//        return super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }

    private Runnable initThread() {

        Runnable runnable = () -> {
            while (true) {
                ScheduleDao dao = new ScheduleDao(getApplicationContext());
                scheduleBeanList.clear();
                try {
                    scheduleBeanList.addAll(dao.getAll());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                for (ScheduleBean bean : scheduleBeanList) {
                    if (bean.getHasSend() == 0) {
                        long dateNow = new Date().getTime();
                        long deadline = bean.getDeadline().getTime();
                        long fromTime = bean.getFromTime().getTime();

                        double progress = (double) (dateNow - fromTime) / (double) (deadline - fromTime);
                        if (progress >= 0.95) {
                            sendNotification(bean.getTitle(), bean.getDesc());
                            bean.setHasSend(1);
                            try {
                                dao.update(bean);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                try {
                    sleep(20 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return runnable;
    }

    private void sendNotification(String title, String content) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.timg)
                .setTicker("消息提示测试")
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setContentTitle(title)
                .setContentText(content);

        Notification notification = builder.build();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(R.string.app_name, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
