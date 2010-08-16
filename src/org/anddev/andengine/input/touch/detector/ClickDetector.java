package org.anddev.andengine.input.touch.detector;

import org.anddev.andengine.input.touch.TouchEvent;

import android.view.MotionEvent;

/**
 * @author Nicolas Gramlich
 * @since 14:29:59 - 16.08.2010
 */
public class ClickDetector {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final long TRIGGER_CLICK_MAXIMUM_MILLISECONDS_DEFAULT = 200;

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mEnabled = true;
	private long mTriggerClickMaximumMilliseconds = TRIGGER_CLICK_MAXIMUM_MILLISECONDS_DEFAULT;
	private final IClickDetectorListener mClickDetectorListener;

	private long mDownTimeMilliseconds = Long.MIN_VALUE;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ClickDetector(final IClickDetectorListener pClickDetectorListener) {
		this(TRIGGER_CLICK_MAXIMUM_MILLISECONDS_DEFAULT, pClickDetectorListener);
	}

	public ClickDetector(final long pTriggerClickMaximumMilliseconds, final IClickDetectorListener pClickDetectorListener) {
		this.mTriggerClickMaximumMilliseconds = pTriggerClickMaximumMilliseconds;
		this.mClickDetectorListener = pClickDetectorListener;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public long getTriggerClickMaximumMilliseconds() {
		return this.mTriggerClickMaximumMilliseconds;
	}

	public void setTriggerClickMaximumMilliseconds(final long pClickMaximumMilliseconds) {
		this.mTriggerClickMaximumMilliseconds = pClickMaximumMilliseconds;
	}

	public boolean isEnabled() {
		return this.mEnabled;
	}

	public void setEnabled(final boolean pEnabled) {
		this.mEnabled = pEnabled;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public boolean onTouchEvent(final TouchEvent pTouchEvent) {
		if(this.mEnabled) {
			switch(pTouchEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					this.mDownTimeMilliseconds = pTouchEvent.getMotionEvent().getDownTime();
					return true;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					final long upTimeMilliseconds = pTouchEvent.getMotionEvent().getEventTime();

					if(upTimeMilliseconds - this.mDownTimeMilliseconds <= this.mTriggerClickMaximumMilliseconds) {
						this.mDownTimeMilliseconds = Long.MIN_VALUE;
						this.mClickDetectorListener.onClick(this, pTouchEvent);
					}
					return true;
				default:
					return false;
			}
		} else {
			return false;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static interface IClickDetectorListener {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		public void onClick(final ClickDetector pClickDetector, final TouchEvent pTouchEvent);
	}
}
