package json.makvenis.com.json;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    //private SimpleViewPage mSimpleViewPage;

    public final Context mContext = MainActivity.this;

    //private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //mText = ((TextView) findViewById(R.id.mText));

        //JSON.GetJson()

        //mSimpleViewPage = ((SimpleViewPage) findViewById(R.id.mSimpleViewPage));

        /*String extra = getIntent().getStringExtra("data");
        mText.setText(extra);

        List<String> mData=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if(i % 2 == 0){
            mData.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1556556440,9566389&fm=115&gp=0.jpg");
            }else {
             mData.add("http://i1.umei.cc/uploads/tu/201806/11/017c745252.jpg");
            }
        }*/

        /*List<Map<String,String>> maps=new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Map<String,String> map=new HashMap<>();
            if(i % 2 == 0){
                map.put("url","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1556556440,9566389&fm=115&gp=0.jpg");
                map.put("like",i+199+"");
                map.put("ok",i+354+"");
                map.put("id",i+10+"");
                map.put("title",i+"--->标题");
            }else {
                map.put("url","http://i1.umei.cc/uploads/tu/201806/11/017c745252.jpg");
                map.put("like",i+199+"");
                map.put("ok",i+354+"");
                map.put("id",i+10+"");
                map.put("title",i+"--->标题");
            }
            maps.add(map);
        }

        SimpleViewPageLikeAdapter adapter = new SimpleViewPageLikeAdapter(maps, this);
        mSimpleViewPage.setAdapter(adapter);
        mSimpleViewPage.setShowTime(4000);
        mSimpleViewPage.setDirection(SimpleViewPage.Direction.LEFT);
        mSimpleViewPage.start();

        adapter.setOnclinkPageAdapterListener(new SimpleViewPageLikeAdapter.setOnClinkCheckListener() {
            @Override
            public void showLike(String mNum, int position, Map<String, String> map) {
                SimpleToast.makeText(mContext,"收藏成功", Toast.LENGTH_SHORT).show();
                Log.e("LOG",position+"");
            }

            @Override
            public void showCollection(String mNum, int position, Map<String, String> map) {
                SimpleToast.makeText(mContext,"点赞成功", Toast.LENGTH_SHORT).show();
            }
        });*/





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


        /*Intent intent=new Intent(this, UploadPhotoActivity.class);
        intent.putExtra("class","json.makvenis.com.json.MainActivity");
        intent.putExtra("servicepath","http://192.168.1.3/im/MyUploadFile");
        intent.putExtra("oldmgpath","http://p0.so.qhmsg.com/bdr/_240_/t01f758438475e8e20f.jpg");
        intent.putExtra("filename","makvenis");
        startActivity(intent);
*/
    }


}
