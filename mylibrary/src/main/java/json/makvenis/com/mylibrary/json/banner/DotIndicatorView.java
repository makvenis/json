package json.makvenis.com.mylibrary.json.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/* 画圆点 */

public class DotIndicatorView extends View {

    private Context mContext;
    private Drawable drawable;

    public DotIndicatorView(Context context) {
        this(context,null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext=context;
    }

    /* 传递一个Drawable 来绘制 */
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        drawable.draw(canvas);
        /* 通知重绘 */
        invalidate();
    }
}
