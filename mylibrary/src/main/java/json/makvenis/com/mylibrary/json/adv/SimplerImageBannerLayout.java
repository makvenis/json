package json.makvenis.com.mylibrary.json.adv;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.squareup.picasso.Picasso;
import java.util.List;
import json.makvenis.com.mylibrary.R;


/**
 * 使用方法
 * mBanner = ((SimplerImageBannerLayout) findViewById(R.id.mBanner));
mBanner.setOnClinkImageBannerLayout(new SimplerImageBannerLayout.OnClinkImageBannerLayout() {
@Override
public void clinkImageIndex(int index) {
Toast.makeText(MainActivity.this,index+"",Toast.LENGTH_SHORT).show();
}
});


List<String> mPath=new ArrayList<>();
mPath.add("http://img3.imgtn.bdimg.com/it/u=670629421,608203952&fm=200&gp=0.jpg");
mPath.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=346054189,780441543&fm=26&gp=0.jpg");
mPath.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1536655533,2863126981&fm=26&gp=0.jpg");
mPath.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2074012031,925977958&fm=26&gp=0.jpg");
mPath.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1440849480,3944946162&fm=26&gp=0.jpg");
mPath.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3709037923,3522139586&fm=26&gp=0.jpg");

网络加载
mBanner.addNetworkImageToSimpleImageBanner(mPath);

int[] mBannerRs = new int[]{
R.drawable.banner1,
R.drawable.banner2,
R.drawable.banner3
};
本地加载
//mBanner.addImageToSimpleImageBanner(bitmaps);

 */


public class SimplerImageBannerLayout extends FrameLayout implements
        SimpleImageBanner.OnSelectChangeIndex, SimpleImageBanner.OnClinkImageBanner{

    /*TAG*/
    public static final String TAG="SimplerImageBannerLayout";

    /* 幻灯布局 */
    private SimpleImageBanner banner;

    /* Dot小圆点布局 */
    private LinearLayout dotLinearLayout;

    public SimplerImageBannerLayout(@NonNull Context context) {
        super(context);
        /* 初始化幻灯 */
        initImagerBannerViewGroup();
        /* 初始化Dot圆点 */
        initDotLinearLayout();


    }

    public SimplerImageBannerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        /* 初始化幻灯 */
        initImagerBannerViewGroup();
        /* 初始化Dot圆点 */
        initDotLinearLayout();

    }

    public SimplerImageBannerLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /* 初始化幻灯 */
        initImagerBannerViewGroup();
        /* 初始化Dot圆点 */
        initDotLinearLayout();

    }

    /* 初始化幻灯 */
    private void initImagerBannerViewGroup() {
        /* 获取幻灯类 */
        banner=new SimpleImageBanner(getContext());
        /* 创建父类 */
        LayoutParams layoutparams=
                new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
        /* 设置幻灯类的父类FragmentLayout里面 */
        banner.setLayoutParams(layoutparams);
        /* 初始化回调事件 */
        banner.setOnSelectChangeIndex(this);
        /* 初始化点击事件 */
        banner.setOnClinkImageBanner(this);
        /* 添加视图 */
        addView(banner);
    }

    /* 初始化Dot圆点 */
    private void initDotLinearLayout() {
        /* dot布局 */
        dotLinearLayout=new LinearLayout(getContext());
        /* 设置Dot布局父类 */
        LayoutParams params=new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 40
        );
        /* 设置方向 */
        dotLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        /* 设置内容居中 */
        dotLinearLayout.setGravity(Gravity.CENTER);
        /* 设置父类 */
        dotLinearLayout.setLayoutParams(params);
        /* 添加到大布局里面 */
        addView(dotLinearLayout);

        /* 设置LinearLayout的布局属性 */
        LayoutParams layoutParams = (LayoutParams) dotLinearLayout.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;
        dotLinearLayout.setLayoutParams(layoutParams);
        //3.0版本使用的是setAlpha() 在3.0之前使用的是setAlpha(),但是调用者不同
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            dotLinearLayout.setAlpha(0.5f);
        } else {
            dotLinearLayout.getBackground().setAlpha(100);
        }

    }

    /* 添加数据到SimpleImageBanner里面 */
    public void addImageToSimpleImageBanner(List<Bitmap> bitmaps){
        for (int i = 0; i < bitmaps.size(); i++) {
            /* 添加img方法 */
            addImageToBanner(bitmaps.get(i));
            /* 添加dot方法 */
            addDot();
        }
    }


    /* 具体添加每一张图片的方法 */
    private void addImageToBanner(Bitmap bitmap) {
        ImageView iv=new ImageView(getContext());
        iv.setImageBitmap(bitmap);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        banner.addView(iv);

    }

    /* 具体添加每一个圆点的方法 */
    public void addDot(){
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);
        lp.setMargins(5, 5, 5, 5);
        imageView.setImageResource(R.drawable.dot_normal);
        dotLinearLayout.addView(imageView);
    }

    /**
     * 回调的索引值
     * @param dos
     */
    @Override
    public void index(int dos) {

        int childCount = dotLinearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //强制转换
            ImageView imageView = (ImageView) dotLinearLayout.getChildAt(i);
            if(i == dos){ //表是选中状态
                imageView.setImageResource(R.drawable.dot_selected);
            }else {
                imageView.setImageResource(R.drawable.dot_normal);
            }
        }
    }


    /**
     * 回调点击事件
     * @param index
     */
    @Override
    public void clinkImageIndex(int index) {
        OnClinkImageBannerLayout.clinkImageIndex(index);
    }

    /* 回调给Activity的点击事件 */
    OnClinkImageBannerLayout OnClinkImageBannerLayout;
    public SimplerImageBannerLayout.OnClinkImageBannerLayout getOnClinkImageBannerLayout() {
        return OnClinkImageBannerLayout;
    }
    public void setOnClinkImageBannerLayout(SimplerImageBannerLayout.OnClinkImageBannerLayout onClinkImageBannerLayout) {
        OnClinkImageBannerLayout = onClinkImageBannerLayout;
    }
    /* 点击事件传递到Activity */
    public interface OnClinkImageBannerLayout{
        void clinkImageIndex(int index);
    }

    /*#######################################拓展部分功能(网络)######################################*/
    /* 具体添加每一张图片的方法 */
    private void addImageByNetworkToBanner(String path) {
        ImageView iv=new ImageView(getContext());
        Picasso.with(getContext()).load(path).into(iv);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        banner.addView(iv);

    }

    public void addNetworkImageToSimpleImageBanner(List<String> path){
        for (int i = 0; i < path.size(); i++) {
            /* 添加img方法 */
            addImageByNetworkToBanner(path.get(i));
            /* 添加dot方法 */
            addDot();
        }
    }





}
