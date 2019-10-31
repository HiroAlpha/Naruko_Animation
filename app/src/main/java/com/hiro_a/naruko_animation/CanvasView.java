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
import android.widget.Toast;

import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;

public class CanvasView extends View {
    int count = 0;  //文字列受け取り回数
    int textSize = 30;  //文字サイズ
    int radius = 700;   //回転半径
    int chatCircleRedius = 25;  //UI白丸半径
    float btmArcLeft, btmArcTop, btmArcRight, btmArcBttom;
    float topArcLeft, topArcTop, topArcRight, topArcBttom;
    double rightCircleSin, rightCircleCos;
    String text = "";
    boolean drawMode = false;

    Paint textPaint;
    Paint pathPaint;
    Paint graphicPaint;

    Path textPath;
    Path graphicPath;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //文字列設定
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

        //文字列補助線Path設定
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.BLACK);
        pathPaint.setStrokeWidth(1);

        //UIPath設定
        graphicPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        graphicPaint.setStyle(Paint.Style.STROKE);
        graphicPaint.setColor(Color.WHITE);
        graphicPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90);

        //文字列補助線
        textPath = new Path();
        textPath.addCircle(-400, 0, radius, Path.Direction.CCW);    //円形のパスをx-400、y0を中心として描画、反時計回り
        canvas.drawPath(textPath, pathPaint);

        if (drawMode){
            //UI白線
            graphicPath = new Path();
            graphicPath.addCircle(radius-400-(textSize/2), 0, chatCircleRedius, Path.Direction.CW); //左白丸
            graphicPath.addCircle((float) rightCircleCos, -((float)rightCircleSin), chatCircleRedius, Path.Direction.CW); //右白丸
            RectF topArcRect = new RectF(topArcLeft, topArcTop, topArcRight, topArcBttom);   //円弧上側範囲
            graphicPath.addArc(topArcRect, 315, 45); //円弧上側
            RectF bottomArcRect = new RectF(btmArcLeft, btmArcTop, btmArcRight, btmArcBttom);  //円弧下側範囲
            graphicPath.addArc(bottomArcRect, 315, 45); //円弧下側
            canvas.drawPath(graphicPath, graphicPaint);

            //曲線文字列
            canvas.drawTextOnPath(text, textPath, 40, 0, textPaint);
        }

    }

    public void getMessage(String messageText){
        text = messageText;

        //1回目の文字列は既定の半径、2回目以降はTextSize分ずらす
        if (count < 6){
            count++;
        }
        radius = ((count-1) * (textSize+20)) + 700;

        //右白丸描画用座標
        rightCircleSin = (sin(Math.toRadians(45))*(radius-(textSize/2)));
        rightCircleCos = (cos(Math.toRadians(45))*(radius-(textSize/2))) - 400;
        //円弧描画用座標（上側）
        topArcLeft = -(400+radius-(textSize/2)-chatCircleRedius);
        topArcTop = -(radius-(textSize/2)-chatCircleRedius);
        topArcRight = radius-400-(textSize/2)-chatCircleRedius;
        topArcBttom = radius-(textSize/2)-chatCircleRedius;
        //円弧描画用座標（下側）
        btmArcLeft = -(400+radius-(textSize/2)+chatCircleRedius);
        btmArcTop = -(radius-(textSize/2)+chatCircleRedius);
        btmArcRight = radius-400-(textSize/2)+chatCircleRedius;
        btmArcBttom = radius-(textSize/2)+chatCircleRedius;

        drawMode = true;
        invalidate();   //再描画
        Toast.makeText(getContext(), rightCircleSin+"："+rightCircleCos, Toast.LENGTH_SHORT).show();
    }
}
