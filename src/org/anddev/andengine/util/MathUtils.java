package org.anddev.andengine.util;

import java.util.Random;

import org.anddev.andengine.util.constants.MathConstants;

import android.util.FloatMath;

/**
 * @author Nicolas Gramlich
 * @since 20:42:15 - 17.12.2009
 */
public class MathUtils implements MathConstants {
	// ===========================================================
	// Constants
	// ===========================================================

	public static Random RANDOM = new Random(System.nanoTime());

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public static final float radToDeg(final float pRad) {
		return RAD_TO_DEG * pRad;
	}

	public static final float degToRad(final float pDegree) {
		return DEG_TO_RAD * pDegree;
	}

	public static final float random(final float pMin, final float pMax) {
		return pMin + RANDOM.nextFloat() * (pMax - pMin);
	}

	public static final int random(final int pMin, final int pMax) {
		return pMin + RANDOM.nextInt(pMax - pMin + 1);
	}

	public static final boolean isPowerOfTwo(final int n) {
		return ((n != 0) && (n & (n - 1)) == 0);
	}

	public static final int nextPowerOfTwo(final int n) {
		int k = n;

		if (k == 0) {
			return 1;
		}

		k--;

		for (int i = 1; i < 32; i <<= 1) {
			k = k | k >> i;
		}

		return k + 1;
	}

	public static final int sum(final int[] pValues) {
		int sum = 0;
		for(int i = pValues.length - 1; i >= 0; i--) {
			sum += pValues[i];
		}

		return sum;
	}

	public static final void arraySumInternal(final int[] pValues) {
		final int valueCount = pValues.length;
		for(int i = 1; i < valueCount; i++) {
			pValues[i] = pValues[i-1] + pValues[i];
		}
	}

	public static final void arraySumInternal(final long[] pValues) {
		final int valueCount = pValues.length;
		for(int i = 1; i < valueCount; i++) {
			pValues[i] = pValues[i-1] + pValues[i];
		}
	}

	public static final void arraySumInternal(final long[] pValues, final long pFactor) {
		pValues[0] = pValues[0] * pFactor;
		final int valueCount = pValues.length;
		for(int i = 1; i < valueCount; i++) {
			pValues[i] = pValues[i-1] + pValues[i] * pFactor;
		}
	}

	public static final float arraySum(final float[] pValues) {
		float sum = 0;
		final int valueCount = pValues.length;
		for(int i = 0; i < valueCount; i++) {
			sum += pValues[i];
		}
		return sum;
	}

	public static final float arrayAverage(final float[] pValues) {
		return arraySum(pValues) / pValues.length;
	}

	public static float[] rotateAroundCenter(final float[] pVertices, final float pRotation, final float pRotationCenterX, final float pRotationCenterY) {
		if(pRotation != 0) {
			final float rotationRad = MathUtils.degToRad(pRotation);
			final float sinRotationRad = FloatMath.sin(rotationRad);
			final float cosRotationInRad = FloatMath.cos(rotationRad);

			for(int i = pVertices.length - 2; i >= 0; i -= 2) {
				final float pX = pVertices[i];
				final float pY = pVertices[i + 1];
				pVertices[i] = pRotationCenterX + (cosRotationInRad * (pX - pRotationCenterX) - sinRotationRad * (pY - pRotationCenterY));
				pVertices[i + 1] = pRotationCenterY + (sinRotationRad * (pX - pRotationCenterX) + cosRotationInRad * (pY - pRotationCenterY));
			}
		}
		return pVertices;
	}

	public static float[] scaleAroundCenter(final float[] pVertices, final float pScaleX, final float pScaleY, final float pScaleCenterX, final float pScaleCenterY) {
		if(pScaleX != 1 || pScaleY != 1) {
			for(int i = pVertices.length - 2; i >= 0; i -= 2) {
				pVertices[i] = pScaleCenterX + (pVertices[i] - pScaleCenterX) * pScaleX;
				pVertices[i + 1] = pScaleCenterY + (pVertices[i + 1] - pScaleCenterY) * pScaleY;
			}
		}

		return pVertices;
	}

	public static float[] rotateAndScaleAroundCenter(final float[] pVertices, final float pRotation, final float pRotationCenterX, final float pRotationCenterY, final float pScaleX, final float pScaleY, final float pScaleCenterX, final float pScaleCenterY) {
		rotateAroundCenter(pVertices, pRotation, pRotationCenterX, pRotationCenterY);
		return scaleAroundCenter(pVertices, pScaleX, pScaleY, pScaleCenterX, pScaleCenterY);
	}

	public static float[] revertScaleAroundCenter(final float[] pVertices, final float pScaleX, final float pScaleY, final float pScaleCenterX, final float pScaleCenterY) {
		return scaleAroundCenter(pVertices, 1 / pScaleX, 1 / pScaleY, pScaleCenterX, pScaleCenterY);
	}

	public static float[] revertRotateAroundCenter(final float[] pVertices, final float pRotation, final float pRotationCenterX, final float pRotationCenterY) {
		return rotateAroundCenter(pVertices, -pRotation, pRotationCenterX, pRotationCenterY);
	}

	public static float[] revertRotateAndScaleAroundCenter(final float[] pVertices, final float pRotation, final float pRotationCenterX, final float pRotationCenterY, final float pScaleX, final float pScaleY, final float pScaleCenterX, final float pScaleCenterY) {
		revertScaleAroundCenter(pVertices, pScaleX, pScaleY, pScaleCenterX, pScaleCenterY);
		return revertRotateAroundCenter(pVertices, pRotation, pRotationCenterX, pRotationCenterY);
	}

	public static int bringToBounds(final int pMinValue, final int pMaxValue, final float pValue) {
		return (int)Math.max(pMinValue, Math.min(pMaxValue, pValue));
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
