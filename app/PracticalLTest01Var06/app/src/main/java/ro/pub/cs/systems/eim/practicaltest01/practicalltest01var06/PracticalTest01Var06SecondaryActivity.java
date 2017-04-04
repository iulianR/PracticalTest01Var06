package ro.pub.cs.systems.eim.practicaltest01.practicalltest01var06;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class PracticalTest01Var06SecondaryActivity extends AppCompatActivity {

    private EditText address;
    private Button cancel_button;
    private Button ok_button;
    private ButtonListener buttonListener = new ButtonListener();
    private Button passButton;

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED);
                    break;
                case R.id.ok_butt:
                    setResult(RESULT_OK);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_secondary);

        address = (EditText) findViewById(R.id.address_bar2);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("link")) {
            String link = intent.getExtras().getString("link");
            address.setText(link);
        }
        passButton = (Button)findViewById(R.id.pass_button2);
        if (intent != null && intent.getExtras().containsKey("pass")) {
            int ok = intent.getExtras().getInt("pass", 0);

            if (ok == 0)
                passButton.setBackgroundColor(Color.RED);
            else
                passButton.setBackgroundColor(Color.GREEN);
        }
        cancel_button = (Button)findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(buttonListener);
        ok_button = (Button)findViewById(R.id.ok_butt);
        ok_button.setOnClickListener(buttonListener);
    }
}
