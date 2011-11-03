package org.anddev.andengine.opengl.font;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 10:30:22 - 03.04.2010
 */
public class Letter {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public final char mCharacter;
	public final int mTextureX;
	public final int mTextureY;
	public final int mTextureWidth;
	public final int mTextureHeight;
	public final float mAdvance;
	public final float mOffsetX;
	public final float mOffsetY;
	public final float mU;
	public final float mV;
	public final float mU2;
	public final float mV2;

	// ===========================================================
	// Constructors
	// ===========================================================

	Letter(final char pCharacter, final int pTextureX, final int pTextureY, final int pTextureWidth, final int pTextureHeight, final float pAdvance, final float pOffsetX, final float pOffsetY, final float pU, final float pV, final float pU2, final float pV2) {
		this.mCharacter = pCharacter;
		this.mAdvance = pAdvance;
		this.mTextureWidth = pTextureWidth;
		this.mTextureHeight = pTextureHeight;
		this.mTextureX = pTextureX;
		this.mTextureY = pTextureY;
		this.mOffsetX = pOffsetX;
		this.mOffsetY = pOffsetY;
		this.mU = pU;
		this.mV = pV;
		this.mU2 = pU2;
		this.mV2 = pV2;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.mCharacter;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		final Letter other = (Letter) obj;
		if(this.mCharacter != other.mCharacter) {
			return false;
		}
		return true;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}