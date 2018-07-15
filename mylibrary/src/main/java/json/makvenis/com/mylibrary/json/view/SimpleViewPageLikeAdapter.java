package json.makvenis.com.mylibrary.json.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import json.makvenis.com.mylibrary.R;


public class SimpleViewPageLikeAdapter extends PagerAdapter {

    List<Map<String,String>> mData;
    Context mContext;

    public SimpleViewPageLikeAdapter(List<Map<String, String>> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if(mData.size() > 0){
            return mData == null ? 0 : Integer.MAX_VALUE;
        }else{
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        /* 赋值 */
        position = position % mData.size();
        final Map<String,String> mp = mData.get(position);
        final int nowPosition = position;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_simple_viewpage_like, null);
        /* 背景大图片 */
        ImageView mImageViewBankground = (ImageView) view.findViewById(R.id.imageView);
        /* 喜欢按钮 */
        final ImageView mLikeImager = (ImageView) view.findViewById(R.id.mLike);
        /* 喜欢的人数 */
        final TextView mlikeNum = (TextView) view.findViewById(R.id.textView);
        /* 点赞 */
        final ImageView mOk = (ImageView) view.findViewById(R.id.mOk);
        /* 点赞数目 */
        final TextView mOkNum = (TextView) view.findViewById(R.id.textView2);
        /* 标题 */
        TextView mTextTitle = (TextView) view.findViewById(R.id.mTextTitle);
        /* beij */
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.mll);
        ll.getBackground().setAlpha(180);

        mlikeNum.setText(mp.get("like"));
        mOkNum.setText(mp.get("ok"));
        mTextTitle.setText(mp.get("title"));

        /* 点击了喜欢 */
        mLikeImager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLikeImager.setImageResource(R.drawable.icon_like_02_120);
                mLikeImager.setEnabled(false);
                String string = mlikeNum.getText().toString();
                listener.showLike(string,nowPosition,mp);
            }
        });

        /* 点击了收藏按钮 */
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOk.setImageResource(R.drawable.icon_ok_02_120);
                mOk.setEnabled(false);
                String string = mOkNum.getText().toString();
                listener.showCollection(string,nowPosition,mp);
            }
        });




        mImageViewBankground.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(mContext).load(mp.get("url")).into(mImageViewBankground);
        Log.e("TAG","url--->"+mp.get("url"));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    setOnClinkCheckListener listener;

    public void setOnclinkPageAdapterListener(setOnClinkCheckListener listener) {
        this.listener = listener;
    }

    public interface setOnClinkCheckListener{

        /* 点击了喜欢 */
        void showLike(String mNum,int position,Map<String,String> map);
        /* 点击了收藏按钮 */
        void showCollection(String mNum,int position,Map<String,String> map);

    }
}
