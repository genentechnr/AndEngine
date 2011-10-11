package org.anddev.andengine.util.spatial.adt.bounds;

import org.anddev.andengine.util.exception.AndEngineException;
import org.anddev.andengine.util.spatial.adt.bounds.source.IBoundsSource;

/**
 * (c) Zynga 2011
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 16:19:39 - 08.10.2011
 */
public interface IBounds<S extends IBoundsSource> extends IBoundsSource {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void set(final IBoundsSource pBoundsSource);
	public boolean isEmpty();
	public boolean contains(final S pBoundsSource);
	public boolean contains(final BoundsSplit pBoundsSplit, final S pBoundsSource);
	public boolean contains(final IBounds<S> pBounds);
	public boolean intersects(final S pBoundsSource);
	public boolean intersects(final IBounds<S> pBounds);
	public IBounds<S> split(final BoundsSplit pBoundsSplit) throws BoundsSplitException;

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static enum BoundsSplit {
		// ===========================================================
		// Elements
		// ===========================================================

		TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

		// ===========================================================
		// Constants
		// ===========================================================

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

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}

	public class BoundsSplitException extends AndEngineException {
		// ===========================================================
		// Constants
		// ===========================================================

		private static final long serialVersionUID = 7970869239897412727L;

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

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}
