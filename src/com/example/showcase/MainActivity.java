package com.example.showcase;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button mButton1;
	private Button mButton2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mButton1 = (Button) findViewById(R.id.button_1);
		mButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showCoachmark();
			}
		});
		mButton2 = (Button) findViewById(R.id.button_2);
		
		
		showCoachmark();
	}

	public void showCoachmarkWithObserver() {

		scheduleAfterLayout(getWindow().getDecorView(), new Runnable() {

			@Override
			public void run() {
				showCoachmark();
			}
		});
	}
	
	private void showCoachmark() {
		ArrayList<View> viewTarget = new ArrayList<View>();
		ArrayList<View> viewTargetText = new ArrayList<View>();

		viewTarget.add(mButton2);
		viewTarget.add(mButton1);

		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		View view = inflater.inflate(R.layout.coachmark_tab_search,null);
		viewTargetText.add(view);

		View viewRight = inflater.inflate(R.layout.coachmark_arrow_up_left, null);
		viewTargetText.add(viewRight);

		CoachMarkView coach = new CoachMarkView(getApplicationContext(), viewTarget, viewTargetText);
		coach.setStorable(new Storable() {

			@Override
			public void onFinishShow() {

			}

			@Override
			public void onAnimationEnd() {

			}
		});

		((ViewGroup) getWindow().getDecorView()).addView(coach);
	}

	public static void scheduleAfterLayout(final View view,
			final Runnable runnable) {
		view.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@SuppressWarnings("deprecation")
					@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
					@Override
					public void onGlobalLayout() {
						runnable.run();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							view.getViewTreeObserver()
									.removeOnGlobalLayoutListener(this);
						} else {
							view.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
						}
					}
				});
	}

}
