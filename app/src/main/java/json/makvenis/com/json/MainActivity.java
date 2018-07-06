package json.makvenis.com.json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import json.makvenis.com.mylibrary.json.view.SimpleDialogView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //JSON.GetJson()


    }

    public void showToast(View view) {

        /*SimpleToast toast = SimpleToast.makeText(this, "我的toast信息", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,50);
        toast.show();*/

        SimpleDialogView dialog=new SimpleDialogView(this,"玩命加载中...");
        dialog.show();
        dialog.close();

    }
}
