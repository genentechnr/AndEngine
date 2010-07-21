package org.anddev.andengine.entity.layer.tiled.tmx.util.constants;

/**
 * @author Nicolas Gramlich
 * @since 19:20:22 - 20.07.2010
 */
public interface TMXConstants {
	// ===========================================================
	// Final Fields
	// ===========================================================

	public static final int BYTES_PER_GLOBALTILEID = 4;
	
	public static final String TAG_MAP = "map";
	public static final String TAG_MAP_ATTRIBUTE_ORIENTATION = "orientation";
	public static final String TAG_MAP_ATTRIBUTE_ORIENTATION_VALUE_ORTHOGONAL = "orthogonal";
	public static final String TAG_MAP_ATTRIBUTE_ORIENTATION_VALUE_ISOMETRIC = "isometric";
	public static final String TAG_MAP_ATTRIBUTE_WIDTH = "width";
	public static final String TAG_MAP_ATTRIBUTE_HEIGHT = "height";
	public static final String TAG_MAP_ATTRIBUTE_TILEWIDTH = "tilewidth";
	public static final String TAG_MAP_ATTRIBUTE_TILEHEIGHT = "tileheight";
	
	public static final String TAG_TILESET = "tileset";
	public static final String TAG_TILESET_ATTRIBUTE_FIRSTGID = "firstgid";
	public static final String TAG_TILESET_ATTRIBUTE_NAME = "name";
	public static final String TAG_TILESET_ATTRIBUTE_TILEWIDTH = "tilewidth";
	public static final String TAG_TILESET_ATTRIBUTE_TILEHEIGHT = "tileheight";
	public static final String TAG_TILESET_ATTRIBUTE_SPACING = "spacing";
	public static final String TAG_TILESET_ATTRIBUTE_MARGIN = "margin";

	public static final String TAG_IMAGE = "image";
	public static final String TAG_IMAGE_ATTRIBUTE_SOURCE = "source";

	public static final String TAG_TILE = "tile";
	public static final String TAG_TILE_ATTRIBUTE_ID = "id";

	public static final String TAG_PROPERTIES = "properties";
	
	public static final String TAG_PROPERTY = "property";
	public static final String TAG_PROPERTY_ATTRIBUTE_NAME = "name";
	public static final String TAG_PROPERTY_ATTRIBUTE_VALUE = "value";
	
	public static final String TAG_LAYER = "layer";
	public static final String TAG_LAYER_ATTRIBUTE_NAME = "name";
	public static final String TAG_LAYER_ATTRIBUTE_WIDTH = "width";
	public static final String TAG_LAYER_ATTRIBUTE_HEIGHT = "height";
	
	public static final String TAG_DATA = "data";
	public static final String TAG_DATA_ATTRIBUTE_ENCODING = "encoding";
	public static final String TAG_DATA_ATTRIBUTE_ENCODING_VALUE_BASE64 = "base64";
	public static final String TAG_DATA_ATTRIBUTE_COMPRESSION = "compression";
	public static final String TAG_DATA_ATTRIBUTE_COMPRESSION_VALUE_GZIP = "gzip";

	// ===========================================================
	// Methods
	// ===========================================================
}
