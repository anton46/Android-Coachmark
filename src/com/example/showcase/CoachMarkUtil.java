package com.example.showcase;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CoachMarkUtil {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void viewOnTopleft(RelativeLayout view, int index, View target, float alpha) {
		view.setPadding(50, (int) (target.getHeight() * 1.5), 0, 0);
		view.setAlpha(alpha);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void viewOnTopleftBook(RelativeLayout view, int index, View target, float alpha) {
		view.setPadding(target.getWidth(), (int) (target.getHeight() * 1.5), 0, 0);
		view.setAlpha(alpha);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void viewOnTopRight(RelativeLayout view, int index, View target, float alpha) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, (int) (target.getHeight() * 1.5), target.getWidth(), 0);
		view.setLayoutParams(params);
		view.setGravity(Gravity.RIGHT);
		view.setAlpha(alpha);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void viewOnMidleLeft(RelativeLayout view, int index, View target, int x, int y, float alpha) {
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT
		);
		int left = x;
		params.setMargins(left, y - target.getHeight()*2, 0 ,0);
		view.setLayoutParams(params);
		view.setGravity(Gravity.TOP);
		view.setAlpha(alpha);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void viewOnMidleRight(RelativeLayout view, int index, View target, int y, float alpha) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, y - target.getMeasuredHeight() * 2, target.getMeasuredWidth(),0);
		view.setLayoutParams(params);
		view.setGravity(Gravity.RIGHT);
		view.setAlpha(alpha);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void viewOnMidleRightHotel(RelativeLayout view, int index, View target, int y, float alpha) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, (int) (y - target.getMeasuredHeight() * 2.5), target.getMeasuredWidth() * 2 + 30,0);
		view.setLayoutParams(params);
		view.setGravity(Gravity.RIGHT);
		view.setAlpha(alpha);
	}
	
	
	public static void viewWebViewTopLeft(RelativeLayout view, View target){
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins((target.getWidth()) + 20, (int) (target.getHeight()) - 20, 0, 0);
		view.setLayoutParams(params);
		view.setGravity(Gravity.LEFT);
	}
	
	public static void viewWebViewTopRight(RelativeLayout view, View target){
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, (int) (target.getHeight()) - 20, (target.getWidth()) + 20, 0);
		view.setLayoutParams(params);
		view.setGravity(Gravity.RIGHT);
	}
	
	public static void viewRowFlightResultDomestic(RelativeLayout view, View target) {
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT
		);
		int top = (int) (view.getY() - target.getHeight());
		params.setMargins(0, top, 0, 0);
		view.setLayoutParams(params);
		view.setGravity(Gravity.TOP);
		view.setAlpha(0);
	}
	
	public static void viewFlightResultDomesticRight(RelativeLayout view, View target, int y) {
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
		);
		params.setMargins(target.getWidth(), y, 0, 0);
		view.setLayoutParams(params);
		view.setGravity(Gravity.RIGHT);
		view.setAlpha(1);
	}
	
	public static void viewFlightResultDomesticLeft(RelativeLayout view, View target, int y) {
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
		);
		params.setMargins(0, y, target.getMeasuredWidth(), 0);
		view.setLayoutParams(params);
		view.setGravity(Gravity.RIGHT);
		view.setAlpha(1);
	}
	

}
