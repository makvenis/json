package json.makvenis.com.mylibrary.json.banner;

/* 观察者 */

import android.view.View;

public abstract class BannerManager {

    /**
     * 用户添加视图
     * @param position
     * @return
     */
    public abstract View getView(int position);

    /**
     * 设置视图的个数
     * @return
     */
    public abstract int getCount();

    /**
     * 设置广告标语
     * @param position
     */
    public abstract String getItemText(int position);
}
