package ro.pub.cs.systems.eim.practicaltest01.practicalltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PracticalTest01Var06MainActivity extends AppCompatActivity {

    private static final int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    private LinearLayout hiddenLayout;
    private Button detailsButton;
    private Button navigateButton;
    private EditText addressBar;

    private ButtonListener buttonListener = new ButtonListener();
    private Button passButton;
    private int serviceStatus = 0;

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.details:
                    hiddenLayout = (LinearLayout)findViewById(R.id.hidden_layout);
                    if (hiddenLayout.getVisibility() == View.INVISIBLE) {
                        hiddenLayout.setVisibility(View.VISIBLE);
                        detailsButton.setText("Less details");
                    } else {
                        hiddenLayout.setVisibility(View.INVISIBLE);
                        detailsButton.setText("More details");
                    }
                    break;
                case R.id.navigate_button:
                    System.out.println("Starting activity");
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06SecondaryActivity.class);
                    intent.putExtra("link", addressBar.getText().toString());
                    passButton = (Button) findViewById(R.id.pass_button);
                    if (passButton.getText().toString().equals("Pass"))
                        intent.putExtra("pass", 1);
                    else
                        intent.putExtra("pass", 0);
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
                default:
                    break;
            }
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);

        detailsButton = (Button) findViewById(R.id.details);
        detailsButton.setOnClickListener(buttonListener);

        navigateButton = (Button) findViewById(R.id.navigate_button);
        navigateButton.setOnClickListener(buttonListener);

        addressBar = (EditText) findViewById(R.id.address_bar);
        addressBar.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (URLUtil.isValidUrl(addressBar.getText().toString())) {
                    passButton = (Button) findViewById(R.id.pass_button);
                    passButton.setBackgroundColor(Color.GREEN);
                    passButton.setText("Pass");

                    if (serviceStatus == 0) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
                        intent.putExtra("link", addressBar.getText().toString());
                        getApplicationContext().startService(intent);
                        System.out.println("Started");
                        serviceStatus = 1;
                    }
                } else {
                    passButton = (Button) findViewById(R.id.pass_button);
                    passButton.setBackgroundColor(Color.RED);
                    passButton.setText("Fail");
                }
                return false;
            }
        });

        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.actionType1");
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.actionType2");
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("link"));
            System.out.println(intent.getStringExtra("link"));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var06Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("Saving " + addressBar.getText().toString());
        outState.putString("url", addressBar.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Colocviu", "Loading");
        addressBar.setText(savedInstanceState.getString("url"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result code: " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
}
