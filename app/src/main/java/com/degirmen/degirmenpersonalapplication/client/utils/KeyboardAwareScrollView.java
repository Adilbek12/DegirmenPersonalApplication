package com.degirmen.degirmenpersonalapplication.client.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ScrollView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class KeyboardAwareScrollView extends ScrollView implements KeyboardVisibilityEventListener {

  public KeyboardAwareScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    KeyboardVisibilityEvent.setEventListener((Activity) getContext(), this);
  }

  @Override
  public void onVisibilityChanged(boolean isOpen) {
    if (isOpen) {
      smoothScrollTo(0, getBottom());
    } else {
      smoothScrollTo(0, getTop());
    }
  }
}
