package json.makvenis.com.mylibrary.json.adv;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/* 自定义轮播图 */

@Deprecated
public class SimpleImageBanner extends ViewGroup {

    /*TAG*/
    public static final String TAG="SimpleImageBanner";

    /* 子视图的个数 */
    private int childCount;

    /* 子视图的高度 */
    private int childHeight;

    /* 子视图的高度 */
    private int childWidth;

    /* ViewGroup的总宽度 */
    private int mViewGroupHeight;

    /* ViewGroup的总高度 */
    private int mViewGroupWidth;

    /* 用户按下的时候X坐标 */
    private int x;

    /* childView的索引(当前View) */
    private int mChildViewIndex = 0;


    /* Scroller对象（辅助类） */
    private Scroller mScroller;


    /* 自动播放 */
    /* 默认开启自动轮播 */
    private boolean isAutoPlay = true;
    /* 定时器 */
    private Timer mTimer=new Timer();
    /* 定时器执行具体类容 */
    private TimerTask mTimerTask;
    /* 消息机制 */
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what){
                case 0:
                    //此时需要具体实现轮播
                    //每个一秒接收一次消息
                    if(++mChildViewIndex >= childCount){
                        //说明已经超过了最后一张轮播图片,将从第一张开始重新滑动
                        mChildViewIndex = 0;
                    }
                    //设置轮播
                    scrollTo(mChildViewIndex * childWidth,0);


                    //图片改变通知Dot回调事件去改变
                    mOnSelectChangeIndex.index(mChildViewIndex);

                    break;

            }
        }
    };

    /* 通过一个变量来判断OnTouchEvent动作是否是点击事件 */
    private boolean isClink; //true点击事件 否则为滑动事件

    /* 接口（点击事件） */
    private OnClinkImageBanner clink;
    /* Get and Set */
    public OnClinkImageBanner getClink() {
        return clink;
    }
    public void setOnClinkImageBanner(OnClinkImageBanner clink) {
        this.clink = clink;
    }
    /* 接口类 */
    public interface OnClinkImageBanner{
        void clinkImageIndex(int index);
    }

    /* 接口索引回调（供底部按钮使用） */
    OnSelectChangeIndex mOnSelectChangeIndex;
    /* Get and Set */
    public OnSelectChangeIndex getOnSelectChangeIndex() {
        return mOnSelectChangeIndex;
    }
    public void setOnSelectChangeIndex(OnSelectChangeIndex mOnSelectChangeIndex) {
        this.mOnSelectChangeIndex = mOnSelectChangeIndex;
    }
    /* 接口类 */
    public interface OnSelectChangeIndex{
        void index(int dos);
    }


    /* 开启轮播 */
    private void startAuto(){
        isAutoPlay = true;
    }

    /* 关闭轮播 */
    private void stopAuto(){
        isAutoPlay = false;
    }

    public SimpleImageBanner(Context context) {
        super(context);
        init();
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        /* 创建滑动辅助类 */
        /* 重写ComputeScroller() */
        mScroller = new Scroller(getContext());

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if(isAutoPlay){ //开启轮播
                    mHandler.sendEmptyMessage(0);
                }else {
                    //没有开启轮播。不需要发送消息
                }
            }
        };

        /* 开启定时任务 */
        //在1000毫秒之后 每隔3000毫秒执行一次
        mTimer.schedule(mTimerTask,1000,3000);
    }

    /* Scroller是否在滑动中... */
    @Override
    public void computeScroll() {
        super.computeScroll();
        /* ComputeScrollerOffset()滑动是否完毕 */
        if(mScroller.computeScrollOffset()){
            //滑动中...继续滑动...怎么滑动scrollTo()
            scrollTo(mScroller.getCurrX(),0);
            //不停的滑动 需要重绘
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /* 获取childView个数 */
        childCount = getChildCount();

        if(childCount == 0){
            setMeasuredDimension(0,0);
        }else {
            measureChildren(widthMeasureSpec,heightMeasureSpec);
            /* 拿到第一个view为基准测量 */
            View childAt = getChildAt(0);
            childHeight = childAt.getMeasuredHeight();
            childWidth = childAt.getMeasuredWidth();

            /* 测量总的高宽 */
            mViewGroupHeight = childAt.getMeasuredHeight();
            mViewGroupWidth = (childAt.getMeasuredWidth()) * childCount;

            /* 设置 */
            setMeasuredDimension(mViewGroupWidth,mViewGroupHeight);
        }


    }

    /**
     *
     * @param changed 当布局发生改变的时候 true 改变
     * @param l 距离左边
     * @param t 距离上边
     * @param r 距离右边
     * @param b 距离下边
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if(changed){

            /* 初始化第一个视图距离左边距离0 */
            int leftMargin = 0;

            for (int i = 0; i < childCount; i++) {
                //拿到每一个视图 位置发生变化
                View view = getChildAt(i);
                //设置位置
                view.layout(leftMargin,0,leftMargin+childWidth,childHeight);
                //累加左边距离
                leftMargin+=childWidth;
            }
        }
    }

    /* 拦截处理方法 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //避免按下的时候 停止自动轮播
                stopAuto();


                //当前图片是否滑动完成
                if(mScroller.isFinished()){
                    //停止滑动
                    mScroller.abortAnimation();
                }

                //当前X轴
                x = (int) event.getX();

                //按下为点击事件（如果没有滑动）
                isClink = true;

                break;

            case MotionEvent.ACTION_MOVE:
                /* 移动的X */
                int moveX = (int) event.getX();
                /* 移动的距离 */
                int distance = moveX - x;//（负数）
                /* 图片移动· */
                //如果将scrollBy中的参数dx和dy设置为证书，那么content将向坐标负方向移动；
                // 如果将scrollBy中的参数dx和dy设置为负数，那么content将向坐标轴正方向移动。
                // 因此回到前面的例子，要实现跟随手指移动而滑动的效果，就必须将偏移量改为负值
                /* <a href="https://www.cnblogs.com/tadage/p/7313861.html">参考地址</a> */
                scrollBy(-distance,0);
                x = moveX;

                //如果滑动了 则不是点击事件
                isClink = false;

                break;

            case MotionEvent.ACTION_UP:

                /* 移除屏幕的距离 */
                int scrollX = getScrollX();
                    /*（DOWN--->MOVE） 累加结束，相当于ViewGroup总体移除屏幕的距离（距离最左边）*/
                    /* 求出当前位置的childView索引 */
                mChildViewIndex = (scrollX + childWidth / 2) / childWidth;
                if(mChildViewIndex < 0){
                    mChildViewIndex = 0;
                }else if(mChildViewIndex > childCount - 1) {
                    mChildViewIndex = childCount - 1;
                }
                //scrollTo(mChildViewIndex * childWidth,0); 被Scroller代替

                //判断是否为点击事件
                if(isClink){ //点击事件
                    /* 回调索引 */
                    /*################2018-11-23修正为空##############*/
                    if(clink != null){
                        clink.clinkImageIndex(mChildViewIndex);
                    }
                }else { //滑动事件
                    int dx = mChildViewIndex * childWidth - scrollX;
                    mScroller.startScroll(scrollX,0,dx,0);
                    /* 通知重回 */
                    postInvalidate();

                    //图片改变通知Dot回调事件去改变
                    mOnSelectChangeIndex.index(mChildViewIndex);
                }



                //抬起手 让其又开始轮播
                startAuto();

                break;

            default:
                break;

        }
        return true;
    }
}
