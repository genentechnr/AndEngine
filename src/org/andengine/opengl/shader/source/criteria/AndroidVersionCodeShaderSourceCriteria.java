package org.andengine.opengl.shader.source.criteria;

import org.andengine.util.operator.IntOperator;

import android.os.Build;

/**
 * (c) Zynga 2011
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 17:21:13 - 10.10.2011
 */
public class AndroidVersionCodeShaderSourceCriteria extends IntShaderSourceCriteria {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public AndroidVersionCodeShaderSourceCriteria(final IntOperator pIntOperator, final int pAndroidVersionCode) {
		super(pIntOperator, pAndroidVersionCode);
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected int getActualCriteria() {
		return Build.VERSION.SDK_INT;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
