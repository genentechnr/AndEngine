package org.anddev.andengine.entity.shape.modifier;

import org.anddev.andengine.entity.shape.IModifierListener;
import org.anddev.andengine.entity.shape.IShapeModifier;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.shape.modifier.util.ShapeModifierUtils;

/**
 * @author Nicolas Gramlich
 * @since 19:39:25 - 19.03.2010
 */
public class SequenceModifier implements IShapeModifier {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private IModifierListener mModiferListener;
	private final IShapeModifier[] mShapeModifiers;
	private int mCurrentShapeModifier;
	private boolean mExpired;
	private final float mDuration;

	// ===========================================================
	// Constructors
	// ===========================================================

	public SequenceModifier(final IShapeModifier ... pShapeModifiers) {
		this(null, pShapeModifiers);
	}

	public SequenceModifier(final IModifierListener pModiferListener, final IShapeModifier ... pShapeModifiers) {
		assert(pShapeModifiers.length > 0);

		this.mModiferListener = pModiferListener;
		this.mShapeModifiers = pShapeModifiers;

		this.mDuration = ShapeModifierUtils.getSequenceDurationOfShapeModifier(pShapeModifiers);

		pShapeModifiers[0].setModiferListener(new InternalModifierListener());
	}

	public SequenceModifier(final SequenceModifier pSequenceModifier) {
		this.mModiferListener = pSequenceModifier.mModiferListener;

		this.mDuration = pSequenceModifier.mDuration;

		final IShapeModifier[] otherShapeModifiers = pSequenceModifier.mShapeModifiers;
		this.mShapeModifiers = new IShapeModifier[otherShapeModifiers.length];

		final IShapeModifier[] shapeModifiers = this.mShapeModifiers;
		for(int i = shapeModifiers.length - 1; i >= 0; i--) {
			shapeModifiers[i] = otherShapeModifiers[i].clone();
		}

		shapeModifiers[0].setModiferListener(new InternalModifierListener());
	}

	@Override
	public SequenceModifier clone(){
		return new SequenceModifier(this);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public boolean isExpired() {
		return this.mExpired;
	}

	public void setExpired(final boolean pExpired) {
		this.mExpired = pExpired;
	}

	public IModifierListener getModiferListener() {
		return this.mModiferListener;
	}

	public void setModiferListener(final IModifierListener pModiferListener) {
		this.mModiferListener = pModiferListener;
	}

	@Override
	public float getDuration() {
		return this.mDuration;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onUpdateShape(final float pSecondsElapsed, final Shape pShape) {
		if(!this.isExpired()) {
			this.mShapeModifiers[this.mCurrentShapeModifier].onUpdateShape(pSecondsElapsed, pShape);
		}
	}

	@Override
	public void reset() {
		this.mCurrentShapeModifier = 0;
		this.mExpired = false;

		final IShapeModifier[] shapeModifiers = this.mShapeModifiers;
		for(int i = shapeModifiers.length - 1; i >= 0; i--) {
			shapeModifiers[i].reset();
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	private class InternalModifierListener implements IModifierListener  {
		@Override
		public void onModifierFinished(final IShapeModifier pShapeModifier, final Shape pShape) {
			final SequenceModifier wrappingSequenceModifier = SequenceModifier.this;
			wrappingSequenceModifier.mCurrentShapeModifier++;
			if(wrappingSequenceModifier.mCurrentShapeModifier < wrappingSequenceModifier.mShapeModifiers.length) {
				wrappingSequenceModifier.mShapeModifiers[wrappingSequenceModifier.mCurrentShapeModifier].setModiferListener(this);
			} else {
				wrappingSequenceModifier.setExpired(true);
				if(wrappingSequenceModifier.mModiferListener != null) {
					wrappingSequenceModifier.mModiferListener.onModifierFinished(wrappingSequenceModifier, pShape);
				}
			}
		}
	}
}
