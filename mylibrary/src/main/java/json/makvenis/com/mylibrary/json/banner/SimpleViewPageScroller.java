package json.makvenis.com.mylibrary.json.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/* 继承Scroller */

public class SimpleViewPageScroller extends Scroller {

    /* 传递时间 */
    private int mDuration = 900;

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public SimpleViewPageScroller(Context context) {
        super(context);
    }

    public SimpleViewPageScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public SimpleViewPageScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    /**
     * @param startX
     * @param startY
     * @param dx
     * @param dy
     * @param duration 替换传递的duration
     */
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
