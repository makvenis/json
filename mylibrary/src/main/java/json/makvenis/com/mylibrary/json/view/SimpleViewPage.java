package json.makvenis.com.mylibrary.json.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


public class SimpleViewPage extends ViewPager{

    public SimpleViewPage(Context context) {
        super(context);
    }

    public SimpleViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* 播放时间 */
    private int showTime = 3 * 1000;

    /* 滚动方向 */
    private Direction direction = Direction.LEFT;

    /* 设置播放时间，默认3秒 */
    public void setShowTime(int showTimeMillis) {
        this.showTime = showTimeMillis;
    }

    /**
     * @param direction 方向
     */
    /* 设置滚动方向，默认向左滚动 */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    /* 开始 */
    public void start() {
        stop();
        postDelayed(player, showTime);
    }


    /* 停止 */
    public void stop() {
        removeCallbacks(player);
    }

    /* 播放上一个 */
    public void previous() {
        if (direction == Direction.RIGHT) {
            play(Direction.LEFT);
        } else if (direction == Direction.LEFT) {
            play(Direction.RIGHT);
        }
    }

    /* 播放下一个 */
    public void next() {
        play(direction);
    }

    /**
     * @param direction 播放方向
     */
    /* 执行播放 */
    private synchronized void play(Direction direction) {
        PagerAdapter pagerAdapter = getAdapter();
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
            int currentItem = getCurrentItem();
            switch (direction) {
                case LEFT:
                    currentItem++;
                    if (currentItem > count)
                        currentItem = 0;
                    break;
                case RIGHT:
                    currentItem--;
                    if (currentItem < 0)
                        currentItem = count;
                    break;
            }
            setCurrentItem(currentItem);
        }
        start();
    }

    /**
     * 播放器
     */
    private Runnable player = new Runnable() {
        @Override
        public void run() {
            play(direction);
        }
    };

    public enum Direction {
        /**
         * 向左行动，播放的应该是右边的
         */
        LEFT,
        /**
         * 向右行动，播放的应该是左边的
         */
        RIGHT
    }

    //考虑到这里post了一个Runnable，它持有当前Activity的实例，
    // 所以在AutoPlayViewPager所在的当前Activity销毁时可能会发生内存泄漏，
    // 我们在View销毁的时候移除Runnable：
    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(player);
        super.onDetachedFromWindow();
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == SCROLL_STATE_IDLE)
                    start();
                else if (state == SCROLL_STATE_DRAGGING)
                    stop();
            }
        });
    }
}
