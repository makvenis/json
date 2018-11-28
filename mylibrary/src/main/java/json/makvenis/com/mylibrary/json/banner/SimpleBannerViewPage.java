package json.makvenis.com.mylibrary.json.banner;

//自定义ViewPage

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class SimpleBannerViewPage extends ViewPager{
    /* 上下文 */
    public Context mContext;
    /* 常量 */
    public static final int AUTO_MESSAGE = 101;
    /* 设置轮播间隔时间 */
    public int mTime = 3500;
    /* 滑动方向(默认为左滑动) */
    public String SCROLL_ORIENTATION = SCROLL_LEFT;
    /* 左滑动 */
    public static final String SCROLL_LEFT = "left";
    /* 右滑动 */
    public static final String SCROLL_RIGHT = "right";
    /* 设置Scroller切换的速度(毫秒) */
    public int mScrollCurrentTime = 805;

    public SimpleBannerViewPage(Context context) {
        this(context,null);
    }

    public SimpleBannerViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        /* 拿到别人类的私有变量 */
        try {
            //创建自定义的Scroller
            SimpleViewPageScroller scroller=new SimpleViewPageScroller(mContext);
            //设置时间
            scroller.setDuration(mScrollCurrentTime);
            //反射
            Field field = ViewPager.class.getDeclaredField("mScroller");
            //设置强制替换，因为 mScroller 为私有变量 private Scroller mScroller;
            field.setAccessible(true);
            //（可以理解为替换）
            field.set(this,scroller);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /* 2.定义观察者 */
    private BannerManager mBannerManager;

    /* 1.调用设置适配器 */
    public void setSimpleViewPageAdapter(BannerManager mBannerManager){
        this.mBannerManager = mBannerManager;
        /* 设置适配器（隶属于SimpleBannerViewPage的适配器） */
        setAdapter(new SimpleBannerViewPageAdapter());
    }

    /* 3.1实现自己去播放 */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what){
                case AUTO_MESSAGE:
                    /* 设置显示某一个View */
                    // getCurrentItem()，返回当前显示的那一张，默认方向为左滑动 +1
                    //setCurrentItem(getCurrentItem() + 1);

                    //前显示的那一张
                    int mIndex =  getCurrentItem();

                    /* 假如为左滑动，那么viewPage的下一张则为当前第几张图片 + 1 */
                    if(SCROLL_ORIENTATION == SCROLL_LEFT){
                        setCurrentItem(mIndex + 1);
                    }else if(SCROLL_ORIENTATION == SCROLL_RIGHT){

                        /**
                         * 假如为右滑动，那么viewPage的下一张则为当前第几张图片 - 1,经过测试，当处于第一张的时候
                         * 0-1，那么就不存在view视图，自然也就不回去实现自动滑动，所以当加载的是第一张，那么滑动到
                         * 下一张，则为最后一张，通过用户定义的图片个数，来获取 mBannerManager.getCount()。
                         * bug...
                         */

                        if(mIndex == 0){ //反向滑动 则为加载最后一张
                            setCurrentItem(mBannerManager.getCount());
                        }else {
                            setCurrentItem(mIndex - 1);
                        }
                    }

                    //进入循环
                    startRoll();

                    break;
                default:
                    break;

            }
        }
    };

    /* 3.2开始去播放方法 */
    public void startRoll(){
        /* 清除消息 */
        mHandler.removeMessages(AUTO_MESSAGE);
        /* 启动之后，每间隔 mTime 时间发送一次消息到Handler */
        mHandler.sendEmptyMessageDelayed(AUTO_MESSAGE,mTime);
    }

    /* 3.3扩展方法，用户设置间隔时间 */
    public void setScrollItemTime(int time){
        this.mTime = time;
    }

    /* 3.4扩展方法，设置滑动的方向 */
    public void setOrientation(String orientation){
        this.SCROLL_ORIENTATION = orientation;
    }

    /* 4.修改ViewPage的切换速度 */
    public void setDurationScrollerTime(int scrollerTime){
        this.mScrollCurrentTime = scrollerTime;
    }

    /* 5.1回调点击事件 */
    private OnSelectedItemClink clink;
    /* 5.2创建接口 */
    public interface OnSelectedItemClink{
        void clink(int position);
    }
    /* 5.3创建set方法 */
    public void setOnSelectedItemClink(OnSelectedItemClink clink) {
        this.clink = clink;
    }

    /* 内部(SimpleBannerViewPage)适配器 */
    public class  SimpleBannerViewPageAdapter extends PagerAdapter{

        /* 无限循环 */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            //通过观察者提供的View来添加到组，完全由用户自定义添加的视图类型.
            View mView = mBannerManager.getView(position % mBannerManager.getCount());

            //添加视图
            container.addView(mView);

            //点击事件
            mView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //position 需要求模之后传递 否则position无限增大
                    int p = position % mBannerManager.getCount();
                    clink.clink(p);
                }
            });

            return mView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }

}
