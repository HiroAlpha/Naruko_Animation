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
    int radius = 400;   //回転半径
    int chatCircleRedius = 20;  //UI白丸半径
    float btmArcLeft, btmArcTop, btmArcRight, btmArcBttom;
    float topArcLeft, topArcTop, topArcRight, topArcBttom;
    double rightCircleSin, rightCircleCos;
    String text = "";
    boolean drawMode = false;

    Paint textPaint;
    Paint pathPaint;
    Paint graphicPain_Line;
    Paint graphicPaint_Colored, graphicPaint_Colored_FILL;

    Path textPath;
    Path graphicPath;
    Path graphicPath_Line;
    Path graphicPath_Colored;

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

        //UI下色Path設定
        graphicPaint_Colored_FILL = new Paint(Paint.ANTI_ALIAS_FLAG);
        graphicPaint_Colored_FILL.setStyle(Paint.Style.FILL);
        graphicPaint_Colored_FILL.setColor(Color.rgb(255,192,203));

        //UI下色Path設定
        graphicPaint_Colored = new Paint(Paint.ANTI_ALIAS_FLAG);
        graphicPaint_Colored.setStyle(Paint.Style.STROKE);
        graphicPaint_Colored.setColor(Color.rgb(255,192,203));
        graphicPaint_Colored.setStrokeWidth(chatCircleRedius*2);

        //UI白線Path設定
        graphicPain_Line = new Paint(Paint.ANTI_ALIAS_FLAG);
        graphicPain_Line.setStyle(Paint.Style.STROKE);
        graphicPain_Line.setColor(Color.WHITE);
        graphicPain_Line.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90);

        //文字列補助線
        textPath = new Path();
        textPath.addCircle(-45, 0, radius, Path.Direction.CCW);    //円形のパスをx-45、y0を中心として描画、反時計回り
        canvas.drawPath(textPath, pathPaint);

        if (drawMode){
            //UI下色
            graphicPath_Colored = new Path();
            RectF coloredArc = new RectF(topArcLeft-chatCircleRedius, topArcTop-chatCircleRedius, topArcRight+chatCircleRedius, topArcBttom+chatCircleRedius);   //円弧範囲
            graphicPath_Colored.addArc(coloredArc, 270, 90); //円弧
            canvas.drawPath(graphicPath_Colored, graphicPaint_Colored);

            //共通項
            graphicPath = new Path();
            graphicPath.addCircle(radius-45-(textSize/2), 0, chatCircleRedius, Path.Direction.CW); //左丸
            graphicPath.addCircle((float) rightCircleCos, -((float)rightCircleSin), chatCircleRedius, Path.Direction.CW); //右丸
            canvas.drawPath(graphicPath, graphicPaint_Colored_FILL);
            canvas.drawPath(graphicPath, graphicPain_Line);

            //UI白線
            graphicPath_Line = new Path();
            RectF topArcRect = new RectF(topArcLeft, topArcTop, topArcRight, topArcBttom);   //円弧上側範囲
            graphicPath_Line.addArc(topArcRect, 270, 90); //円弧上側
            RectF bottomArcRect = new RectF(btmArcLeft, btmArcTop, btmArcRight, btmArcBttom);  //円弧下側範囲
            graphicPath_Line.addArc(bottomArcRect, 270, 90); //円弧下側
            canvas.drawPath(graphicPath_Line, graphicPain_Line);

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
        radius = ((count-1) * (textSize+10)) + 400;

        //右白丸描画用座標
        rightCircleSin = (sin(Math.toRadians(90))*(radius-(textSize/2)));
        rightCircleCos = (cos(Math.toRadians(90))*(radius-(textSize/2))) - 45;
        //円弧描画用座標（上側）
        topArcLeft = -(45+radius-(textSize/2)-chatCircleRedius);
        topArcTop = -(radius-(textSize/2)-chatCircleRedius);
        topArcRight = radius-45-(textSize/2)-chatCircleRedius;
        topArcBttom = radius-(textSize/2)-chatCircleRedius;
        //円弧描画用座標（下側）
        btmArcLeft = -(45+radius-(textSize/2)+chatCircleRedius);
        btmArcTop = -(radius-(textSize/2)+chatCircleRedius);
        btmArcRight = radius-45-(textSize/2)+chatCircleRedius;
        btmArcBttom = radius-(textSize/2)+chatCircleRedius;

        drawMode = true;
        invalidate();   //再描画
        Toast.makeText(getContext(), rightCircleSin+"："+rightCircleCos, Toast.LENGTH_SHORT).show();
    }
}
