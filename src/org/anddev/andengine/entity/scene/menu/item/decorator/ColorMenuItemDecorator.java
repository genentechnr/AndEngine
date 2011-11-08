package org.anddev.andengine.entity.scene.menu.item.decorator;

import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.opengl.Mesh;
import org.anddev.andengine.util.color.Color;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 14:25:35 - 07.07.2010
 */
public class ColorMenuItemDecorator extends BaseMenuItemDecorator<Mesh> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Color mSelectedColor;
	private final Color mUnselectedColor;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ColorMenuItemDecorator(final IMenuItem<Mesh> pMenuItem, final Color pSelectedColor, final Color pUnselectedColor) {
		super(pMenuItem);

		this.mSelectedColor = pSelectedColor;
		this.mUnselectedColor = pUnselectedColor;

		pMenuItem.setColor(pUnselectedColor);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onMenuItemSelected(final IMenuItem<Mesh> pMenuItem) {
		pMenuItem.setColor(this.mSelectedColor);
	}

	@Override
	public void onMenuItemUnselected(final IMenuItem<Mesh> pMenuItem) {
		pMenuItem.setColor(this.mUnselectedColor);
	}

	@Override
	public void onMenuItemReset(final IMenuItem<Mesh> pMenuItem) {
		pMenuItem.setColor(this.mUnselectedColor);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
