package org.anddev.andengine.util.pool;

/**
 * @author Valentin Milea
 * @author Nicolas Gramlich
 * 
 * @since 23:02:47 - 21.08.2010
 */
public abstract class PoolItem {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	Pool<? extends PoolItem> mParent;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Pool<? extends PoolItem> getParent() {
		return this.mParent;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public boolean isFromPool(final Pool<? extends PoolItem> pPool) {
		return pPool == this.mParent;
	}

	protected void onRecycle() {

	}

	public void recycle() {
		if(this.mParent == null) {
			throw new IllegalStateException("Item already recycled!");
		}

		this.mParent.recycle(this);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}