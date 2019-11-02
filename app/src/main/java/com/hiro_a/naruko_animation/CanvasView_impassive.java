package com.hiro_a.naruko_animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView_impassive extends View {
    int textCircleRadius = 400;   //回転半径
    int boundaryLineRedius;

    Paint centerCirclePaint;
    Paint outerCirclePaint;
    Paint graphicPaint_Line;

    Path centerCirclePath;
    Path outerCirclePath;
    Path graphicPath_Line;

    public CanvasView_impassive (Context context, AttributeSet attrs){
        super(context, attrs);

        //中心円設定
        centerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerCirclePaint.setStyle(Paint.Style.FILL);
        centerCirclePaint.setColor(Color.rgb(192,252,214));

        //外側円設定
        outerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerCirclePaint.setStyle(Paint.Style.STROKE);
        outerCirclePaint.setColor(Color.rgb(172,222,242));
        outerCirclePaint.setStrokeWidth(20);

        //UI白線Path設定
        graphicPaint_Line = new Paint(Paint.ANTI_ALIAS_FLAG);
        graphicPaint_Line.setStyle(Paint.Style.STROKE);
        graphicPaint_Line.setColor(Color.WHITE);
        graphicPaint_Line.setStrokeWidth(5);

        centerCirclePath = new Path();
        outerCirclePath = new Path();
        graphicPath_Line = new Path();

        boundaryLineRedius = textCircleRadius - 35;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90);

        //中心円
        centerCirclePath.addCircle(-45, 0, boundaryLineRedius-20, Path.Direction.CW);
        canvas.drawPath(centerCirclePath, centerCirclePaint);

        //外側円
        outerCirclePath.addCircle(-45, 0, boundaryLineRedius-10, Path.Direction.CW);
        canvas.drawPath(outerCirclePath, outerCirclePaint);

        //UI白線
        graphicPath_Line.addCircle(-45, 0, boundaryLineRedius, Path.Direction.CW);
        graphicPath_Line.addCircle(-45, 0, boundaryLineRedius-20, Path.Direction.CW);
        canvas.drawPath(graphicPath_Line, graphicPaint_Line);
    }
}
