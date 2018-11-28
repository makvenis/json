package json.makvenis.com.mylibrary.json.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import json.makvenis.com.mylibrary.R;


/**
 * 自定义Banner(包括文字，按钮信息等)
 */

public class BannerLayout extends RelativeLayout {

    /* Context */
    public Context mContext;

    /* ViewPage */
    private SimpleBannerViewPage mSimpleBannerViewPage;
    /* 轮播标题 */
    private TextView mSimpleBannerAdvText;
    /* 小圆点的容器 */
    private LinearLayout mSimpleBannerLinearLayout;


    /* BannerManager */
    private BannerManager mBannerManager;

    /* 滑动间隔时间 */
    private int scrollItemTime = 3500;
    /* 滑动方向(默认为左滑动) */
    public String SCROLL_ORIENTATION = SCROLL_LEFT;
    /* 左滑动 */
    public static final String SCROLL_LEFT = "left";
    /* 右滑动 */
    public static final String SCROLL_RIGHT = "right";
    /* 设置Scroller切换的速度(毫秒) */
    public int mScrollCurrentTime = 805;

    /* 选中状态 */
    private Drawable mSelectFocusDrawable;
    /* 未选中状态 */
    private Drawable mSelectNormalDrawable;

    /* 记录之前的位置 */
    private int old_index = 0;

    /* 扩展方法设置底部圆点的方位，靠右，居中  */
    private int mGravity = Gravity.RIGHT;

    /* 7.1回调点击事件 */
    private OnSelectedItemClink clink;
    /* 7.2创建接口 */
    public interface OnSelectedItemClink{
        void clink(int position);
    }
    /* 7.3创建set方法 */
    public void setOnSelectedItemClink(OnSelectedItemClink clink) {
        this.clink = clink;
    }




    public BannerLayout(Context context) {
        this(context,null);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        /* 1.初始化布局 */
        initView();
        /* 4.初始化指示器的颜色 */
        mSelectFocusDrawable = new ColorDrawable(Color.RED);
        mSelectNormalDrawable = new ColorDrawable(Color.WHITE);

    }


    /* 1.初始化布局 */
    private void initView() {
        //加载到当前布局中
        inflate(mContext, R.layout.banner_simple_view_page_layout,this);
        //控件查找
        mSimpleBannerViewPage = ((SimpleBannerViewPage) findViewById(R.id.mSimpleBannerViewPage));
        mSimpleBannerAdvText = ((TextView) findViewById(R.id.mSimpleBannerAdvText));
        mSimpleBannerLinearLayout = ((LinearLayout) findViewById(R.id.mSimpleBannerDot));
    }

    /* 2.设置适配器 */
    public void setAdapterLayout(BannerManager mBannerManager){
        this.mBannerManager=mBannerManager;
        //自定义布局中设置适配器
        mSimpleBannerViewPage.setSimpleViewPageAdapter(mBannerManager);
        //加载圆点布局 初始化点的指示器
        initDotIndicator();
        //监听滑动到第几张
        mSimpleBannerViewPage.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            /**
             * 监听滑动到几张（位置）
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                /* 位置改变之后触发 */
                changeDotIndex(position);
            }


        });

        //当是第一个视图的时候默认赋值
        if(old_index == 0){
            String mFirstItemText = mBannerManager.getItemText(0);
            mSimpleBannerAdvText.setText(mFirstItemText);
        }

        //ViewPage点击事件
        mSimpleBannerViewPage.setOnSelectedItemClink(new SimpleBannerViewPage.OnSelectedItemClink() {
            @Override
            public void clink(int position) {
                clink.clink(position);
            }
        });


    }

    /* 3.加载圆点布局 初始化点的指示器 */
    private void initDotIndicator() {

        //获取布局总数
        int count = mBannerManager.getCount();

        /**
         * 可以居中，靠右，靠左（文本默认是靠左的）
         * {@link #setBottomDotIndicatorGravity(int)}
         * 布局内容靠右（默认靠右）
         */
        mSimpleBannerLinearLayout.setGravity(mGravity);
        //更改背景透明度
        //在3.0之前使用的是setAlpha(),但是调用者不同
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mSimpleBannerLinearLayout.setAlpha(0.7f);
        } else {
            mSimpleBannerLinearLayout.getBackground().setAlpha(200);
        }

        //循环添加
        for (int i = 0; i < count; i++) {
            //创建圆点布局
            DotIndicatorView mDotView = new DotIndicatorView(mContext);

            //设置布局大小
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(dp2px(16),dp2px(6));
            //设置布局
            mDotView.setLayoutParams(params);
            //设置背景颜色
            mDotView.setBackgroundColor(Color.RED);
            //设置边距
            params.leftMargin = params.rightMargin = dp2px(4);

            //默认选中第一个
            if(i == 0){
                mDotView.setDrawable(mSelectFocusDrawable);
            }else {
                mDotView.setDrawable(mSelectNormalDrawable);
            }

            //6.添加布局
            mSimpleBannerLinearLayout.addView(mDotView);
        }




    }

    /* 5.动态改变dotIndicator的位置 */
    private void changeDotIndex(int position) {
        //清除之前（旧位置）的位置设置为未选中
        //拿到旧的view
        DotIndicatorView childAt_old = (DotIndicatorView)mSimpleBannerLinearLayout.getChildAt(old_index);
        childAt_old.setDrawable(mSelectNormalDrawable);

        //更新新的选中位置
        //拿到旧的view
        //position % mBannerManager.getCount(); 不确定这个position是否被去摸过
        int p = position % mBannerManager.getCount();
        old_index = p;
        DotIndicatorView childAt_news = (DotIndicatorView)mSimpleBannerLinearLayout.getChildAt(p);
        childAt_news.setDrawable(mSelectFocusDrawable);

        /* 动态更改广告汉字 此处也要取模 否则数组越界 */
        String mText = mBannerManager.getItemText(p);
        mSimpleBannerAdvText.setText(mText);

    }



    /* dip 转 px */
    private int dp2px(int dip) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
        return px;
    }


    /* 开始滑动 */
    public void startRoll() {
        mSimpleBannerViewPage.startRoll();
    }
    /* 滑动间隔时间 */
    public void setScrollItemTime(int scrollItemTime) {
        this.scrollItemTime = scrollItemTime;
        mSimpleBannerViewPage.setScrollItemTime(scrollItemTime);
    }

    /**
     * 滑动的方向 使用 {@link BannerLayout#SCROLL_LEFT} 向左滑动
     * {@link BannerLayout#SCROLL_RIGHT} 向右滑动，反向滑动
     * @param orientation 方向
     */
    public void setOrientation(String orientation) {
        this.SCROLL_ORIENTATION = orientation;
        mSimpleBannerViewPage.setOrientation(SCROLL_ORIENTATION);
    }

    /* 设置滑动持续的事件 */
    public void setDurationScrollerTime(int durationScrollerTime) {
        this.mScrollCurrentTime = durationScrollerTime;
        mSimpleBannerViewPage.setDurationScrollerTime(mScrollCurrentTime);
    }


}
