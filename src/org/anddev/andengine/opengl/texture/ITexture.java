package org.anddev.andengine.opengl.texture;

import java.io.IOException;

import org.anddev.andengine.opengl.texture.atlas.source.ITextureAtlasSource;
import org.anddev.andengine.util.debug.Debug;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 15:01:03 - 11.07.2011
 */
public interface ITexture {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public int getWidth();
	public int getHeight();

	public int getHardwareTextureID();

	public boolean isLoadedToHardware();
	public void setLoadedToHardware(final boolean pLoadedToHardware);

	public boolean isUpdateOnHardwareNeeded();
	public void setUpdateOnHardwareNeeded(final boolean pUpdateOnHardwareNeeded);

	/**
	 * @return itself for method chaining.
	 */
	public ITexture load();
	/**
	 * @return itself for method chaining.
	 */
	public ITexture unload();

	public void loadToHardware() throws IOException;
	public void unloadFromHardware();
	public void reloadToHardware() throws IOException;

	public void bind();

	public TextureOptions getTextureOptions();

	public boolean hasTextureStateListener();
	public ITextureStateListener getTextureStateListener();

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public static interface ITextureStateListener {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		public void onLoadedToHardware(final ITexture pTexture);
		public void onUnloadedFromHardware(final ITexture pTexture);

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================

		public static class TextureStateAdapter<T extends ITextureAtlasSource> implements ITextureStateListener {
			@Override
			public void onLoadedToHardware(final ITexture pTexture) { }

			@Override
			public void onUnloadedFromHardware(final ITexture pTexture) { }
		}

		public static class DebugTextureStateListener<T extends ITextureAtlasSource> implements ITextureStateListener {
			@Override
			public void onLoadedToHardware(final ITexture pTexture) {
				Debug.d("Texture loaded: " + pTexture.toString());
			}

			@Override
			public void onUnloadedFromHardware(final ITexture pTexture) {
				Debug.d("Texture unloaded: " + pTexture.toString());
			}
		}
	}
}