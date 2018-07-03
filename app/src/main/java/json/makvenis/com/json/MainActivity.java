package json.makvenis.com.json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import json.makvenis.com.mylibrary.json.view.SimpleInputCode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //JSON.GetJson()

        final SimpleInputCode inputCode = (SimpleInputCode) findViewById(R.id.verificationCodeInput);
        inputCode.setOnCompleteListener(new SimpleInputCode.OnClinkOverListener() {
            @Override
            public void onComplete(String content) {
                Toast.makeText(MainActivity.this,"输入完毕",Toast.LENGTH_SHORT).show();



            }
        });
    }
}
