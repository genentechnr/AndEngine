package org.anddev.andengine.opengl.util;

import java.nio.Buffer;

import org.anddev.andengine.engine.options.RenderOptions;
import org.anddev.andengine.opengl.shader.util.constants.ShaderProgramConstants;
import org.anddev.andengine.opengl.texture.Texture.PixelFormat;
import org.anddev.andengine.util.Debug;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLUtils;
import android.opengl.Matrix;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 18:00:43 - 08.03.2010
 */
public class GLState {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int[] HARDWAREID_CONTAINER = new int[1];

	// ===========================================================
	// Fields
	// ===========================================================

	private static int sCurrentHardwareBufferID = -1;
	private static int sCurrentShaderProgramID = -1;
	private static int sCurrentHardwareTextureID = -1;
	private static int sCurrentHardwareFramebufferID = -1;

	private static int sCurrentSourceBlendMode = -1;
	private static int sCurrentDestinationBlendMode = -1;

	private static boolean sEnableDither = true;
	private static boolean sEnableDepthTest = true;

	private static boolean sEnableScissorTest = false;
	private static boolean sEnableBlend = false;
	private static boolean sEnableCulling = false;
	private static boolean sEnableTextures = false;

	private static float sLineWidth = 1;

	private static final GLMatrixStack sModelViewGLMatrixStack = new GLMatrixStack();
	private static final GLMatrixStack sProjectionGLMatrixStack = new GLMatrixStack();

	private static final float[] sModelViewGLMatrix = new float[GLMatrixStack.GLMATRIX_SIZE];
	private static final float[] sProjectionGLMatrix = new float[GLMatrixStack.GLMATRIX_SIZE];
	private static final float[] sModelViewProjectionGLMatrix = new float[GLMatrixStack.GLMATRIX_SIZE];

	// ===========================================================
	// Methods
	// ===========================================================

