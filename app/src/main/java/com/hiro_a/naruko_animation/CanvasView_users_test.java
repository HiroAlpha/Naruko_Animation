package com.hiro_a.naruko_animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView_users_test extends View {
    int iconOffset = 240;
    Point userGrid = new Point(0, 0);

    Paint iconOuterCirclePaint;

    Path iconOuterCirclePath;

    public CanvasView_users_test(Context context, AttributeSet attrs) {
        super(context, attrs);
        iconOuterCirclePath = new Path();

        //UI白線Path設定
        iconOuterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iconOuterCirclePaint.setStyle(Paint.Style.STROKE);
        iconOuterCirclePaint.setColor(Color.WHITE);
        iconOuterCirclePaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(90);

        RectF outerCircleRect = new RectF(userGrid.x+21, userGrid.y-iconOffset+21, userGrid.x+iconOffset-21, userGrid.y-21);
        iconOuterCirclePath.addArc(outerCircleRect, 45, 90);
        iconOuterCirclePath.addArc(outerCircleRect, 225, 90);
        //iconOuterCirclePath.addRect(outerCircleRect, Path.Direction.CW);
        canvas.drawPath(iconOuterCirclePath, iconOuterCirclePaint);
    }

    public void getUserGrid(Point grid){
        userGrid = new Point(grid.y, -grid.x);

        invalidate();
    }
}