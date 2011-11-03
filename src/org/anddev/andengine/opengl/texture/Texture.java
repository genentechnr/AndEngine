package org.anddev.andengine.opengl.texture;

import java.io.IOException;

import org.anddev.andengine.opengl.texture.atlas.source.ITextureAtlasSource;
import org.anddev.andengine.opengl.util.GLState;

import android.opengl.GLES20;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 14:55:02 - 08.03.2010
 */
public abstract class Texture implements ITexture {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected final PixelFormat mPixelFormat;
	protected final TextureOptions mTextureOptions;

	protected int mTextureID = -1;
	protected boolean mLoadedToHardware;
	protected boolean mUpdateOnHardwareNeeded = false;

	protected final ITextureStateListener mTextureStateListener;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pPixelFormat
	 * @param pTextureOptions the (quality) settings of the Texture.
	 * @param pTextureStateListener to be informed when this {@link Texture} is loaded, unloaded or a {@link ITextureAtlasSource} failed to load.
	 */
	public Texture(final PixelFormat pPixelFormat, final TextureOptions pTextureOptions, final ITextureStateListener pTextureStateListener) throws IllegalArgumentException {
		this.mPixelFormat = pPixelFormat;
		this.mTextureOptions = pTextureOptions;
		this.mTextureStateListener = pTextureStateListener;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	@Override
	public int getHardwareTextureID() {
		return this.mTextureID;
	}

	@Override
	public boolean isLoadedToHardware() {
		return this.mLoadedToHardware;
	}

	@Override
	public void setLoadedToHardware(final boolean pLoadedToHardware) {
		this.mLoadedToHardware = pLoadedToHardware;
	}

	@Override
	public boolean isUpdateOnHardwareNeeded() {
		return this.mUpdateOnHardwareNeeded;
	}

	@Override
	public void setUpdateOnHardwareNeeded(final boolean pUpdateOnHardwareNeeded) {
		this.mUpdateOnHardwareNeeded = pUpdateOnHardwareNeeded;
	}
	
	public PixelFormat getPixelFormat() {
		return this.mPixelFormat;
	}

	@Override
	public TextureOptions getTextureOptions() {
		return this.mTextureOptions;
	}

	@Override
	public ITextureStateListener getTextureStateListener() {
		return this.mTextureStateListener;
	}

	@Override
	public boolean hasTextureStateListener() {
		return this.mTextureStateListener != null;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	protected abstract void writeTextureToHardware() throws IOException;

	@Override
	public void load() {
		TextureManager.loadTexture(this);
	}

	@Override
	public void unload() {
		TextureManager.unloadTexture(this);
	}

	@Override
	public void loadToHardware() throws IOException {
		GLState.enableTextures();

		this.mTextureID = GLState.generateTexture();

		GLState.bindTexture(this.mTextureID);

		this.writeTextureToHardware();
		
		this.mTextureOptions.apply();

		this.mUpdateOnHardwareNeeded = false;
		this.mLoadedToHardware = true;

		if(this.mTextureStateListener != null) {
			this.mTextureStateListener.onLoadedToHardware(this);
		}
	}

	@Override
	public void unloadFromHardware() {
		GLState.enableTextures();

		GLState.deleteTexture(this.mTextureID);

		this.mTextureID = -1;

		this.mLoadedToHardware = false;

		if(this.mTextureStateListener != null) {
			this.mTextureStateListener.onUnloadedFromHardware(this);
		}
	}

	@Override
	public void reloadToHardware() throws IOException {
		this.unloadFromHardware();
		this.loadToHardware();
	}

	@Override
	public void bind() {
		GLState.bindTexture(this.mTextureID);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public enum PixelFormat {
		// ===========================================================
		// Elements
		// ===========================================================

		UNDEFINED(-1, -1, -1),
		RGBA_4444(GLES20.GL_RGBA, GLES20.GL_UNSIGNED_SHORT_4_4_4_4, 16),
		RGBA_5551(GLES20.GL_RGBA, GLES20.GL_UNSIGNED_SHORT_5_5_5_1, 16),
		RGBA_8888(GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, 32),
		RGB_565(GLES20.GL_RGB, GLES20.GL_UNSIGNED_SHORT_5_6_5, 16),
		A_8(GLES20.GL_ALPHA, GLES20.GL_UNSIGNED_BYTE, 8),
		I_8(GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, 8),
		AI_88(GLES20.GL_LUMINANCE_ALPHA, GLES20.GL_UNSIGNED_BYTE, 16);

		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		private final int mGLFormat;
		private final int mGLType;
		private final int mBitsPerPixel;

		// ===========================================================
		// Constructors
		// ===========================================================

		private PixelFormat(final int pGLFormat, final int pGLType, final int pBitsPerPixel) {
			this.mGLFormat = pGLFormat;
			this.mGLType = pGLType;
			this.mBitsPerPixel = pBitsPerPixel;
		}

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		public int getGLFormat() {
			return this.mGLFormat;
		}

		public int getGLType() {
			return this.mGLType;
		}

		public int getBitsPerPixel() {
			return this.mBitsPerPixel;
		}

		// ===========================================================
		// Methods from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}