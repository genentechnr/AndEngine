package org.anddev.andengine.entity.layer.tiled.tmx;

import java.util.ArrayList;

import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.anddev.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

/**
 * @author Nicolas Gramlich
 * @since 11:21:01 - 29.07.2010
 */
public class TMXObject implements TMXConstants {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final String mName;
	private final String mType;
	private final int mX;
	private final int mY;
	private final int mWidth;
	private final int mHeight;
	private final ArrayList<TMXObjectProperty> mTMXObjectProperties = new ArrayList<TMXObjectProperty>();

	// ===========================================================
	// Constructors
	// ===========================================================

	public TMXObject(final Attributes pAttributes) {
		this.mName = pAttributes.getValue("", TAG_OBJECT_ATTRIBUTE_NAME);
		this.mType = pAttributes.getValue("", TAG_OBJECT_ATTRIBUTE_TYPE);
		this.mX = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_OBJECT_ATTRIBUTE_X);
		this.mY = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_OBJECT_ATTRIBUTE_Y);
		this.mWidth = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_OBJECT_ATTRIBUTE_WIDTH);
		this.mHeight = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_OBJECT_ATTRIBUTE_HEIGHT);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public String getName() {
		return this.mName;
	}

	public String getType() {
		return this.mType;
	}

	public int getX() {
		return this.mX;
	}

	public int getY() {
		return this.mY;
	}

	public int getWidth() {
		return this.mWidth;
	}

	public int getHeight() {
		return this.mHeight;
	}

	public void addTMXObjectProperty(final TMXObjectProperty pTMXObjectProperty) {
		this.mTMXObjectProperties.add(pTMXObjectProperty); 
	}
	
	public ArrayList<TMXObjectProperty> getTMXObjectProperties() {
		return this.mTMXObjectProperties;
	}

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
