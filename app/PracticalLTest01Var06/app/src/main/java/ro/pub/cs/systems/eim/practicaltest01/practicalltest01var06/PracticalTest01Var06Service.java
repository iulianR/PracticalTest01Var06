package ro.pub.cs.systems.eim.practicaltest01.practicalltest01var06;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PracticalTest01Var06Service extends Service {
    String link;

    ProcessingThread processingThread;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String link = intent.getStringExtra("link");

        Log.d("message", "started service");
        processingThread = new ProcessingThread(this, link);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
