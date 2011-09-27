package org.anddev.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.anddev.andengine.opengl.texture.compressed.pvr.PVRTexture;

/**
 * (c) Zynga 2011
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 11:26:07 - 27.09.2011
 */
public interface IPVRTexturePixelBufferStrategy {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public abstract IPVRTexturePixelBufferStrategyBufferManager newPVRTexturePixelBufferStrategyManager(final PVRTexture pPVRTexture) throws IOException;

	public abstract void loadPVRTextureData(final IPVRTexturePixelBufferStrategyBufferManager pPVRTexturePixelBufferStrategyManager, final int pWidth, final int pHeight, final int pBytesPerPixel, final int pGLFormat, final int pGLType, final int pMipmapLevel, final int pCurrentPixelDataOffset, final int pCurrentPixelDataSize) throws IOException;

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static interface IPVRTexturePixelBufferStrategyBufferManager {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		public ByteBuffer getPixelBuffer(final int pStart, final int pByteCount) throws IOException;
	}
}

