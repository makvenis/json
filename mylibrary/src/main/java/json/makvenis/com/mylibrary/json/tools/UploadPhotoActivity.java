package json.makvenis.com.mylibrary.json.tools;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.util.Date;

import json.makvenis.com.mylibrary.R;
import json.makvenis.com.mylibrary.json.view.SimpleDialogView;


public class UploadPhotoActivity extends AppCompatActivity {

    /* 返回的图标 */
    private ImageView mBankImageView;
    /* 返回的汉字 */
    private TextView mBankTextView;
    /* 上传的文字 */
    private TextView mUploadTextView;
    /* 上传进度的文字显示器 */
    private TextView mUploadNumTextView;
    /* 上传的ProgressBar */
    private ProgressBar mUploadProgressBar;
    /* 以前的旧图标 */
    private ImageView mOldImageViewPhoto;
    /* 旧图标地址 */
    private String mOldImagePath;
    /* 源目标 */
    private String mClassName;
    /* 打开本地图片 */
    private Button mSelectLocalPhoto;
    /* 剪切之后的图片(也就是新地址)等待上传服务器的图片地址 */
    private String newImagePath;
    /* 全局dialog */
    SimpleDialogView dialogView;
    /* 服务器地址 */
    private String mServicePath;
    /* 全局Handler */
    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what){
                case NetworkTools.UPLOAD_CALLBANK:

                    int obj = (int) msg.obj;
                    if(mVistiLine.getVisibility() == View.GONE && obj <= 99){
                        mVistiLine.setVisibility(View.VISIBLE);
                        mUploadNumTextView.setText(obj+"%");
                        mUploadProgressBar.setProgress(obj);
                    }else {
                        mVistiLine.setVisibility(View.VISIBLE);
                        mUploadNumTextView.setText("上传完成");
                        mUploadProgressBar.setProgress(100);
                        dialogView.close();
                    }
                    break;
                case NetworkTools.UPLOAD_CALLBANK_DATA:
                    String data = (String) msg.obj;
                    if(data != null){
                        /* 获取返回的类 */
                        Log.e("DATA",data);
                        try {
                            Class<? extends Activity> mClass = (Class<? extends Activity>) Class.forName(mClassName);
                            Intent intent = new Intent(UploadPhotoActivity.this, mClass);
                            intent.putExtra("data",data);
                            startActivity(intent);
                        } catch (ClassNotFoundException e) {
                        }
                    }

                    break;
            }
        }
    };
    private LinearLayout mVistiLine;
    private String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);


        mBankImageView = ((ImageView) findViewById(R.id.user_bank_left));
        mBankTextView = ((TextView) findViewById(R.id.user_bank_left_text));
        mUploadTextView = ((TextView) findViewById(R.id.upload_user_poto));
        mUploadNumTextView = ((TextView) findViewById(R.id.mProgressBar_int));
        mUploadProgressBar = ((ProgressBar) findViewById(R.id.mProgressBar));
        mOldImageViewPhoto = ((ImageView) findViewById(R.id.old_user_poto));
        mSelectLocalPhoto = ((Button) findViewById(R.id.select_user_poto));
        mVistiLine = ((LinearLayout) findViewById(R.id.mInvisible));
        /* 获取父类参数 */
        getParentData();
        /* 赋值操作 */
        setDataImageView();

        /*  */
        mSelectLocalPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomwindow(v);
            }
        });

        /* 上传 */
        mUploadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView=new SimpleDialogView(UploadPhotoActivity.this,
                        "上传中...");
                dialogView.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("DATA",newImagePath+"--->"+mServicePath+"<---");
                        NetworkTools.upload(newImagePath,UploadPhotoActivity.this,mHandler,mServicePath);
                    }
                }).start();
            }
        });

        /* 返回 */
        mBankImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class<? extends Activity> mClass = (Class<? extends Activity>) Class.forName(mClassName);
                    Intent intent = new Intent(UploadPhotoActivity.this, mClass);
                    intent.putExtra("data","");
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                }
            }
        });

        mBankTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class<? extends Activity> mClass = (Class<? extends Activity>) Class.forName(mClassName);
                    Intent intent = new Intent(UploadPhotoActivity.this, mClass);
                    intent.putExtra("data","");
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                }
            }
        });


    }

    private void setDataImageView() {
        Picasso.with(this).load(mOldImagePath).into(mOldImageViewPhoto);
    }



    public void getParentData() {
        Intent intent = getIntent();
        mOldImagePath = intent.getStringExtra("oldmgpath");
        mClassName = intent.getStringExtra("class");
        mServicePath = intent.getStringExtra("servicepath");
        mFileName = intent.getStringExtra("filename");
    }




    /* PopWindows 的操作等（开始） */
    /**
     * @ 解释直接调用 bottomwindow(view)方法
     * @ 解释 详细的不揍操作在方法setButtonListeners()里面
     */
    private PopupWindow popupWindow;
    //使用PopWindows的时候需要给定当前的View
    public void bottomwindow(View view) {

        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_potocrop_popwindows, null);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        setButtonListeners(layout);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        backgroundAlpha(0.5f);
    }
    //渐变通道
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }
    //PopWindows的控件全局变量
    private LinearLayout callBank,localImage,camera;
    //PopWindows的控件的点击事件
    public void setButtonListeners(LinearLayout view) { //
        callBank = (LinearLayout) view.findViewById(R.id.mHistory_pop_over); //退出
        localImage = (LinearLayout) view.findViewById(R.id.mCrop_local_sd);  //本地图片按钮
        camera = (LinearLayout) view.findViewById(R.id.mCrop_local_camera);  //启动照相机

        /* 启动本地图片选择 */
        localImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActionPoto();
                popupWindow.dismiss();
            }
        });

        /* 启动照相机拍照 */
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/5/21  启动照相机拍照
                getLocalCamera();
                popupWindow.dismiss();
            }
        });

        /* 取消操作 */
        callBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /* PopWindows 的操作等 （结束） */

        /* 启动本地图片的选择 开始 */

    /**
     * @? 获取图片资源
     * @？ 获取图片资源的路径
     * @？ 通过调用android自带的图片剪切
     * @？ 保存剪切的图片资源
     *
     */
    //获取图片资源
    public void getActionPoto(){
        //创建意图
        Intent intent=new Intent();
        //设置获取资源的意图
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //设置获取资源的类型
        intent.setType("image/*");
        //设置如果意图加载失败不崩溃
        ComponentName componentName = intent.resolveActivity(getPackageManager());
        if(componentName !=null){
            startActivityForResult(intent,1);
        }
    }

    //剪切图片 并把剪切之后的图片发出去
    public void getCropPoto(Uri uri) throws IOException {
        //设置裁剪图片后存储位置 crop  文件名称
        File file=new File(getExternalCacheDir(),"crop");
        Log.e("TAG","剪切之后的图片存储路径到文件夹"+file);
        if(!file.exists()){
            file.mkdirs();
        }
        //设置文件名称
        File img=new File(file,mFileName+".jpg");
        if(!img.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("crop","剪切之后的图片存储完整路径"+img);
        //调用android 自带的图片剪切
        Intent intent=new Intent("com.android.camera.action.CROP");
        //设置图片需要地址
        intent.setDataAndType(uri,"image/*");
        //设置属性 都是通过固定的键值对应的关系
        intent.putExtra("crop","true");
        //设置图片宽高比例及其大小
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",200);
        intent.putExtra("outputY",200);
        //设置图片不反回
        intent.putExtra("return-data",false);
        //设置剪切之后的图片保存位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(img));
        //设置输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //是否取消人脸识别
        intent.putExtra("noFaceDetecation",true);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                try {
                    Uri path = data.getData();
                    Log.e("crop",path.toString()+"获取的是选择需要剪切的图片地址");
                    getCropPoto(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                newImagePath = getExternalCacheDir() + File.separator + "crop" + File.separator + mFileName+".jpg";
                Log.e("crop","保存的截图---"+newImagePath);
                Bitmap bitmap= BitmapFactory.decodeFile(newImagePath);
                mOldImageViewPhoto.setImageBitmap(bitmap);
                break;

            case 3:
                if(requestCode == 3) {
                    newImagePath = Environment.getExternalStorageDirectory().toString()+"/WCCamera"+"/"+mFileName+".jpg";
                    Uri uri = Uri.fromFile(new File(newImagePath));
                    Log.e("crop","newImagePath == "+ newImagePath);
                    try {
                        getCropPoto(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    /* 启动本地图片的选择 结束 */

    /* 启动照相机拍照 开始 */
    public void getLocalCamera(){
        //创建意图
        Intent intent=new Intent();
        //设置获取资源的意图
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //在使用Camera video 需要指定路径
        /**
         * 1.指定图像的存储位置，一般图像都是存储在外部存储设备，即SD卡上。

         　　你可以考虑的标准的位置有以下两个：

         　　Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

         　　这个方法返回图像和视频的标准共享位置，别的应用也可以访问，如果你的应用被卸载了，这个路径下的文件是会保留的。

         　　为了区分，你可以在这个路径下为你的应用创建一个子文件夹。

         　2.Context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

         　　这个方法返回的路径是和你的应用相关的一个存储图像和视频的方法。

         　　如果应用被卸载，这个路径下的东西全都会被删除。

         　　这个路径没有什么安全性限制，别的应用也可以自由访问里面的文件。
         */
        Uri mOutPutFileUri;
        String path = Environment.getExternalStorageDirectory().toString()+"/WCCamera";
        File path1 = new File(path);
        if(!path1.exists()){
            path1.mkdirs();
        }
        /**
         *
         *@ 解释  全程地址为： path + "mFileName+".jpg"
         */

        //File file = new File(path1,System.currentTimeMillis()+".jpg");
        File file = new File(path1,mFileName+".jpg");
        mOutPutFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
        Log.e("crop",new Date()+" >>> 预备传递的Intent(MediaStore.EXTRA_OUTPUT, uri) "+mOutPutFileUri+"");
        //设置如果意图加载失败不崩溃
        ComponentName componentName = intent.resolveActivity(getPackageManager());
        if(componentName !=null){
            startActivityForResult(intent,3);
        }
    }

    /* 启动照相机拍照 结束 */


}
