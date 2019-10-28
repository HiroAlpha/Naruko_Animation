package com.hiro_a.naruko_animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {
    Paint paint;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 背景、半透明
        canvas.drawColor(Color.argb(127, 0, 127, 63));

        // 円
        paint.setColor(Color.argb(255, 68, 255, 255));
        paint.setStrokeWidth(30);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
        canvas.drawCircle(450, 450, 100, paint);

        // 矩形
        paint.setColor(Color.argb(255, 255, 190, 0));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        canvas.drawRect(480, 480, 850, 880, paint);

        // 線
        paint.setStrokeWidth(15);
        paint.setColor(Color.argb(255, 0, 255, 120));
        // (x1,y1,x2,y2,paint) 始点の座標(x1,y1), 終点の座標(x2,y2)
        canvas.drawLine(350, 850, 750, 630, paint);
    }
}
