package com.jy.jz.zbx.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.jy.jz.zbx.R;


public class MaskView extends AppCompatImageView {

    private static final String TAG = "MaskView";

    private Paint mLinePaint;
    private Paint mAreaPaint;
    private Rect mCenterRect = null;
    private Context mContext;
    private int screenWidth;
    private int screenHeight;

    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initPaint(context);
        mContext = context;
        Point screenMetrics = CommonUtil.getScreenMetrics(mContext);
        screenWidth = screenMetrics.x;
        screenHeight = screenMetrics.y;
    }

    private void initPaint(Context context) {
        //绘制中间透明区域矩形边界的Paint
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(context.getResources().getColor(R.color.all_transparent));
        mLinePaint.setStyle(Style.STROKE);
        mLinePaint.setStrokeWidth(5f);
        mLinePaint.setAlpha(0);

        //绘制四周阴影区域
        mAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAreaPaint.setColor(context.getResources().getColor(R.color.half_transparent));
        mAreaPaint.setStyle(Style.FILL);
        mAreaPaint.setAlpha(128);
    }

    public void setCenterRect(Rect rect) {
        this.mCenterRect = rect;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw");
        if (mCenterRect == null) {
            Log.e(TAG, "mCenterRect == null");
            return;
        }

        //  上
        canvas.drawRect(0, 0, screenWidth, mCenterRect.top, mAreaPaint);
        //  下
        canvas.drawRect(0, mCenterRect.bottom, screenWidth, screenHeight, mAreaPaint);
        //  左
        canvas.drawRect(0, mCenterRect.top, mCenterRect.left, mCenterRect.bottom, mAreaPaint);
        //  右
        canvas.drawRect(mCenterRect.right, mCenterRect.top, screenWidth, mCenterRect.bottom, mAreaPaint);

        //  绘制目标透明区域
        canvas.drawRect(mCenterRect, mLinePaint);
        Log.e(TAG, "onDraw over");
        super.onDraw(canvas);
    }


}
