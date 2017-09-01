package com.xiaokele.rightscrollcleanitemlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by QiyiLive on 2017/9/1.
 */

public class RightScrollCleanSelectedItemListView extends ListView implements GestureDetector.OnGestureListener, View.OnTouchListener {

    //手势动作监听器
    private GestureDetector mGestureDetector;
    //删除按钮是否显示
    private boolean isDeleteShown;
    //选中的item
    private int mSelectedItem;
    //删除按钮
    private View mDeleteBtn;
    private ViewGroup mItemLayout;

    public RightScrollCleanSelectedItemListView(Context context) {
        this(context, null);
    }

    public RightScrollCleanSelectedItemListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RightScrollCleanSelectedItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //创建首饰监听器对象
        mGestureDetector = new GestureDetector(getContext(), this);
        //监测onTouch事件
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (isDeleteShown) {
            hideDeleteBtn();
            return false;
        }else{
            return mGestureDetector.onTouchEvent(motionEvent);
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        if (!isDeleteShown){
            mSelectedItem = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {
        // 如果当前删除按钮没有显示出来，并且x方向滑动的速度大于y方向的滑动速度
        if (!isDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)) {
            mDeleteBtn = LayoutInflater.from(getContext()).inflate(R.layout.delete_btn, null);
            mDeleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemLayout.removeView(mDeleteBtn);
                    mDeleteBtn=null;
                    isDeleteShown=false;
                    if (null != onDeleteClickListener) {
                        onDeleteClickListener.onClickDelete(mSelectedItem);
                    }
                }
            });

            mItemLayout = (ViewGroup)getChildAt(mSelectedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mItemLayout.addView(mDeleteBtn, layoutParams);
            isDeleteShown=true;
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /**
     * 隐藏删除按钮
     * */
    public void hideDeleteBtn() {
        mItemLayout.removeView(mDeleteBtn);
        mDeleteBtn=null;
        isDeleteShown=false;
    }

    public boolean isDeleteShown(){
        return isDeleteShown;
    }

    /**
     * 添加删除监听
     * */
    private OnDeleteClickListener onDeleteClickListener;
    public interface OnDeleteClickListener{
        void onClickDelete(int index);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener=onDeleteClickListener;
    }
}
