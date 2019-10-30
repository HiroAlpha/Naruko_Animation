package com.hiro_a.naruko_animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class CanvasView2 extends View {
    int textSize = 30;  //文字サイズ
    int radius = 700;   //回転半径
    int chatCircleRedius = 25;  //UI白丸半径
    String text = "";
    ArrayList<String> textHolder = new ArrayList<String>(); //過去の文字列格納用Array
    float btmArcLeft, btmArcTop, btmArcRight, btmArcBttom;
    float topArcLeft, topArcTop, topArcRight, topArcBttom;

    Paint textPaint;
    Paint pathPaint;
    Paint graphicPaint;

    Path textPath;
    Path graphicPath;

    public CanvasView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        //文字列設定
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

        //文字列補助線Path設定
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.RED);
        pathPaint.setStrokeWidth(1);

        //UIPath設定
        graphicPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        graphicPaint.setStyle(Paint.Style.STROKE);
        graphicPaint.setColor(Color.WHITE);
        graphicPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90);  //反時計回りに描画すると文字が画面外なので回転

        if (textHolder.size() > 1){
            int multiplier = 0;
            int textSpan = 0;
            if (textHolder.size() >= 7){
                textSpan = textHolder.size() - 6;
            }

            //入力されたものより1つ前の文字列から最も古いものまで
            for (int i=textSpan; i<textHolder.size()-1; i++){
                radius = (multiplier * (textSize+20)) + 700;    //TextSize分半径をずらす

                //文字列補助線
                textPath = new Path();
                textPath.addCircle(-400, 0, radius, Path.Direction.CCW);    //円形のパスをx-400、y0を中心として描画、反時計回り
                canvas.drawPath(textPath, pathPaint);

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

                //UI白線
                graphicPath = new Path();
                graphicPath.addCircle(radius-400-(textSize/2), 0, chatCircleRedius, Path.Direction.CW); //白丸
                RectF topArcRect = new RectF(topArcLeft, topArcTop, topArcRight, topArcBttom);   //円弧上側範囲
                graphicPath.addArc(topArcRect, 315, 45); //円弧上側
                RectF bottomArcRect = new RectF(btmArcLeft, btmArcTop, btmArcRight, btmArcBttom);  //円弧下側範囲
                graphicPath.addArc(bottomArcRect, 315, 45); //円弧下側
                canvas.drawPath(graphicPath, graphicPaint);

                //曲線文字列
                canvas.drawTextOnPath(textHolder.get(i), textPath, 40, 0, textPaint);

                multiplier++;
            }
        }
    }

    public void getMessage(String messageText){
        textHolder.add(messageText);    //Arrayに文字列を追加

        //最初の入力は描画しない
        if (textHolder.size() > 1){
            invalidate();
        }
    }
}
