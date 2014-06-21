package com.edinstudio.app.demos.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.edinstudio.app.demos.R;

import java.util.ArrayList;

/**
 * Created by albert on 14-6-20.
 */
public class BouncingBallsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_bouncing_balls);

        LinearLayout container = (LinearLayout) findViewById(R.id.ac_bouncing_balls_container);
        container.addView(new AnimationView(this));
    }

    public class AnimationView extends View {
        private static final int RED = 0xffff0000;
        private static final int BLUE = 0xff0000ff;

        private ArrayList<Ball> balls = new ArrayList<Ball>();

        public AnimationView(Context context) {
            super(context);

            // Animate background color
            // Note that setting the background color will automatically invalidate the
            // view, so that the animated color, and the bouncing balls, get redisplayed on
            // every frame of the animation.
            ObjectAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", RED, BLUE);
            colorAnim.setDuration(3000);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setRepeatCount(ValueAnimator.INFINITE);
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);
            colorAnim.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN ||
                    event.getAction() == MotionEvent.ACTION_MOVE) {
                addBall(event.getX(), event.getY());
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (Ball ball : balls) {
                canvas.save();
                canvas.translate(ball.getX(), ball.getY());
                ball.getShapeDrawable().draw(canvas);
                canvas.restore();
            }
        }

        private void addBall(float x, float y) {
            Ball ball = new Ball(x, y);
            balls.add(ball);

            float startY = ball.getY();
            float endY = getHeight() - ball.getHeight();
            int duration = (int) ( 800 * (getHeight() - startY) / getHeight());

            ObjectAnimator bounceAnim = ObjectAnimator.ofFloat(ball, "y", startY, endY);
            bounceAnim.setDuration(duration);

            ObjectAnimator squashAnim = ObjectAnimator.ofFloat(ball, "height",
                    ball.getHeight(), ball.getHeight() * 3 / 4);
            squashAnim.setDuration(duration / 4);
            squashAnim.setRepeatCount(1);
            squashAnim.setRepeatMode(ValueAnimator.REVERSE);

            ObjectAnimator stretchAnim = ObjectAnimator.ofFloat(ball, "width",
                    ball.getWidth(), ball.getWidth() * 5 / 4);
            stretchAnim.setDuration(duration / 4);
            stretchAnim.setRepeatCount(1);
            stretchAnim.setRepeatMode(ValueAnimator.REVERSE);

            ObjectAnimator bounceBackAnim = ObjectAnimator.ofFloat(ball, "y", endY, startY);
            bounceBackAnim.setDuration(duration);

            ObjectAnimator fadeAnim = ObjectAnimator.ofInt(ball, "alpha", 255, 0);
            fadeAnim.setDuration(250);
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    balls.remove(((ObjectAnimator) animation).getTarget());
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(bounceAnim).before(squashAnim);
            animatorSet.play(squashAnim).with(stretchAnim);
            animatorSet.play(bounceBackAnim).after(squashAnim);
            animatorSet.play(fadeAnim).after(bounceBackAnim);
            animatorSet.start();
        }
    }

    public class Ball {
        private float x;
        private float y;
        private float width = 50f;
        private float height = 50f;
        private int alpha;
        private ShapeDrawable shapeDrawable;

        public Ball(float x, float y) {
            this.x = x - width / 2;
            this.y = y - width / 2;

            Shape circle = new OvalShape();
            circle.resize(width, height);
            shapeDrawable = new ShapeDrawable(circle);

            int red = (int) (Math.random() * 255 + .5f);
            int green = (int) (Math.random() * 255 + .5f);
            int blue = (int) (Math.random() * 255 + .5f);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            shapeDrawable.getPaint().setColor(color);
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
            shapeDrawable.getShape().resize(width, getHeight());
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
            shapeDrawable.getShape().resize(getWidth(), height);
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
            shapeDrawable.setAlpha(alpha);
        }

        public ShapeDrawable getShapeDrawable() {
            return shapeDrawable;
        }
    }
}
