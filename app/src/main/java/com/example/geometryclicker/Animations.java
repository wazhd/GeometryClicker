package com.example.geometryclicker;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

public class Animations {
    public void shapeClickAnimation(android.view.View v) {

        float initialScale = 1f;
        float newScale = 1.15f;
        float bobDifference = 0.1f;
        float newBobScale = newScale - bobDifference;


        ObjectAnimator scaleXUp = ObjectAnimator.ofFloat(v, "scaleX", initialScale, newScale);
        ObjectAnimator scaleYUp = ObjectAnimator.ofFloat(v, "scaleY", initialScale, newScale);

        ObjectAnimator scaleXDown = ObjectAnimator.ofFloat(v, "scaleX", newScale, initialScale);
        ObjectAnimator scaleYDown = ObjectAnimator.ofFloat(v, "scaleY", newScale, initialScale);

        ObjectAnimator bobXUp = ObjectAnimator.ofFloat(v, "scaleX", initialScale, newBobScale);
        ObjectAnimator bobXDown = ObjectAnimator.ofFloat(v, "scaleX", newBobScale, initialScale);
        ObjectAnimator bobYUp = ObjectAnimator.ofFloat(v, "scaleY", initialScale, newBobScale);
        ObjectAnimator bobYDown = ObjectAnimator.ofFloat(v, "scaleY", newBobScale, initialScale);

        int duration = 145;
        int bobDuration = 90;
        scaleXUp.setDuration(duration);
        scaleYUp.setDuration(duration);
        scaleXDown.setDuration(duration);
        scaleYDown.setDuration(duration);
        bobXUp.setDuration(bobDuration);
        bobXDown.setDuration(bobDuration);
        bobYUp.setDuration(bobDuration);
        bobYDown.setDuration(bobDuration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXUp).with(scaleYUp); // Play scaleX and scaleY simultaneously
        animatorSet.play(scaleXDown).with(scaleYDown).after(scaleXUp);
        animatorSet.play(bobXUp).with(bobYUp).after(scaleXDown);
        animatorSet.play(bobXDown).with(bobYDown).after(bobXUp);

        animatorSet.start();
    }

    public void rotatingAnimation(android.view.View v, int totalRotations, int rotatingDurationMillis) {
        if (totalRotations <= 0) {
            ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(v, "rotation", 0f, 360f);
            rotateAnimator.setDuration(rotatingDurationMillis);
            rotateAnimator.setInterpolator(new LinearInterpolator());
            rotateAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            rotateAnimator.setRepeatMode(ObjectAnimator.RESTART);


            rotateAnimator.start();

        } else {
            ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(v, "rotation", 0f, 360f);
            rotateAnimator.setDuration(rotatingDurationMillis);
            rotateAnimator.setRepeatCount(totalRotations);
            rotateAnimator.setInterpolator(new LinearInterpolator());
            rotateAnimator.setRepeatMode(ObjectAnimator.RESTART);


            rotateAnimator.start();
        }
    }

    public void savingAnimation(android.view.View v, int fadeDurationMillis, int totalDurationMillis) {
        v.setAlpha(1f);
        float initialAlpha = 0.3f;
        float visibleAlpha = 1f;
        float fadedAlpha = 0f;

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(v, "alpha", initialAlpha, fadedAlpha);
        ObjectAnimator visibleAlphaAnim = ObjectAnimator.ofFloat(v, "alpha", totalDurationMillis-2*fadeDurationMillis, visibleAlpha);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha", fadedAlpha, initialAlpha);
        fadeOut.setDuration(fadeDurationMillis);

        fadeIn.setDuration(fadeDurationMillis);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeIn).before(visibleAlphaAnim);
        animatorSet.play(fadeOut).after(visibleAlphaAnim);

        animatorSet.start();
    }

    public void panelSlideAnimation(android.view.View v, float startY, float endY, int durationMillis) {
        ObjectAnimator slideAnimator = ObjectAnimator.ofFloat(v, "translationY", startY, endY);
        slideAnimator.setDuration(durationMillis);
        slideAnimator.start();
    }

    public void buttonSlideAnimation(android.widget.Button button1, android.widget.Button button2, String text, float startAlpha, float endAlpha, int durationMillis) {
        button1.setAlpha(0);
        button2.setVisibility(Button.GONE);
        button1.setVisibility(Button.VISIBLE);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(button1, "alpha", startAlpha, endAlpha);
        fadeIn.setDuration(durationMillis);
        button1.setText(text);
    }
}
