package com.hiro_a.naruko_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    int screenWidth;
    int screenHeight;
    int menuAnimLength;
    boolean menuPos = true;

    EditText mMessageText;
    ImageView mSendMessageButton;
    ImageView mMenuSlideButton;

    CanvasView canvasView;
    CanvasView_history canvasViewHistory;
    CanvasView_impassive canvasViewImpassive;

    View menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        menuAnimLength = -(screenWidth/2)+20;

                //簡易的なメッセージフォーム
        mMessageText = (EditText)findViewById(R.id.messageText);

        mSendMessageButton = (ImageView) findViewById(R.id.btn_send);
        mSendMessageButton.setOnClickListener(this);

        mMenuSlideButton = (ImageView) findViewById(R.id.btn_slide);
        mMenuSlideButton.setOnClickListener(this);

        //アニメーション用View
        canvasView = (CanvasView)findViewById(R.id.canvasView);
        canvasViewHistory = (CanvasView_history)findViewById(R.id.canvasView_history);
        canvasViewImpassive = (CanvasView_impassive) findViewById(R.id.canvasView_impassive);

        menuView = findViewById(R.id.chat_ui);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                if (!(TextUtils.isEmpty(mMessageText.getText().toString()))){
                    //canvasViewに文字列を送信
                    (canvasView).getMessage(mMessageText.getText().toString());
                    viewRotate();

                    //canvasView2に文字列を送信
                    canvasViewHistory.getMessage(mMessageText.getText().toString());
                    mMessageText.setText("");
                }
                break;
            case R.id.btn_slide:
                viewSlide();
                //Toast.makeText(this, "pushed!", Toast.LENGTH_SHORT).show();
                break;

        }
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
