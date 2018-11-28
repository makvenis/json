package json.makvenis.com.json;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import json.makvenis.com.mylibrary.json.banner.BannerLayout;
import json.makvenis.com.mylibrary.json.banner.BannerManager;



public class MainActivity extends AppCompatActivity {
    //private SimpleViewPage mSimpleViewPage;

    public final Context mContext = MainActivity.this;

    private BannerLayout mMyBanner;

    //private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMyBanner = ((BannerLayout) findViewById(R.id.mMyBanner));

        final List<String> mPath=new ArrayList<>();
        mPath.add("http://img3.imgtn.bdimg.com/it/u=670629421,608203952&fm=200&gp=0.jpg");
        mPath.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=346054189,780441543&fm=26&gp=0.jpg");
        mPath.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1536655533,2863126981&fm=26&gp=0.jpg");
        mPath.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2074012031,925977958&fm=26&gp=0.jpg");
        mPath.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1440849480,3944946162&fm=26&gp=0.jpg");
        mPath.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3709037923,3522139586&fm=26&gp=0.jpg");

        final List<String> mText=new ArrayList<>();
        mText.add("1111");
        mText.add("2222");
        mText.add("3333");
        mText.add("4444");
        mText.add("5555");
        mText.add("6666");

        mMyBanner.setAdapterLayout(new BannerManager() {
            @Override
            public View getView(int position) {
                ImageView imageView=new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Picasso.with(mContext).load(mPath.get(position)).into(imageView);
                return imageView;
            }

            @Override
            public int getCount() {
                return mPath.size();
            }

            @Override
            public String getItemText(int position) {
                return mText.get(position);
            }
        });

        mMyBanner.startRoll();



        /*SimpleDelPlusView view = (SimpleDelPlusView) findViewById(R.id.mTest);
        view.setTextColor(Color.BLACK);
        view.onClinkCheckNumber(new SimpleDelPlusView.setOnClinkCheckNumber() {
            @Override
            public void showNowNumbler(int num) {
                SimpleToast.makeText(mContext,num+"", Toast.LENGTH_SHORT).show();
            }
        });*/


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
