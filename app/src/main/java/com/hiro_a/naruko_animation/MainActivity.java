package com.hiro_a.naruko_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.CircularPropagation;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    int statusBarHeight;
    int screenWidth, screenHeight;
    int userImagePosX, userImagePosY;
    int menuAnimLength;
    int userColor = Color.rgb(255,192,203);
    int animFrameRate = 1;
    long animStartTimeMillis, animElapsedTimeMills;
    boolean menuPos = true;
    boolean animIsRunning = true;
    Point userGrid;

    TextView fpsViewer;
    EditText mMessageText;
    ImageView mSendMessageButton;
    ImageView mMenuSlideButton;
    CircularImageView userImageView;

    CanvasView canvasView;
    CanvasView_history canvasViewHistory;
    CanvasView_impassive canvasViewImpassive;
    CanvasView_users canvasViewUsers;
    SurfaceView surfaceView;
    CanvasView_users_test canvasViewUsersTest;
    CanvasView_userLine canvasViewUserLine;

    View menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ウィンドウサイズ取得
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        //ユーザーアイコン座標
        userImagePosX = screenWidth-240;
        userImagePosY = (screenHeight/2)-180;
        //userGrid = new Point(userImagePosX, userImagePosY);

        //入力メニュー移動幅
        menuAnimLength = -(screenWidth/2)+20;

        //ユーザーアイコン
        userImageView = (CircularImageView) findViewById(R.id.userImageView);
        userImageView.setBorderColor(userColor);
        userImageView.setImageResource(R.drawable.gyuki);
        userImageView.setX(userImagePosX);
        userImageView.setY(userImagePosY);

        //メッセージフォーム
        mMessageText = (EditText)findViewById(R.id.messageText);
        mMessageText.setWidth(screenWidth-20);

        mSendMessageButton = (ImageView) findViewById(R.id.btn_send);
        mSendMessageButton.setOnClickListener(this);

        mMenuSlideButton = (ImageView) findViewById(R.id.btn_slide);
        mMenuSlideButton.setOnClickListener(this);

        //アニメーション用View
        canvasView = (CanvasView)findViewById(R.id.canvasView);
        canvasViewHistory = (CanvasView_history)findViewById(R.id.canvasView_history);
        canvasViewImpassive = (CanvasView_impassive) findViewById(R.id.canvasView_impassive);
//        surfaceView = (SurfaceView)findViewById(R.id.canvasView_users);
//        canvasViewUsers = new CanvasView_users(this, surfaceView);
        canvasViewUsersTest = (CanvasView_users_test)findViewById(R.id.canvasView_users_test);
        canvasViewUserLine = (CanvasView_userLine)findViewById(R.id.canvasView_usersLine);

        //入力メニュー
        menuView = findViewById(R.id.chat_ui);

        //アニメーション
        //viewFloating();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                if (!(TextUtils.isEmpty(mMessageText.getText().toString()))){
                    //canvasViewに文字列を送信
                    (canvasView).getMessage(mMessageText.getText().toString());
                    viewRotate();

                    //canvasViewHistory
                    // に文字列を送信
                    canvasViewHistory.getMessage(mMessageText.getText().toString());
                    mMessageText.setText("");

                    //
                    (canvasViewUserLine).getUserGrid(userGrid);
                }
                break;
            case R.id.btn_slide:
                viewSlide();
                break;

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //ステータスバーサイズ取得
        Rect rect = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        statusBarHeight = rect.top;

        //canvasViewに文字列を送信
        getViewGrid();
    }

    private void getViewGrid(){
        int[] viewGrid = new int[2];
        userImageView.getLocationOnScreen(viewGrid);
        userGrid = new Point(viewGrid[0], viewGrid[1]-statusBarHeight);
//        userGrid = new Point(userImagePosX, userImagePosY);
//
//        (canvasViewUsers).getUserGrid(userGrid);
        (canvasViewUsersTest).getUserGrid(userGrid);
        //Toast.makeText(this, userGrid.x+":"+userGrid.y, Toast.LENGTH_SHORT).show();
    }

    private void viewFloating(){
        //ユーザーアイコン上下アニメーション
        ObjectAnimator iconFloatingUp = ObjectAnimator.ofFloat(userImageView,"translationY", userImagePosY-8, userImagePosY+8);
        iconFloatingUp.setDuration(1000);
        iconFloatingUp.setRepeatCount(ObjectAnimator.INFINITE);
        iconFloatingUp.setRepeatMode(ObjectAnimator.REVERSE);
        iconFloatingUp.start();
    }

    private void viewRotate(){
        //文字列回転アニメーション
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.view_rotation);    //アニメーションはR.anim.view_rotationから
        canvasView.startAnimation(rotate);

        //履歴回転アニメーション（ずらす）
        Animation rotate_instant = AnimationUtils.loadAnimation(this, R.anim.view_rotation_instant);    //アニメーションはR.anim.view_rotation_instantから
        canvasViewHistory.startAnimation(rotate_instant);
    }

    private void viewSlide(){
        //入力メニュースライドアニメーション
        if (menuPos){
            ObjectAnimator translate = ObjectAnimator.ofFloat(menuView, "translationX", 0, menuAnimLength);
            translate.setDuration(700);
            translate.start();
            menuPos = false;

        } else if (!menuPos){
            ObjectAnimator translate = ObjectAnimator.ofFloat(menuView, "translationX", menuAnimLength, 0);
            translate.setDuration(700);
            translate.start();
            menuPos = true;
        }
    }
}
