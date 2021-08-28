package com.tecpron.tecpronscanning.ui.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.ortiz.touchview.TouchImageView;

import java.util.ArrayList;
import java.util.List;

public class TouchableImageView extends TouchImageView {

    public static final String BUNDLE_SUPER_STATE = "bundle_super_state";
    public static final String BUNDLE_POINT_LIST = "bundle_point_list";

    private float mRadius;
    private Paint myCirclePaint = null;
    private List<PointF> mPoints;
    private GestureDetector mDetector;

    public TouchableImageView(Context context) {
        super(context);
        init(context);
    }

    public TouchableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TouchableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRadius = 15;
        myCirclePaint = new Paint();
        mPoints = new ArrayList<>();
        mDetector = new GestureDetector(context, new MyListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPoints != null) {
            for (int i = 0; i < mPoints.size(); i++) {
                canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mRadius, myCirclePaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    @Nullable
    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_SUPER_STATE, super.onSaveInstanceState());
        bundle.putParcelableArrayList(BUNDLE_POINT_LIST, (ArrayList<? extends Parcelable>) mPoints);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mPoints = bundle.getParcelableArrayList(BUNDLE_POINT_LIST);
            super.onRestoreInstanceState(bundle.getParcelable(BUNDLE_SUPER_STATE));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    class MyListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            if (mPoints != null) {
                mPoints.add(new PointF(event.getX(), event.getY()));
                invalidate();
            }
            return true;
        }
    }
}