	public static void reset() {
		GLState.sModelViewGLMatrixStack.reset();
		GLState.sProjectionGLMatrixStack.reset();

		GLState.sCurrentHardwareBufferID = -1;
		GLState.sCurrentShaderProgramID = -1;
		GLState.sCurrentHardwareTextureID = -1;
		GLState.sCurrentHardwareFramebufferID = -1;

		GLState.sCurrentSourceBlendMode = -1;
		GLState.sCurrentDestinationBlendMode = -1;

		GLState.enableDither();
		GLState.enableDepthTest();

		GLState.disableBlend();
		GLState.disableCulling();
		GLState.disableTextures();

		GLES20.glEnableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_POSITION_LOCATION);
		GLES20.glEnableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION);
		GLES20.glEnableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES_LOCATION);

		GLState.sLineWidth = 1;
	}

	public static void enableExtensions(final RenderOptions pRenderOptions) {
		final String version = GLES20.glGetString(GLES20.GL_VERSION);
		final String renderer = GLES20.glGetString(GLES20.GL_RENDERER);
		final String extensions = GLES20.glGetString(GLES20.GL_EXTENSIONS);

		Debug.d("RENDERER: " + renderer);
		Debug.d("VERSION: " + version);
		Debug.d("EXTENSIONS: " + extensions);
	}

	public static void enableScissorTest() {
		if(!GLState.sEnableScissorTest) {
			GLState.sEnableScissorTest = true;
			GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
		}
	}
	public static void disableScissorTest() {
		if(GLState.sEnableScissorTest) {
			GLState.sEnableScissorTest = false;
			GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
		}
	}

	public static void enableBlend() {
		if(!GLState.sEnableBlend) {
			GLState.sEnableBlend = true;
			GLES20.glEnable(GLES20.GL_BLEND);
		}
	}
	public static void disableBlend() {
		if(GLState.sEnableBlend) {
			GLState.sEnableBlend = false;
			GLES20.glDisable(GLES20.GL_BLEND);
		}
	}

	public static void enableCulling() {
		if(!GLState.sEnableCulling) {
			GLState.sEnableCulling = true;
			GLES20.glEnable(GLES20.GL_CULL_FACE);
		}
	}
	public static void disableCulling() {
		if(GLState.sEnableCulling) {
			GLState.sEnableCulling = false;
			GLES20.glDisable(GLES20.GL_CULL_FACE);
		}
	}

	public static void enableTextures() {
		if(!GLState.sEnableTextures) {
			GLState.sEnableTextures = true;
			GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		}
	}
	public static void disableTextures() {
		if(GLState.sEnableTextures) {
			GLState.sEnableTextures = false;
			GLES20.glDisable(GLES20.GL_TEXTURE_2D);
		}
	}

	public static void enableDither() {
		if(!GLState.sEnableDither) {
			GLState.sEnableDither = true;
			GLES20.glEnable(GLES20.GL_DITHER);
		}
	}
	public static void disableDither() {
		if(GLState.sEnableDither) {
			GLState.sEnableDither = false;
			GLES20.glDisable(GLES20.GL_DITHER);
		}
	}

	public static void enableDepthTest() {
		if(!GLState.sEnableDepthTest) {
			GLState.sEnableDepthTest = true;
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		}
	}
	public static void disableDepthTest() {
		if(GLState.sEnableDepthTest) {
			GLState.sEnableDepthTest = false;
			GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		}
	}

	public static int generateBuffer() {
		GLES20.glGenBuffers(1, GLState.HARDWAREID_CONTAINER, 0);
		return GLState.HARDWAREID_CONTAINER[0];
	}

	public static int generateBuffer(final int pSize, final int pUsage) {
		GLES20.glGenBuffers(1, GLState.HARDWAREID_CONTAINER, 0);
		final int hardwareBufferID = GLState.HARDWAREID_CONTAINER[0];

		GLState.bindBuffer(hardwareBufferID);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, pSize, null, pUsage);
		GLState.bindBuffer(0);

		return hardwareBufferID;
	}

	public static void bindBuffer(final int pHardwareBufferID) {
		if(GLState.sCurrentHardwareBufferID != pHardwareBufferID) {
			GLState.sCurrentHardwareBufferID = pHardwareBufferID;
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, pHardwareBufferID);
		}
	}

	public static void deleteBuffer(final int pHardwareBufferID) {
		if(GLState.sCurrentHardwareBufferID == pHardwareBufferID) {
			GLState.sCurrentHardwareBufferID = -1;
		}
		GLState.HARDWAREID_CONTAINER[0] = pHardwareBufferID;
		GLES20.glDeleteBuffers(1, GLState.HARDWAREID_CONTAINER, 0);
	}

	public static int generateFramebuffer() {
		GLES20.glGenFramebuffers(1, GLState.HARDWAREID_CONTAINER, 0);
		return GLState.HARDWAREID_CONTAINER[0];
	}

	public static void bindFramebuffer(final int pFramebufferID) {
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, pFramebufferID);
	}

	public static int getFramebufferStatus() {
		return GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
	}

	public static void checkFramebufferStatus() {
		final int status = GLState.getFramebufferStatus();
		if(status != GLES20.GL_FRAMEBUFFER_COMPLETE) {
			throw new GLException(status);
		}
	}

	public static int getActiveFramebuffer() {
		GLES20.glGetIntegerv(GLES20.GL_FRAMEBUFFER_BINDING, GLState.HARDWAREID_CONTAINER, 0);
		return GLState.HARDWAREID_CONTAINER[0];
	}

	public static void deleteFramebuffer(final int pHardwareFramebufferID) {
		if(GLState.sCurrentHardwareFramebufferID == pHardwareFramebufferID) {
			GLState.sCurrentHardwareFramebufferID = -1;
		}
		GLState.HARDWAREID_CONTAINER[0] = pHardwareFramebufferID;
		GLES20.glDeleteFramebuffers(1, GLState.HARDWAREID_CONTAINER, 0);
	}

	public static void useProgram(final int pShaderProgramID) {
		if(GLState.sCurrentShaderProgramID != pShaderProgramID) {
			GLState.sCurrentShaderProgramID = pShaderProgramID;
			GLES20.glUseProgram(pShaderProgramID);
		}
	}

	public static void deleteProgram(final int pShaderProgramID) {
		if(GLState.sCurrentShaderProgramID == pShaderProgramID) {
			GLState.sCurrentShaderProgramID = -1;
		}
		GLES20.glDeleteProgram(pShaderProgramID);
	}

	public static int generateTexture() {
		GLES20.glGenTextures(1, GLState.HARDWAREID_CONTAINER, 0);
		return GLState.HARDWAREID_CONTAINER[0];
	}

	/**
	 * @see {@link GLState#forceBindTexture(GLES20, int)}
	 * @param GLES20
	 * @param pHardwareTextureID
	 */
	public static void bindTexture(final int pHardwareTextureID) {
		if(GLState.sCurrentHardwareTextureID != pHardwareTextureID) {
			GLState.sCurrentHardwareTextureID = pHardwareTextureID;
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, pHardwareTextureID);
		}
	}

	public static void deleteTexture(final int pHardwareTextureID) {
		if(GLState.sCurrentHardwareTextureID == pHardwareTextureID) {
			GLState.sCurrentHardwareTextureID = -1;
		}
		GLState.HARDWAREID_CONTAINER[0] = pHardwareTextureID;
		GLES20.glDeleteTextures(1, GLState.HARDWAREID_CONTAINER, 0);
	}

	public static void blendFunction(final int pSourceBlendMode, final int pDestinationBlendMode) {
		if(GLState.sCurrentSourceBlendMode != pSourceBlendMode || GLState.sCurrentDestinationBlendMode != pDestinationBlendMode) {
			GLState.sCurrentSourceBlendMode = pSourceBlendMode;
			GLState.sCurrentDestinationBlendMode = pDestinationBlendMode;
			GLES20.glBlendFunc(pSourceBlendMode, pDestinationBlendMode);
		}
	}

	public static void lineWidth(final float pLineWidth) {
		if(GLState.sLineWidth  != pLineWidth) {
			GLState.sLineWidth = pLineWidth;
			GLES20.glLineWidth(pLineWidth);
		}
	}

	public static void pushModelViewGLMatrix() {
		GLState.sModelViewGLMatrixStack.glPushMatrix();
	}

	public static void popModelViewGLMatrix() {
		GLState.sModelViewGLMatrixStack.glPopMatrix();
	}

	public static void loadModelViewGLMatrixIdentity() {
		GLState.sModelViewGLMatrixStack.glLoadIdentity();
	}

	public static void translateModelViewGLMatrixf(final float pX, final float pY, final float pZ) {
		GLState.sModelViewGLMatrixStack.glTranslatef(pX, pY, pZ);
	}

	public static void rotateModelViewGLMatrixf(final float pAngle, final float pX, final float pY, final float pZ) {
		GLState.sModelViewGLMatrixStack.glRotatef(pAngle, pX, pY, pZ);
	}

	public static void scaleModelViewGLMatrixf(final float pScaleX, final float pScaleY, final int pScaleZ) {
		GLState.sModelViewGLMatrixStack.glScalef(pScaleX, pScaleY, pScaleZ);
	}

	public static void orthoModelViewGLMatrixf(final float pLeft, final float pRight, final float pBottom, final float pTop, final float pZNear, final float pZFar) {
		GLState.sModelViewGLMatrixStack.glOrthof(pLeft, pRight, pBottom, pTop, pZNear, pZFar);
	}

	public static void pushProjectionGLMatrix() {
		GLState.sProjectionGLMatrixStack.glPushMatrix();
	}
	
	public static void popProjectionGLMatrix() {
		GLState.sProjectionGLMatrixStack.glPopMatrix();
	}
	
	public static void loadProjectionGLMatrixIdentity() {
		GLState.sProjectionGLMatrixStack.glLoadIdentity();
	}
	
	public static void translateProjectionGLMatrixf(final float pX, final float pY, final float pZ) {
		GLState.sProjectionGLMatrixStack.glTranslatef(pX, pY, pZ);
	}
	
	public static void rotateProjectionGLMatrixf(final float pAngle, final float pX, final float pY, final float pZ) {
		GLState.sProjectionGLMatrixStack.glRotatef(pAngle, pX, pY, pZ);
	}
	
	public static void scaleProjectionGLMatrixf(final float pScaleX, final float pScaleY, final int pScaleZ) {
		GLState.sProjectionGLMatrixStack.glScalef(pScaleX, pScaleY, pScaleZ);
	}
	
	public static void orthoProjectionGLMatrixf(final float pLeft, final float pRight, final float pBottom, final float pTop, final float pZNear, final float pZFar) {
		GLState.sProjectionGLMatrixStack.glOrthof(pLeft, pRight, pBottom, pTop, pZNear, pZFar);
	}

	public static float[] getModelViewGLMatrix() {
		GLState.sModelViewGLMatrixStack.getMatrix(GLState.sModelViewGLMatrix);
		return GLState.sModelViewGLMatrix;
	}

	public static float[] getProjectionGLMatrix() {
		GLState.sProjectionGLMatrixStack.getMatrix(GLState.sProjectionGLMatrix);
		return GLState.sProjectionGLMatrix;
	}

	public static float[] getModelViewProjectionGLMatrix() {
		Matrix.multiplyMM(GLState.sModelViewProjectionGLMatrix, 0, GLState.sProjectionGLMatrixStack.mMatrixStack, GLState.sProjectionGLMatrixStack.mMatrixStackOffset, GLState.sModelViewGLMatrixStack.mMatrixStack, GLState.sModelViewGLMatrixStack.mMatrixStackOffset);
		return GLState.sModelViewProjectionGLMatrix;
	}

	/**
	 * <b>Note:</b> does not pre-multiply the alpha channel!</br>
	 * Except that difference, same as: {@link GLUtils#texSubImage2D(int, int, int, int, Bitmap, int, int)}</br>
	 * </br>
	 * See topic: '<a href="http://groups.google.com/group/android-developers/browse_thread/thread/baa6c33e63f82fca">PNG loading that doesn't premultiply alpha?</a>'
	 * @param pBorder
	 */
	public static void glTexImage2D(final int pTarget, final int pLevel, final Bitmap pBitmap, final int pBorder, final PixelFormat pPixelFormat) {
		final Buffer pixelBuffer = GLHelper.getPixels(pBitmap, pPixelFormat);

		GLES20.glTexImage2D(pTarget, pLevel, pPixelFormat.getGLFormat(), pBitmap.getWidth(), pBitmap.getHeight(), pBorder, pPixelFormat.getGLFormat(), pPixelFormat.getGLType(), pixelBuffer);
	}

	/**
	 * <b>Note:</b> does not pre-multiply the alpha channel!</br>
	 * Except that difference, same as: {@link GLUtils#texSubImage2D(int, int, int, int, Bitmap, int, int)}</br>
	 * </br>
	 * See topic: '<a href="http://groups.google.com/group/android-developers/browse_thread/thread/baa6c33e63f82fca">PNG loading that doesn't premultiply alpha?</a>'
	 */
	public static void glTexSubImage2D(final int pTarget, final int pLevel, final int pX, final int pY, final Bitmap pBitmap, final PixelFormat pPixelFormat) {
		final Buffer pixelBuffer = GLHelper.getPixels(pBitmap, pPixelFormat);

		GLES20.glTexSubImage2D(pTarget, pLevel, pX, pY, pBitmap.getWidth(), pBitmap.getHeight(), pPixelFormat.getGLFormat(), pPixelFormat.getGLType(), pixelBuffer);
	}

	public static int getGLError() {
		return GLES20.glGetError();
	}

	public static void checkGLError() throws GLException { // TODO Use more often!
		final int err = GLES20.glGetError();
		if(err != GLES20.GL_NO_ERROR) {
			throw new GLException(err);
		}
	}

	public static void clearGLError() {
		GLES20.glGetError();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
