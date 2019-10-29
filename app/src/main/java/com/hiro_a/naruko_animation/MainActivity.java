package com.hiro_a.naruko_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mMessageText;
    Button mSendMessageButton;

    CanvasView canvasView;
    CanvasView2 canvasView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //簡易的なメッセージフォーム
        mMessageText = (EditText)findViewById(R.id.messageText);

        mSendMessageButton = (Button)findViewById(R.id.sendMessageButton);
        mSendMessageButton.setOnClickListener(this);

        //アニメーション用View
        canvasView = (CanvasView)findViewById(R.id.canvasView);
        canvasView2 = (CanvasView2)findViewById(R.id.canvasView2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendMessageButton:
                if (!(TextUtils.isEmpty(mMessageText.getText().toString()))){
                    //canvasViewに文字列を送信
                    (canvasView).getMessage(mMessageText.getText().toString());
                    viewRotate();

                    //canvasView2に文字列を送信
                    canvasView2.getMessage(mMessageText.getText().toString());
                    mMessageText.setText("");
                }
                break;
        }
    }

    private void viewRotate(){
        //アニメーションはR.anim.view_rotationから
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.view_rotation);
        canvasView.startAnimation(rotate);

        /*
        rotateでViewごと文字を回転させようとしていますが、回転軸の設定がうまくいない
        回転のY軸を-400で上にずらすとViewもそれに追従して上にいってしまう
         */
    }
}
