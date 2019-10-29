package com.hiro_a.naruko_animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {
    int count = 0;  //文字列受け取り回数
    int radius = 600;   //半径
    String text = "";

    Paint textPaint;
    Paint pathPaint;

    Path textPath;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.BLACK);
        pathPaint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90);

        textPath = new Path();
        textPath.addCircle(-400, 0, radius, Path.Direction.CCW);    //円形のパスをx-400、y0を中心として描画、反時計回り

        canvas.drawPath(textPath, pathPaint);
        canvas.drawTextOnPath(text, textPath, 0, 0, textPaint);

    }

    public void getMessage(String messageText){
        text = messageText;

        //1回目の文字列は既定の半径、2回目以降はTextSize分ずらす
        if (count < 6){
            count++;
        }
        radius = ((count-1) * 30) + 600;

        invalidate();   //再描画
    }
}
