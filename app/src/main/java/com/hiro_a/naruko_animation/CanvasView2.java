package com.hiro_a.naruko_animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class CanvasView2 extends View {
    String text = "";
    ArrayList<String> textHolder = new ArrayList<String>(); //過去の文字列格納用Array

    Paint textPaint;

    Path textPath;

    public CanvasView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90);  //反時計回りに描画すると文字が画面外なので回転

        if (textHolder.size() > 1){
            //入力されたものより1つ前の文字列から最も古いものまで
            for (int i=0; i<textHolder.size()-1; i++){
                int radius = (i * 30) + 600;    //TextSize分半径をずらす

                textPath = new Path();
                textPath.addCircle(-400, 0, radius, Path.Direction.CCW);    //円形のパスをx-400、y0を中心として描画、反時計回り

                canvas.drawTextOnPath(textHolder.get(i), textPath, 0, 0, textPaint);
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
