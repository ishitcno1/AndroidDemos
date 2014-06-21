package com.edinstudio.app.demos.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.edinstudio.app.demos.R;

/**
 * Created by albert on 14-6-21.
 */
public class DrawableAnimActivity extends Activity {
    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_drawable_anim);

        ImageView imageView = (ImageView) findViewById(R.id.ac_drawable_anim_image);
        anim = (AnimationDrawable) imageView.getDrawable();
    }

    public void startAnim(View view) {
        anim.start();
    }
}
