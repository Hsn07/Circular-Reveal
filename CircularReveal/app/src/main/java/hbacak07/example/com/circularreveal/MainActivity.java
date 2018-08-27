package hbacak07.example.com.circularreveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import hbacak07.example.com.circularreveal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
    }
    public void load(View view){
        animateButtomWidth();
        fadeOutTextandsetProgressDialog();
        nextAction();
    }
    private void animateButtomWidth(){
        ValueAnimator anim=ValueAnimator.ofInt(mBinding.signInBtn.getMeasuredWidth(),getFinalWidth());

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value= (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams=mBinding.signInBtn.getLayoutParams();
                layoutParams.width=value;

                mBinding.signInBtn.requestLayout();

            }
        });

        anim.setDuration(250);
        anim.start();
    }
    private void fadeOutTextandsetProgressDialog(){
        mBinding.signInText.animate().alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                showProgressDialog();
            }
        }).start();

    }
    private void showProgressDialog(){
        mBinding.progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        mBinding.progressBar.setVisibility(View.VISIBLE);
    }
    private void nextAction(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            revealbtn();
            fadeOutProgressDialog();
            deleyedStartNextActivity();
            }
        },2000);
    }
    private void revealbtn(){
        mBinding.signInBtn.setElevation(0f);
        mBinding.revealview.setVisibility(View.VISIBLE);

        int x=mBinding.revealview.getWidth();
        int y=mBinding.revealview.getHeight();

        int StartX=(int)(getFinalWidth()/2+mBinding.signInBtn.getX());
        int StartY=(int)(getFinalWidth()/2+mBinding.signInBtn.getY());

        float radius=Math.max(x,y)*1.2f;

        Animator reveal= ViewAnimationUtils.createCircularReveal(mBinding.revealview,StartX,StartY,getFinalWidth(),radius);
        reveal.setDuration(300);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });
        reveal.start();


    }
    private void fadeOutProgressDialog(){
        mBinding.progressBar.animate().alpha(0f).setDuration(200).start();
    }
    private void deleyedStartNextActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        },100);
    }
    private int getFinalWidth(){
       return (int)getResources().getDimension(R.dimen.getWidth);
    }
}
