package ro.pub.cs.systems.eim.practicaltest01.practicalltest01var06;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

/**
 * Created by master on 4/4/17.
 */


public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;
    private String link;

    private Random random = new Random();


    public ProcessingThread(Context context, String link) {
        this.context = context;

        this.link = link;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        if (random.nextInt(2) == 0)
            intent.setAction("ro.pub.cs.systems.eim.practicaltest01.actionType1");
        else
            intent.setAction("ro.pub.cs.systems.eim.practicaltest01.actionType2");
        intent.putExtra("link", new Date(System.currentTimeMillis()) + " " + link);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }

}
