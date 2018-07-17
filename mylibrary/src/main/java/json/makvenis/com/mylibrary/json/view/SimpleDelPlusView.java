package json.makvenis.com.mylibrary.json.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import json.makvenis.com.mylibrary.R;


public class SimpleDelPlusView extends LinearLayout {
    public static final String TAG="SimpleDelPlusView";
    public int textColor = Color.parseColor("#caa11d");

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public SimpleDelPlusView(Context context) {
        super(context);
        getLayout(context);
    }

    public SimpleDelPlusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getLayout(context);
    }


    public void getLayout(Context mContext){
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_del_plus, this);
        //View view = LayoutInflater.from(mContext).inflate(R.layout.layout_del_plus, null);
        ImageView mDel = (ImageView) view.findViewById(R.id.mDel);
        ImageView mAdd = (ImageView) view.findViewById(R.id.mAdd);
        final TextView mNum = (TextView) view.findViewById(R.id.mTextNum);

        /* 设置文本颜色 */
        mNum.setTextColor(getTextColor());


        /* 获取或者设置默认的mNum */
        mNum.setText("0");

        /* 减少 */
        mDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 获取数值 */
                String mGetNum = mNum.getText().toString();
                int i = Integer.valueOf(mGetNum).intValue();
                if(i <= 0){
                    Log.e(TAG,"数值小于等于0 不能做减法");
                }else {
                    i--;
                    /* 更新数值 */
                    mNum.setText(i+"");
                    /* 回调当前数值 */
                    listener.showNowNumbler(i);
                }
            }
        });

        /* 增加 */
        mAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 获取数值 */
                String mGetNum = mNum.getText().toString();
                int i = Integer.valueOf(mGetNum).intValue();
                if(i >= 0){
                    i++;
                    /* 更新数值 */
                    mNum.setText(i+"");
                    /* 回调当前数值 */
                    listener.showNowNumbler(i);
                }else {
                    Log.e(TAG,"数值小于等于0 不能做加法");
                }
            }
        });
    }
    setOnClinkCheckNumber listener;

    public void onClinkCheckNumber(setOnClinkCheckNumber listener) {
        this.listener = listener;
    }

    public interface setOnClinkCheckNumber{
        void showNowNumbler(int num);
    }
}
