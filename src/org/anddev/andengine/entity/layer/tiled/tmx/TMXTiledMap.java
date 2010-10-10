package org.anddev.andengine.entity.layer.tiled.tmx;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.microedition.khronos.opengles.GL11;

import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.util.HashtableUtils;
import org.xml.sax.Attributes;

import android.util.SparseArray;

/**
 * @author Nicolas Gramlich
 * @since 19:38:11 - 20.07.2010
 */
public class TMXTiledMap implements TMXConstants {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final String mOrientation;
	private final int mTileColumns;
	private final int mTilesRows;
	private final int mTileWidth;
	private final int mTileHeight;
	// Custom properties
	private final Hashtable<String, String> mAttributes = new Hashtable<String, String>();

	private final ArrayList<TMXTileSet> mTMXTileSets = new ArrayList<TMXTileSet>();
	private final ArrayList<TMXLayer> mTMXLayers = new ArrayList<TMXLayer>();
	private final ArrayList<TMXObjectGroup> mTMXObjectGroups = new ArrayList<TMXObjectGroup>();

	private final RectangleVertexBuffer mSharedVertexBuffer;

	private final SparseArray<TextureRegion> mGlobalTileIDToTextureRegionCache = new SparseArray<TextureRegion>();
	private final SparseArray<TMXProperties<TMXTileProperty>> mGlobalTileIDToTMXTilePropertiesCache = new SparseArray<TMXProperties<TMXTileProperty>>();

	// ===========================================================
	// Constructors
	// ===========================================================

	TMXTiledMap(final Attributes pAttributes) {
		for(int i = 0; i < pAttributes.getLength(); i++) {
			this.mAttributes.put(pAttributes.getLocalName(i), pAttributes.getValue(i));
		}	
		
		this.mOrientation = this.getAttribute(TAG_MAP_ATTRIBUTE_ORIENTATION, null);
		if(this.mOrientation.equals(TAG_MAP_ATTRIBUTE_ORIENTATION_VALUE_ORTHOGONAL) == false) {
			throw new IllegalArgumentException(TAG_MAP_ATTRIBUTE_ORIENTATION + ": '" + this.mOrientation + "' is not supported.");
		}
		this.mTileColumns = HashtableUtils.getIntValueOrThrow(this.mAttributes, TAG_MAP_ATTRIBUTE_WIDTH);
		this.mTilesRows = HashtableUtils.getIntValueOrThrow(this.mAttributes, TAG_MAP_ATTRIBUTE_HEIGHT);
		this.mTileWidth = HashtableUtils.getIntValueOrThrow(this.mAttributes, TAG_MAP_ATTRIBUTE_TILEWIDTH);
		this.mTileHeight = HashtableUtils.getIntValueOrThrow(this.mAttributes, TAG_MAP_ATTRIBUTE_TILEHEIGHT);

		this.mSharedVertexBuffer = new RectangleVertexBuffer(GL11.GL_STATIC_DRAW);
		BufferObjectManager.getActiveInstance().loadBufferObject(this.mSharedVertexBuffer);
		this.mSharedVertexBuffer.update(0, 0, this.mTileWidth, this.mTileHeight);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public final String getAttribute(final String pAttributeName, final String pDefaultValue) {
		return this.mAttributes.containsKey(pAttributeName) ? this.mAttributes.get(pAttributeName) : pDefaultValue;
	}
	
	public final String getOrientation() {
		return this.mOrientation;
	}
	/**
	 * Use {@link TMXTiledMap#getTileColumns()} instead.
	 * @return
	 */
	@Deprecated
	public final int getWidth() {
		return this.mTileColumns;
	}
	
	public final int getTileColumns() {
		return this.mTileColumns;
	}

	/**
	 * Use {@link TMXTiledMap#getTileRows()} instead.
	 * @return
	 */
	@Deprecated
	public final int getHeight() {
		return this.mTilesRows;
	}
	
	public final int getTileRows() {
		return this.mTilesRows;
	}

	public final int getTileWidth() {
		return this.mTileWidth;
	}

	public final int getTileHeight() {
		return this.mTileHeight;
	}

	public RectangleVertexBuffer getSharedVertexBuffer() {
		return this.mSharedVertexBuffer;
	}

	void addTMXTileSet(final TMXTileSet pTMXTileSet) {
		this.mTMXTileSets.add(pTMXTileSet);
	}

	public ArrayList<TMXTileSet> getTMXTileSets() {
		return this.mTMXTileSets;
	}

	void addTMXLayer(final TMXLayer pTMXLayer) {
		this.mTMXLayers.add(pTMXLayer);
	}

	public ArrayList<TMXLayer> getTMXLayers() {
		return this.mTMXLayers;
	}

	void addTMXObjectGroup(final TMXObjectGroup pTMXObjectGroup) {
		this.mTMXObjectGroups.add(pTMXObjectGroup);
	}

	public ArrayList<TMXObjectGroup> getTMXObjectGroups() {
		return this.mTMXObjectGroups;
	}

	public TMXProperties<TMXTileProperty> getTMXTilePropertiesByGlobalTileID(final int pGlobalTileID) {
		return this.mGlobalTileIDToTMXTilePropertiesCache.get(pGlobalTileID);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public TMXProperties<TMXTileProperty> getTMXTileProperties(final int pGlobalTileID) {
		final SparseArray<TMXProperties<TMXTileProperty>> globalTileIDToTMXTilePropertiesCache = this.mGlobalTileIDToTMXTilePropertiesCache;

		final TMXProperties<TMXTileProperty> cachedTMXTileProperties = globalTileIDToTMXTilePropertiesCache.get(pGlobalTileID);
		if(cachedTMXTileProperties != null) {
			return cachedTMXTileProperties;
		} else {
			final ArrayList<TMXTileSet> tmxTileSets = this.mTMXTileSets;

			for(int i = tmxTileSets.size() - 1; i >= 0; i--) {
				final TMXTileSet tmxTileSet = tmxTileSets.get(i);
				if(pGlobalTileID >= tmxTileSet.getFirstGlobalTileID()) {
					return tmxTileSet.getTMXTilePropertiesFromGlobalTileID(pGlobalTileID);
				}
			}
			throw new IllegalArgumentException("No TMXTileProperties found for pGlobalTileID=" + pGlobalTileID);
		}
	}

	public TextureRegion getTextureRegionFromGlobalTileID(final int pGlobalTileID) {
		final SparseArray<TextureRegion> globalTileIDToTextureRegionCache = this.mGlobalTileIDToTextureRegionCache;

		final TextureRegion cachedTextureRegion = globalTileIDToTextureRegionCache.get(pGlobalTileID);
		if(cachedTextureRegion != null) {
			return cachedTextureRegion;
		} else {
			final ArrayList<TMXTileSet> tmxTileSets = this.mTMXTileSets;

			for(int i = tmxTileSets.size() - 1; i >= 0; i--) {
				final TMXTileSet tmxTileSet = tmxTileSets.get(i);
				if(pGlobalTileID >= tmxTileSet.getFirstGlobalTileID()) {
					final TextureRegion textureRegion = tmxTileSet.getTextureRegionFromGlobalTileID(pGlobalTileID);
					/* Add to cache for the all future pGlobalTileIDs with the same value. */
					globalTileIDToTextureRegionCache.put(pGlobalTileID, textureRegion);
					return textureRegion;
				}
			}
			throw new IllegalArgumentException("No TextureRegion found for pGlobalTileID=" + pGlobalTileID);
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
