package org.anddev.andengine.entity;

import static org.anddev.andengine.util.constants.Constants.VERTEX_INDEX_X;
import static org.anddev.andengine.util.constants.Constants.VERTEX_INDEX_Y;

import java.util.ArrayList;
import java.util.Comparator;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.collision.ShapeCollisionChecker;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.layer.ZIndexSorter;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.util.IEntityMatcher;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ModifierList;


/**
 * @author Nicolas Gramlich
 * @since 12:00:48 - 08.03.2010
 */
public class Entity implements IEntity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CHILDREN_CAPACITY_DEFAULT = 4;
	private static final int TOUCHAREAS_CAPACITY_DEFAULT = 4;

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mVisible = true;
	private boolean mIgnoreUpdate = false;
	private int mZIndex = 0;

	private IEntity mParent;

	private ArrayList<IEntity> mChildren;
	private ArrayList<ITouchArea> mTouchAreas;

	protected float mRed = 1f;
	protected float mGreen = 1f;
	protected float mBlue = 1f;
	protected float mAlpha = 1f;

	private final float mBaseX;
	private final float mBaseY;

	protected float mX;
	protected float mY;

	protected float mRotation = 0;

	protected float mRotationCenterX = 0;
	protected float mRotationCenterY = 0;

	protected float mScaleX = 1f;
	protected float mScaleY = 1f;

	protected float mScaleCenterX = 0;
	protected float mScaleCenterY = 0;

	private final ModifierList<IEntity> mEntityModifiers = new ModifierList<IEntity>(this);

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public Entity() {
		this(0, 0);
	}

	public Entity(final float pX, final float pY) {
		this.mBaseX = pX;
		this.mBaseY = pY;

		this.mX = pX;
		this.mY = pY;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public boolean isVisible() {
		return this.mVisible;
	}

	public void setVisible(final boolean pVisible) {
		this.mVisible = pVisible;
	}

	public boolean isIgnoreUpdate() {
		return this.mIgnoreUpdate;
	}

	public void setIgnoreUpdate(final boolean pIgnoreUpdate) {
		this.mIgnoreUpdate = pIgnoreUpdate;
	}

	public IEntity getParent() {
		return this.mParent;
	}

	public void setParent(final IEntity pParent) {
		this.mParent = pParent;
	}

	@Override
	public int getZIndex() {
		return this.mZIndex;
	}

	@Override
	public void setZIndex(final int pZIndex) {
		this.mZIndex = pZIndex;
	}

	@Override
	public float getBaseX() {
		return this.mBaseX;
	}

	@Override
	public float getBaseY() {
		return this.mBaseY;
	}

	@Override
	public void setPosition(final IEntity pOtherEntity) {
		this.setPosition(pOtherEntity.getX(), pOtherEntity.getY());
	}

	@Override
	public void setPosition(final float pX, final float pY) {
		this.mX = pX;
		this.mY = pY;
		this.onPositionChanged();
	}

	@Override
	public void setBasePosition() {
		this.mX = this.mBaseX;
		this.mY = this.mBaseY;
		this.onPositionChanged();
	}

	@Override
	public float getRotation() {
		return this.mRotation;
	}

	@Override
	public void setRotation(final float pRotation) {
		this.mRotation = pRotation;
	}

	@Override
	public float getRotationCenterX() {
		return this.mRotationCenterX;
	}

	@Override
	public float getRotationCenterY() {
		return this.mRotationCenterY;
	}

	@Override
	public void setRotationCenterX(final float pRotationCenterX) {
		this.mRotationCenterX = pRotationCenterX;
	}

	@Override
	public void setRotationCenterY(final float pRotationCenterY) {
		this.mRotationCenterY = pRotationCenterY;
	}

	@Override
	public void setRotationCenter(final float pRotationCenterX, final float pRotationCenterY) {
		this.mRotationCenterX = pRotationCenterX;
		this.mRotationCenterY = pRotationCenterY;
	}

	@Override
	public boolean isScaled() {
		return this.mScaleX != 1 || this.mScaleY != 1;
	}

	@Override
	public float getScaleX() {
		return this.mScaleX;
	}

	@Override
	public float getScaleY() {
		return this.mScaleY;
	}

	@Override
	public void setScaleX(final float pScaleX) {
		this.mScaleX = pScaleX;
	}

	@Override
	public void setScaleY(final float pScaleY) {
		this.mScaleY = pScaleY;
	}

	@Override
	public void setScale(final float pScale) {
		this.mScaleX = pScale;
		this.mScaleY = pScale;
	}

	@Override
	public void setScale(final float pScaleX, final float pScaleY) {
		this.mScaleX = pScaleX;
		this.mScaleY = pScaleY;
	}

	@Override
	public float getScaleCenterX() {
		return this.mScaleCenterX;
	}

	@Override
	public float getScaleCenterY() {
		return this.mScaleCenterY;
	}

	@Override
	public void setScaleCenterX(final float pScaleCenterX) {
		this.mScaleCenterX = pScaleCenterX;
	}

	@Override
	public void setScaleCenterY(final float pScaleCenterY) {
		this.mScaleCenterY = pScaleCenterY;
	}

	@Override
	public void setScaleCenter(final float pScaleCenterX, final float pScaleCenterY) {
		this.mScaleCenterX = pScaleCenterX;
		this.mScaleCenterY = pScaleCenterY;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	@Override
	public float getRed() {
		return this.mRed;
	}

	@Override
	public float getGreen() {
		return this.mGreen;
	}

	@Override
	public float getBlue() {
		return this.mBlue;
	}

	@Override
	public float getAlpha() {
		return this.mAlpha;
	}

	/**
	 * @param pAlpha from <code>0.0f</code> (invisible) to <code>1.0f</code> (opaque)
	 */
	@Override
	public void setAlpha(final float pAlpha) {
		this.mAlpha = pAlpha;
	}

	/**
	 * @param pRed from <code>0.0f</code> to <code>1.0f</code>
	 * @param pGreen from <code>0.0f</code> to <code>1.0f</code>
	 * @param pBlue from <code>0.0f</code> to <code>1.0f</code>
	 */
	@Override
	public void setColor(final float pRed, final float pGreen, final float pBlue) {
		this.mRed = pRed;
		this.mGreen = pGreen;
		this.mBlue = pBlue;
	}

	/**
	 * @param pRed from <code>0.0f</code> to <code>1.0f</code>
	 * @param pGreen from <code>0.0f</code> to <code>1.0f</code>
	 * @param pBlue from <code>0.0f</code> to <code>1.0f</code>
	 * @param pAlpha from <code>0.0f</code> (invisible) to <code>1.0f</code> (opaque)
	 */
	@Override
	public void setColor(final float pRed, final float pGreen, final float pBlue, final float pAlpha) {
		this.mRed = pRed;
		this.mGreen = pGreen;
		this.mBlue = pBlue;
		this.mAlpha = pAlpha;
	}

	@Override
	public float getX() {
		return this.mX;
	}

	@Override
	public float getY() {
		return this.mY;
	}


	@Override
	public float[] getSceneCenterCoordinates() {
		return this.convertLocalToSceneCoordinates(0, 0);
	}
	
	public float[] convertLocalToSceneCoordinates(final float pX, final float pY) {
		final float[] sceneCoordinates = ShapeCollisionChecker.convertLocalToSceneCoordinates(this, pX, pY);
		sceneCoordinates[VERTEX_INDEX_X] += this.mX;
		sceneCoordinates[VERTEX_INDEX_Y] += this.mY;
		return sceneCoordinates;
	}

	public float[] convertSceneToLocalCoordinates(final float pX, final float pY) {
		final float[] localCoordinates = ShapeCollisionChecker.convertSceneToLocalCoordinates(this, pX, pY);
		localCoordinates[VERTEX_INDEX_X] -= this.mX;
		localCoordinates[VERTEX_INDEX_Y] -= this.mY;
		return localCoordinates;
	}

	@Override
	public final void onDraw(final GL10 pGL, final Camera pCamera) {
		if(this.mVisible) {
			this.onManagedDraw(pGL, pCamera);
		}
	}

	@Override
	public final void onUpdate(final float pSecondsElapsed) {
		if(!this.mIgnoreUpdate) {
			this.onManagedUpdate(pSecondsElapsed);
		}
	}

	@Override
	public void registerTouchArea(final ITouchArea pTouchArea) {
		if(this.mTouchAreas == null) {
			this.allocateTouchAreas();
		}
		this.mTouchAreas.add(pTouchArea);
	}

	@Override
	public void unregisterTouchArea(final ITouchArea pTouchArea) {
		if(this.mTouchAreas == null) {
			return;
		}
		this.mTouchAreas.remove(pTouchArea);
	}

	@Override
	public ArrayList<ITouchArea> getTouchAreas() {
		return this.mTouchAreas;
	}

	@Override
	public IEntity getChild(final int pIndex) {
		if(this.mChildren == null) {
			return null;
		}
		return this.mChildren.get(pIndex);
	}

	@Override
	public int getChildCount() {
		if(this.mChildren == null) {
			return 0;
		}
		return this.mChildren.size();
	}

	@Override
	public void reset() {
		this.mVisible = true;
		this.mIgnoreUpdate = false;

		this.mX = this.mBaseX;
		this.mY = this.mBaseY;
		this.mRotation = 0;
		this.mScaleX = 1;
		this.mScaleY = 1;

		this.onPositionChanged();

		this.mRed = 1.0f;
		this.mGreen = 1.0f;
		this.mBlue = 1.0f;
		this.mAlpha = 1.0f;

		this.mEntityModifiers.reset();

		final ArrayList<IEntity> entities = this.mChildren;
		for(int i = entities.size() - 1; i >= 0; i--) {
			entities.get(i).reset();
		}
	}

	@Override
	public void clearChildren() {
		if(this.mChildren == null) {
			return;
		}
		this.mChildren.clear();
	}

	@Override
	public void addChild(final IEntity pEntity) {
		if(this.mChildren == null) {
			this.allocateChildren();
		}

		this.mChildren.add(pEntity);
	}

	@Override
	public boolean removeChild(final IEntity pEntity) {
		if(this.mChildren == null) {
			return false;
		}
		return this.mChildren.remove(pEntity);
	}

	@Override
	public IEntity removeChild(final int pIndex) {
		if(this.mChildren == null) {
			return null;
		}
		return this.mChildren.remove(pIndex);
	}

	@Override
	public boolean removeChild(final IEntityMatcher pEntityMatcher) {
		if(this.mChildren == null) {
			return false;
		}
		final ArrayList<IEntity> entities = this.mChildren;
		for(int i = entities.size() - 1; i >= 0; i--) {
			if(pEntityMatcher.matches(entities.get(i))) {
				entities.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public IEntity findChild(final IEntityMatcher pEntityMatcher) {
		if(this.mChildren == null) {
			return null;
		}
		final ArrayList<IEntity> entities = this.mChildren;
		for(int i = entities.size() - 1; i >= 0; i--) {
			final IEntity entity = entities.get(i);
			if(pEntityMatcher.matches(entity)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void sortChildren() {
		if(this.mChildren == null) {
			return;
		}
		ZIndexSorter.getInstance().sort(this.mChildren);
	}

	@Override
	public void sortChildren(final Comparator<IEntity> pEntityComparator) {
		if(this.mChildren == null) {
			return;
		}
		ZIndexSorter.getInstance().sort(this.mChildren, pEntityComparator);
	}

	@Override
	public IEntity replaceChild(final int pEntityIndex, final IEntity pEntity) {
		if(this.mChildren == null) {
			return null;
		}
		final ArrayList<IEntity> entities = this.mChildren;
		final IEntity oldEntity = entities.set(pEntityIndex, pEntity);
		return oldEntity;
	}

	@Override
	public void setChild(final int pEntityIndex, final IEntity pEntity) {
		if(this.mChildren == null) {
			return;
		}
		if(pEntityIndex == this.mChildren.size()) {
			this.addChild(pEntity);
		} else {
			this.mChildren.set(pEntityIndex, pEntity);
		}
	}

	@Override
	public void swapChildren(final int pEntityIndexA, final int pEntityIndexB) {
		if(this.mChildren == null) {
			return;
		}
		final ArrayList<IEntity> entities = this.mChildren;
		final IEntity entityA = entities.get(pEntityIndexA);
		final IEntity entityB = entities.set(pEntityIndexB, entityA);
		entities.set(pEntityIndexA, entityB);
	}

	@Override
	public void addEntityModifier(final IModifier<IEntity> pEntityModifier) {
		this.mEntityModifiers.add(pEntityModifier);
	}

	@Override
	public boolean removeEntityModifier(final IModifier<IEntity> pEntityModifier) {
		return this.mEntityModifiers.remove(pEntityModifier);
	}

	@Override
	public void clearEntityModifiers() {
		this.mEntityModifiers.clear();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	protected void onPositionChanged(){

	}

	private void allocateTouchAreas() {
		this.mTouchAreas = new ArrayList<ITouchArea>(Entity.TOUCHAREAS_CAPACITY_DEFAULT);
	}

	private void allocateChildren() {
		this.mChildren = new ArrayList<IEntity>(Entity.CHILDREN_CAPACITY_DEFAULT);
	}

	protected void onApplyTransformations(final GL10 pGL) {
		/* Translation. */
		this.applyTranslation(pGL);

		/* Rotation. */
		this.applyRotation(pGL);

		/* Scale. */
		this.applyScale(pGL);
	}

	protected void applyTranslation(final GL10 pGL) {
		pGL.glTranslatef(this.mX, this.mY, 0);
	}

	protected void applyRotation(final GL10 pGL) {
		final float rotation = this.mRotation;

		if(rotation != 0) {
			final float rotationCenterX = this.mRotationCenterX;
			final float rotationCenterY = this.mRotationCenterY;

			pGL.glTranslatef(rotationCenterX, rotationCenterY, 0);
			pGL.glRotatef(rotation, 0, 0, 1);
			pGL.glTranslatef(-rotationCenterX, -rotationCenterY, 0);
		}
	}

	protected void applyScale(final GL10 pGL) {
		final float scaleX = this.mScaleX;
		final float scaleY = this.mScaleY;

		if(scaleX != 1 || scaleY != 1) {
			final float scaleCenterX = this.mScaleCenterX;
			final float scaleCenterY = this.mScaleCenterY;

			pGL.glTranslatef(scaleCenterX, scaleCenterY, 0);
			pGL.glScalef(scaleX, scaleY, 1);
			pGL.glTranslatef(-scaleCenterX, -scaleCenterY, 0);
		}
	}

	protected void onManagedDraw(final GL10 pGL, final Camera pCamera) {
		pGL.glPushMatrix();
		{
			this.onApplyTransformations(pGL);

			this.doDraw(pGL, pCamera);

			if(this.mChildren != null) {
				final ArrayList<IEntity> entities = this.mChildren;
				final int entityCount = entities.size();
				for(int i = 0; i < entityCount; i++) {
					entities.get(i).onDraw(pGL, pCamera);
				}
			}
		}
		pGL.glPopMatrix();
	}

	protected void doDraw(final GL10 pGL, final Camera pCamera) {

	}

	protected void onManagedUpdate(final float pSecondsElapsed) {
		this.mEntityModifiers.onUpdate(pSecondsElapsed);

		if(this.mChildren == null) {
			return;
		}

		final ArrayList<IEntity> entities = this.mChildren;
		final int entityCount = entities.size();
		for(int i = 0; i < entityCount; i++) {
			entities.get(i).onUpdate(pSecondsElapsed);
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
