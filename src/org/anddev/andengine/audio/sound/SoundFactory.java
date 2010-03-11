package org.anddev.andengine.audio.sound;

import java.io.IOException;


import android.content.Context;

/**
 * @author Nicolas Gramlich
 * @since 14:23:03 - 11.03.2010
 */
public class SoundFactory {
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
	
	public static Sound createSoundFromAsset(final SoundManager pSoundManager, final Context pContext, final String pAssetPath) throws IOException {
		final int soundID = pSoundManager.getSoundPool().load(pContext.getAssets().openFd(pAssetPath), 1);
		final Sound sound = new Sound(pSoundManager, soundID);
		pSoundManager.addSound(sound);
		return sound;
	}
	
	public static Sound createSoundFromResource(final SoundManager pSoundManager, final Context pContext, final int pSoundResID) {
		final int soundID = pSoundManager.getSoundPool().load(pContext, pSoundResID, 1);
		final Sound sound = new Sound(pSoundManager, soundID);
		pSoundManager.addSound(sound);
		return sound;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
