package org.anddev.andengine.opengl.vertex;

import org.anddev.andengine.opengl.util.FastFloatBuffer;

/**
 * @author Nicolas Gramlich
 * @since 13:07:25 - 13.03.2010
 */
public class RectangleVertexBuffer extends VertexBuffer {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final int VERTICES_PER_RECTANGLE = 4;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public RectangleVertexBuffer(final int pDrawType) {
		super(2 * VERTICES_PER_RECTANGLE, pDrawType);
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

	public synchronized void update(final float pX, final float pY, final float pWidth, final float pHeight) {
		// TODO First parameters are always Zero...
		final float x2 = pX + pWidth;
		final float y2 = pY + pHeight;

		final FastFloatBuffer buffer = this.getFloatBuffer();
		buffer.position(0);

		// TODO Maybe use put(float []) instead of put(float) ...
		buffer.put(pX);
		buffer.put(pY);

		buffer.put(pX);
		buffer.put(y2);

		buffer.put(x2);
		buffer.put(pY);

		buffer.put(x2);
		buffer.put(y2);

		buffer.position(0);

		super.setHardwareBufferNeedsUpdate();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
