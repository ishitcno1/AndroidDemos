package com.edinstudio.app.demos.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.edinstudio.app.demos.R;

/**
 * Created by albert on 14-6-21.
 */
public class ViewAnimActivity extends Activity {
    ImageView imageView;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_view_anim);

        animation = AnimationUtils.loadAnimation(this, R.anim.stretch_and_spin);
        imageView = (ImageView) findViewById(R.id.ac_view_anim_image);
    }

    public void startAnim(View view) {
        imageView.startAnimation(animation);
    }
}
