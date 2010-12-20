package org.anddev.andengine.entity.layer;

import java.util.ArrayList;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;

/**
 * @author Nicolas Gramlich
 * @since 00:13:59 - 23.07.2010
 */
public abstract class BaseLayer extends Entity implements ILayer {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public BaseLayer() {

	}

	public BaseLayer(final int pZIndex) {
		super(pZIndex);
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

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
