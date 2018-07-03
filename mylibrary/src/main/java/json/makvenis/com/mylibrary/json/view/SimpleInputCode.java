package json.makvenis.com.mylibrary.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import json.makvenis.com.mylibrary.R;

/**
 * 自定义输入框
 */

public class SimpleInputCode extends LinearLayout implements
        TextWatcher, View.OnKeyListener{
    //输入类型
    private final static String TYPE_NUMBER = "number";
    private final static String TYPE_TEXT = "text";
    private final static String TYPE_PASSWORD = "password";
    private final static String TYPE_PHONE = "phone";
    private static final String TAG = "SimpleInputCode -->";

    private int box = 4;
    private int boxWidth = 160;
    private int boxHeight = 160;
    private int childHPadding = 14;
    private int childVPadding = 14;
    private Drawable boxBgFocus = null;
    private Drawable boxBgNormal = null;
    private String inputType = TYPE_NUMBER;


    /* Height */
    private List<EditText> mEditTextList = new ArrayList<>(); //承载EditText的集合
    private int currentPosition = 0;
    public OnClinkOverListener listener; //回调当前用户输入完毕的字符串的接口
    private boolean focus = false; //默认未被选中

    public SimpleInputCode(Context context) {
        super(context);
    }

    public SimpleInputCode(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.vericationCodeInput);
        //对应的参数（当前自定义属性id,默认值）
        box = attributes.getInt(R.styleable.vericationCodeInput_box,4);
        childHPadding = (int) attributes.getDimension(R.styleable.vericationCodeInput_child_h_padding, 0);
        childVPadding = (int) attributes.getDimension(R.styleable.vericationCodeInput_child_v_padding, 0);
        boxBgFocus =  attributes.getDrawable(R.styleable.vericationCodeInput_box_bg_focus);
        boxBgNormal = attributes.getDrawable(R.styleable.vericationCodeInput_box_bg_normal);
        inputType = attributes.getString(R.styleable.vericationCodeInput_inputType);
        boxWidth = (int) attributes.getDimension(R.styleable.vericationCodeInput_child_width, boxWidth);
        boxHeight = (int) attributes.getDimension(R.styleable.vericationCodeInput_child_height, boxHeight);

        initViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(getClass().getName(), "onMeasure");
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        if (count > 0) {
            View child = getChildAt(0);
            int cHeight = child.getMeasuredHeight();
            int cWidth = child.getMeasuredWidth();
            int maxH = cHeight + 2 * childVPadding;
            int maxW = (cWidth + childHPadding) * box + childHPadding;
            setMeasuredDimension(resolveSize(maxW, widthMeasureSpec),
                    resolveSize(maxH, heightMeasureSpec));
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(getClass().getName(), "onLayout");
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            child.setVisibility(View.VISIBLE);
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();
            int cl = (i) * (cWidth + childHPadding);
            int cr = cl + cWidth;
            int ct = childVPadding;
            int cb = ct + cHeight;
            child.layout(cl, ct, cr, cb);
        }
    }




    private void initViews() {

        for (int i = 0; i < box; i++) {
            //创建盒子的个数 以及盒子的布局属性
            EditText editText = new EditText(getContext());
            //继承自LinearLayout 获取LinearLayout的布局父类 得到layoutParams对象 参数为指定布局的高宽
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(boxWidth, boxHeight);
            //box的边距
            layoutParams.bottomMargin = childVPadding;
            layoutParams.topMargin = childVPadding;
            layoutParams.leftMargin = childHPadding;
            layoutParams.rightMargin = childHPadding;
            //box整体布局居中
            layoutParams.gravity = Gravity.CENTER;
            //设置点击事件
            editText.setOnKeyListener(this);
            //因为默认选中第一个 多以如果i=0 设置选中的背景颜色
            if(i == 0) {
                //进入输入验证码的界面，第一个默认被选中，且使用被选中的背景颜色
                setBg(editText, true);
            }else {
                //且其余使用没有选中的背景颜色（默认的背景）
                setBg(editText, false);
            }
            //设置editText的文字属性
            editText.setCursorVisible(false);
            editText.setTextSize(30);
            editText.setTextColor(Color.BLACK);
            editText.setLayoutParams(layoutParams);
            editText.setGravity(Gravity.CENTER);
            editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            //显示输入 是ediText内置属性
            //InputFilter 是editText里一个InputFilter  类型的属性，是个集合类型的，所以用[]
            //InputFilter.LengthFilter (20)  设置这个InputFilter 集合 过滤字符的长度
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            //给当前editText便序号 类同setTag()
            editText.setId(i);
            editText.setEms(1);
            //editText点击事件，处理输入完毕，获取焦点等事件（这里不能完全全部用上）
            editText.addTextChangedListener(this);
            addView(editText,i);
            //添加到集合里面
            mEditTextList.add(editText);
            //循环结束
        }
    }

    //设置背景颜色

    /**
     * @param editText 当前box
     * @param focus  是否被选中
     */
    private void setBg(EditText editText, boolean focus) {
        if (boxBgNormal != null && !focus) { //没有被选中
            editText.setBackground(boxBgNormal); //设置默认的背景颜色（这个是通过外部xml加载）
        } else if (boxBgFocus != null && focus) { //被选中
            editText.setBackground(boxBgFocus);
        }
    }

    private void setBg(){
        int count = getChildCount();
        EditText editText ;
        for(int i = 0; i< count; i++){
            editText = (EditText) getChildAt(i);
            if (boxBgNormal != null && !focus) {
                editText.setBackground(boxBgNormal);
            } else if (boxBgFocus != null && focus) {
                editText.setBackground(boxBgFocus);
            }
        }

    }

    //editText点击事件，处理输入完毕，获取焦点等事件（这里不能完全全部用上）
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //在EditText内容已经改变之后调用
    }

    //editText点击事件，处理输入完毕，获取焦点等事件（这里不能完全全部用上）
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /* 方法解释 */
        //1.在Text改变之前被调用(将要替换的字符串，替换的开始位置，将要替换的字符串的长度，替换的个数)
        //2.意思就是说在原有的文本s中，从start开始的count个字符将会被一个新的长度为before的文本替换，注意这里是将被替换，还没有被替换。
        if (start == 0 && count >= 1 && currentPosition != mEditTextList.size() - 1) {
            //解释: 这句话的意思就是，当文本改变的时候（怎么改变，有字符串且从第一个开始，字符长度大于1）
            currentPosition++;
            // EditText获得焦点  mEditTextList.get(currentPosition)返回的是EditText
            mEditTextList.get(currentPosition).requestFocus();
            //设置背景颜色选中的时候
            setBg(mEditTextList.get(currentPosition),true);
            //设置前一个背景颜色没有选中
            setBg(mEditTextList.get(currentPosition-1),false);
        }
    }

    //editText点击事件，处理输入完毕，获取焦点等事件（这里不能完全全部用上）
    @Override
    public void afterTextChanged(Editable s) {
        //文本变化之后
        if (s.length() == 0) {
        } else {
            //只要输入的字符长度大于0
            focus();
            checkAndCommit();
        }
    }

    private void checkAndCommit() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean full = true;
        for (int i = 0 ;i < box; i++){
            EditText editText = (EditText) getChildAt(i);
            String content = editText.getText().toString();
            if ( content.length() == 0) {
                full = false;
                break;
            } else {
                stringBuilder.append(content);
            }

        }
        Log.d(TAG, "checkAndCommit:" + stringBuilder.toString());
        if (full){
            //接口
            if (listener != null) {
                listener.onComplete(stringBuilder.toString());
                setEnabled(false);
            }

        }
    }

    private void focus() {
        //获取子布局个数（感觉跟box大小没有区别）
        int count = getChildCount();
        EditText editText ;
        for (int i = 0; i< count; i++) {
            //循环获取每一个子控件（也就是box）
            editText = (EditText) getChildAt(i);
            //如果长度不大于1 就继续获得焦点
            if (editText.getText().length() < 1) {
                editText.requestFocus();
                return;
            }
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        EditText editText = (EditText) view;
        if (keyCode == KeyEvent.KEYCODE_DEL && editText.getText().length() == 0) {
            int action = event.getAction();
            if (currentPosition != 0 && action == KeyEvent.ACTION_DOWN) {
                currentPosition--;
                mEditTextList.get(currentPosition).requestFocus();
                setBg(mEditTextList.get(currentPosition),true);
                setBg(mEditTextList.get(currentPosition+1),false);
                mEditTextList.get(currentPosition).setText("");
            }
        }
        return true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void setEnabled(boolean enabled) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.setEnabled(enabled);
        }
    }

    public void setOnCompleteListener(OnClinkOverListener listener){
        this.listener = listener;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LinearLayout.LayoutParams(getContext(), attrs);
    }

    //接口
    public interface OnClinkOverListener {
        void onComplete(String content);
    }
}
