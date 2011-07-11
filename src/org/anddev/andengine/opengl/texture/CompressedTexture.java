package org.anddev.andengine.opengl.texture;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.source.ITextureSource;

import android.opengl.ETC1Util;

/**
 * @author Nicolas Gramlich
 * @since 14:55:02 - 08.03.2010
 */
public abstract class CompressedTexture extends BaseTexture {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final int mWidth;
	private final int mHeight;
	private final TextureCompression mTextureCompression;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Uses {@link TextureCompression#ETC1}.
	 *
	 * @param pWidth must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pHeight must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 */
	public CompressedTexture(final int pWidth, final int pHeight) {
		this(pWidth, pHeight, TextureCompression.ETC1);
	}

	/**
	 * @param pWidth must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pHeight must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pTextureCompression use {@link TextureCompression#RGBA_8888} for {@link CompressedTexture}s with transparency and {@link TextureCompression#RGB_565} for {@link CompressedTexture}s without transparency.
	 */
	public CompressedTexture(final int pWidth, final int pHeight, final TextureCompression pTextureCompression) {
		this(pWidth, pHeight, pTextureCompression, TextureOptions.DEFAULT, null);
	}

	/**
	 * Uses {@link TextureCompression#ETC1}.
	 *
	 * @param pWidth must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pHeight must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pTextureStateListener to be informed when this {@link CompressedTexture} is loaded, unloaded or a {@link ITextureSource} failed to load.
	 */
	public CompressedTexture(final int pWidth, final int pHeight, final ITextureStateListener pTextureStateListener) {
		this(pWidth, pHeight, TextureCompression.ETC1, TextureOptions.DEFAULT, pTextureStateListener);
	}

	/**
	 * @param pWidth must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pHeight must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pTextureCompression use {@link TextureCompression#RGBA_8888} for {@link CompressedTexture}s with transparency and {@link TextureCompression#RGB_565} for {@link CompressedTexture}s without transparency.
	 * @param pTextureStateListener to be informed when this {@link CompressedTexture} is loaded, unloaded or a {@link ITextureSource} failed to load.
	 */
	public CompressedTexture(final int pWidth, final int pHeight, final TextureCompression pTextureCompression, final ITextureStateListener pTextureStateListener) {
		this(pWidth, pHeight, pTextureCompression, TextureOptions.DEFAULT, pTextureStateListener);
	}

	/**
	 * Uses {@link TextureCompression#ETC1}.
	 *
	 * @param pWidth must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pHeight must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pTextureOptions the (quality) settings of the Texture.
	 */
	public CompressedTexture(final int pWidth, final int pHeight, final TextureOptions pTextureOptions) throws IllegalArgumentException {
		this(pWidth, pHeight, TextureCompression.ETC1, pTextureOptions, null);
	}

	/**
	 * @param pWidth must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pHeight must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pTextureCompression use {@link TextureCompression#RGBA_8888} for {@link CompressedTexture}s with transparency and {@link TextureCompression#RGB_565} for {@link CompressedTexture}s without transparency.
	 * @param pTextureOptions the (quality) settings of the Texture.
	 */
	public CompressedTexture(final int pWidth, final int pHeight, final TextureCompression pTextureCompression, final TextureOptions pTextureOptions) throws IllegalArgumentException {
		this(pWidth, pHeight, pTextureCompression, pTextureOptions, null);
	}

	/**
	 * Uses {@link TextureCompression#ETC1}.
	 *
	 * @param pWidth must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pHeight must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pTextureOptions the (quality) settings of the Texture.
	 * @param pTextureStateListener to be informed when this {@link CompressedTexture} is loaded, unloaded or a {@link ITextureSource} failed to load.
	 */
	public CompressedTexture(final int pWidth, final int pHeight, final TextureOptions pTextureOptions, final ITextureStateListener pTextureStateListener) throws IllegalArgumentException {
		this(pWidth, pHeight, TextureCompression.ETC1, pTextureOptions, pTextureStateListener);
	}

	/**
	 * @param pWidth must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pHeight must be a power of 2 (i.e. 32, 64, 128, 256, 512, 1024).
	 * @param pTextureCompression use {@link TextureCompression#RGBA_8888} for {@link CompressedTexture}s with transparency and {@link TextureCompression#RGB_565} for {@link CompressedTexture}s without transparency.
	 * @param pTextureOptions the (quality) settings of the Texture.
	 * @param pTextureStateListener to be informed when this {@link CompressedTexture} is loaded, unloaded or a {@link ITextureSource} failed to load.
	 */
	public CompressedTexture(final int pWidth, final int pHeight, final TextureCompression pTextureCompression, final TextureOptions pTextureOptions, final ITextureStateListener pTextureStateListener) throws IllegalArgumentException {
		super(pTextureOptions, pTextureStateListener);
		this.mTextureCompression = pTextureCompression;

		this.mWidth = pWidth;
		this.mHeight = pHeight;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public TextureCompression getTextureCompression() {
		return this.mTextureCompression;
	}

	@Override
	public int getWidth() {
		return this.mWidth;
	}

	@Override
	public int getHeight() {
		return this.mHeight;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	protected abstract InputStream getInputStream();

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	protected final void writeTextureToHardware(final GL10 pGL) throws IOException {
		this.writeTextureToHardware(pGL, this.getInputStream());
	}

	protected final void writeTextureToHardware(final GL10 pGL, final InputStream pInputStream) throws IOException {
		switch(this.mTextureCompression) {
		case ETC1:
			ETC1Util.loadTexture(GL10.GL_TEXTURE_2D, 0, 0, GL10.GL_RGB, GL10.GL_UNSIGNED_SHORT_5_6_5, pInputStream);
			break;
		default:
			throw new IllegalArgumentException("Unexpected textureCompression: '" + this.mTextureCompression + "'.");
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public enum TextureCompression {
		// ===========================================================
		// Elements
		// ===========================================================

		ETC1;

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
