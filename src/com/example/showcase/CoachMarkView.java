package com.example.showcase;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.showcase.AnimationUtils.AnimationEndListener;

/**
 * Coachmark for flight search form
 * 
 * @author anton
 */
public class CoachMarkView extends RelativeLayout implements
		OnTouchListener {
	private Paint mEraser;
	private Paint mEraser2;
	private int alpha1 = 0;
	private int alpha2 = 255;
	private float mX;
	private float mY;
	private float xTarget;
	private float yTarget;
	private int radius = 94;
	private int mRectRight;
	private ArrayList<View> views = new ArrayList<View>();
	private ArrayList<ShowcasePosition> showcasePositions = new ArrayList<ShowcasePosition>();
	private int overlayColor;
	private TypedArray styled;
	private boolean isFinish = false;
	private Storable storable;
	private float scaleMultiplier = 0.5f;
	private ArrayList<View> viewTexts = new ArrayList<View>();
	private static float metricScale = 1.0f;

	/**
	 * Constructor method, target views and text
	 * 
	 * @param context
	 *            Current Activity
	 * @param viewTarget
	 *            list of target views that will be add to a coachmark
	 * @param viewTargetText
	 *            list of text for target views
	 */
	public CoachMarkView(Context context, ArrayList<View> viewTarget,
			ArrayList<View> viewTargetText) {
		super(context);
		views = viewTarget;
		viewTexts = viewTargetText;
		init();
		metricScale = context.getResources().getDisplayMetrics().density;
		radius = (int) (radius * metricScale);
	}

	/**
	 * Create overlay layer and create eraser layout which extends {@link Paint}
	 */
	@TargetApi(11)
	private void init() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(LAYER_TYPE_SOFTWARE, null);
		} else {
			setDrawingCacheEnabled(true);
		}

		int[] attrs = { R.attr.cm_overlayBackgroundColor };
		styled = getContext().obtainStyledAttributes(R.style.Coachmark, attrs);
		overlayColor = styled.getColor(0, Color.BLACK);
		mEraser = new Paint();
		mEraser.setColor(0xFFFFFF);
		mEraser.setAlpha(alpha1);
		mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
		mEraser.setAntiAlias(true);
		mEraser2 = new Paint();
		mEraser2.setColor(0xFFFFFF);
		mEraser2.setAlpha(alpha2);
		mEraser2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
		mEraser2.setAntiAlias(true);

		buildPosition();

	}

	/**
	 * Update target views for next showcase
	 * 
	 * @param index
	 */
	private void setTarget(int index) {
		xTarget = showcasePositions.get(index).getX();
		yTarget = showcasePositions.get(index).getY();
	}

	public void setStorable(Storable storable) {
		this.storable = storable;
	}

	public void setShowcaseIndicatorScale(float scaleMultiplier) {
		this.scaleMultiplier = scaleMultiplier;
	}

	public float getShowcaseIndicatorScale() {
		return this.scaleMultiplier;
	}

	/**
	 * Build text description position
	 */
	@TargetApi(11)
	public void buildPositionText() {
		if (showcasePositions.size() == views.size())
			for (int i = 0; i < viewTexts.size(); i++) {
				RelativeLayout view = (RelativeLayout) viewTexts.get(i);
				if (i == 0) {
					LayoutParams params = new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT);

					params.setMargins(0, 0, 0, views.get(i).getHeight() + 20);
					view.setLayoutParams(params);
					view.setGravity(Gravity.BOTTOM);

				} else if (i == 1) {
					CoachMarkUtil.viewOnTopleft(view, i, views.get(i), 0);
				}
				addView(view);
				invalidate();
			}
	}

	/**
	 * Build target views position
	 */
	private void buildPosition() {
		for (int i = 0; i < views.size(); i++) {
			final View view = views.get(i);
			view.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					final int[] coordinates = new int[2];
					view.getLocationInWindow(coordinates);
					float x = (float) (coordinates[0] + view.getWidth() / 2);
					float y = (float) (coordinates[1] + view.getHeight() / 2);

					ShowcasePosition showcasePosition = new ShowcasePosition();
					showcasePosition.setX(x);
					showcasePosition.setY(y);

					showcasePositions.add(showcasePosition);
					mX = showcasePositions.get(0).getX();
					mY = showcasePositions.get(0).getY();

					buildPositionText();
				}
			});
		}

	}

	/**
	 * This method is called when back button is pressed
	 */
	public void onBackPressed() {
		setVisibility(GONE);
		storable.onFinishShow();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		canvas.drawColor(overlayColor);
		styled.recycle();
		invalidate();
		Matrix mm = new Matrix();
		mm.postScale(scaleMultiplier, scaleMultiplier, mX, mY);
		canvas.setMatrix(mm);
		canvas.drawCircle(mX, mY, radius, mEraser2);
		canvas.setMatrix(new Matrix());
		View v = views.get(0);
		int left = v.getLeft();
		mRectRight = left + (views.get(0).getWidth() * 2);
		Rect r = new Rect(left, (int) mY - v.getHeight() / 2, mRectRight,
				(int) mY + v.getHeight() / 2);
		canvas.drawRect(r, mEraser);
		super.dispatchDraw(canvas);
	}

	public void setShowcaseX(float x) {
		this.mX = x;
	}

	public float getShowcaseX() {
		return this.mX;
	}

	public void setShowcaseY(float y) {
		this.mY = y;
	}

	public float getShowcaseY() {
		return this.mY;
	}

	public int getRectRight() {
		return mRectRight;
	}

	public void setRectRight(int mRectRight) {
		this.mRectRight = mRectRight;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = (int) (radius * metricScale);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		onTouchEvent(event);
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		if (action != MotionEvent.ACTION_UP) {
			if (!isFinish) {
				setTarget(1);
				ValueAnimator xTranslate = ObjectAnimator.ofFloat(this,
						"showcaseX", xTarget);
				xTranslate.setInterpolator(new AccelerateInterpolator(2f));
				xTranslate.setDuration(300);
				ValueAnimator yTranslate = ObjectAnimator.ofFloat(this,
						"showcaseY", yTarget);
				yTranslate.setInterpolator(new AccelerateInterpolator(2f));
				yTranslate.setDuration(300);

				ValueAnimator alpha1 = ObjectAnimator.ofInt(mEraser, "alpha",
						255).setDuration(300);
				ValueAnimator alpha2 = ObjectAnimator.ofInt(mEraser2, "alpha",
						0).setDuration(300);

				ValueAnimator radius = ObjectAnimator.ofFloat(this,
						"showcaseIndicatorScale", 0.25f).setDuration(300);
				ValueAnimator rectRight = ObjectAnimator.ofInt(this,
						"rectRight", (int) mX).setDuration(300);

				ObjectAnimator obj = ObjectAnimator.ofFloat(viewTexts.get(0),
						"alpha", 0f).setDuration(300);
				ObjectAnimator obj2 = ObjectAnimator.ofFloat(viewTexts.get(1),
						"alpha", 1f).setDuration(300);

				AnimatorSet alphaSet = new AnimatorSet();
				alphaSet.play(obj).before(obj2);

				AnimatorSet set1 = new AnimatorSet();
				set1.playTogether(rectRight, alpha1, alpha2);
				AnimatorSet set2 = new AnimatorSet();
				set2.playTogether(xTranslate, yTranslate, radius, alphaSet);
				AnimatorSet animSet = new AnimatorSet();
				animSet.playSequentially(set1, set2);
				animSet.addListener(new AnimatorListener() {

					@Override
					public void onAnimationStart(Animator arg0) {
						storable.onAnimationEnd();
					}

					@Override
					public void onAnimationRepeat(Animator arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animator arg0) {
						isFinish = true;
						storable.onFinishShow();
					}

					@Override
					public void onAnimationCancel(Animator arg0) {

					}
				});
				animSet.start();

				return true;
			} else {
				fadeOutShowcase();
				return true;
			}
		}
		return true;
	}

	/**
	 * Hide this coachmark
	 */
	private void fadeOutShowcase() {
		AnimationUtils.createFadeOutAnimation(this,
				AnimationUtils.DEFAULT_DURATION, new AnimationEndListener() {
					@Override
					public void onAnimationEnd() {
						setVisibility(View.GONE);
					}
				}).start();
	}

	/**
	 * This Class is used for save target view position
	 */
	class ShowcasePosition {
		private float x;
		private float y;

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
	}
}
