package org.anddev.andengine.opengl.shader.source.criteria;

/**
 * (c) Zynga 2011
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 18:10:26 - 12.10.2011
 */
public class LogicalOrShaderSourceCriteria implements IShaderSourceCriteria {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private final IShaderSourceCriteria[] mShaderSourceCriterias;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public LogicalOrShaderSourceCriteria(final IShaderSourceCriteria ... pShaderSourceCriterias) {
		this.mShaderSourceCriterias = pShaderSourceCriterias;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public boolean isMet() {
		for(IShaderSourceCriteria shaderSourceCriteria : this.mShaderSourceCriterias) {
			if(shaderSourceCriteria.isMet()) {
				return true;
			}
		}
		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
