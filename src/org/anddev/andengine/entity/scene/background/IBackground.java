package org.anddev.andengine.entity.scene.background;

import org.anddev.andengine.engine.handler.IDrawHandler;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.util.color.Color;
import org.anddev.andengine.util.modifier.IModifier;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 13:47:41 - 19.07.2010
 */
public interface IBackground extends IDrawHandler, IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void addBackgroundModifier(final IModifier<IBackground> pBackgroundModifier);
	public boolean removeBackgroundModifier(final IModifier<IBackground> pBackgroundModifier);
	public void clearBackgroundModifiers();

	public boolean isColorEnabled();
	public void setColorEnabled(final boolean pColorEnabled);

	public void setColor(final Color pColor);
	public void setColor(final float pRed, final float pGreen, final float pBlue);
	public void setColor(final float pRed, final float pGreen, final float pBlue, final float pAlpha);
}
