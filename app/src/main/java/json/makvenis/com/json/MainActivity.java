package json.makvenis.com.json;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import json.makvenis.com.mylibrary.json.tools.UploadPhotoActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = ((TextView) findViewById(R.id.mText));

        //JSON.GetJson()

        String extra = getIntent().getStringExtra("data");
        mText.setText(extra);

    }

    public void showToast(View view) {

        /*SimpleToast toast = SimpleToast.makeText(this, "我的toast信息", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,50);
        toast.show();*/

        /*SimpleDialogView dialog=new SimpleDialogView(this,"玩命加载中...");
        dialog.show();
        dialog.close();*/

        /**
         * mOldImagePath = intent.getStringExtra("oldmgpath");
         * mClassName = intent.getStringExtra("class");
         * mServicePath = intent.getStringExtra("servicepath");
         */


        Intent intent=new Intent(this, UploadPhotoActivity.class);
        intent.putExtra("class","json.makvenis.com.json.MainActivity");
        intent.putExtra("servicepath","http://192.168.1.3/im/MyUploadFile");
        intent.putExtra("oldmgpath","http://p0.so.qhmsg.com/bdr/_240_/t01f758438475e8e20f.jpg");
        intent.putExtra("filename","makvenis");
        startActivity(intent);

    }


}
