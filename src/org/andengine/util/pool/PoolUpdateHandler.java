package org.andengine.util.pool;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.util.list.ConcurrentShiftQueue;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Valentin Milea
 * @author Nicolas Gramlich
 * 
 * @since 23:02:58 - 21.08.2010
 */
public abstract class PoolUpdateHandler<T extends PoolItem> implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Pool<T> mPool;
	private final ConcurrentShiftQueue<T> mScheduledPoolItemQueue = new ConcurrentShiftQueue<T>();

	// ===========================================================
	// Constructors
	// ===========================================================

	public PoolUpdateHandler() {
		this.mPool = new Pool<T>() {
			@Override
			protected T onAllocatePoolItem() {
				return PoolUpdateHandler.this.onAllocatePoolItem();
			}
		};
	}

	public PoolUpdateHandler(final int pInitialPoolSize) {
		this.mPool = new Pool<T>(pInitialPoolSize) {
			@Override
			protected T onAllocatePoolItem() {
				return PoolUpdateHandler.this.onAllocatePoolItem();
			}
		};
	}

	public PoolUpdateHandler(final int pInitialPoolSize, final int pGrowth) {
		this.mPool = new Pool<T>(pInitialPoolSize, pGrowth) {
			@Override
			protected T onAllocatePoolItem() {
				return PoolUpdateHandler.this.onAllocatePoolItem();
			}
		};
	}

	public PoolUpdateHandler(final int pInitialPoolSize, final int pGrowth, final int pAvailableItemCountMaximum) {
		this.mPool = new Pool<T>(pInitialPoolSize, pGrowth, pAvailableItemCountMaximum) {
			@Override
			protected T onAllocatePoolItem() {
				return PoolUpdateHandler.this.onAllocatePoolItem();
			}
		};
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	protected abstract T onAllocatePoolItem();

	protected abstract void onHandlePoolItem(final T pPoolItem);

	@Override
	public void onUpdate(final float pSecondsElapsed) {
		final ConcurrentShiftQueue<T> scheduledPoolItemQueue = this.mScheduledPoolItemQueue;
		final Pool<T> pool = this.mPool;

		T item;
		while((item = scheduledPoolItemQueue.poll()) != null) {
			this.onHandlePoolItem(item);
			pool.recyclePoolItem(item);
		}
	}

	@Override
	public void reset() {
		final ConcurrentShiftQueue<T> scheduledPoolItemQueue = this.mScheduledPoolItemQueue;
		final Pool<T> pool = this.mPool;

		T item;
		while((item = scheduledPoolItemQueue.poll()) != null) {
			pool.recyclePoolItem(item);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public T obtainPoolItem() {
		return this.mPool.obtainPoolItem();
	}

	public void postPoolItem(final T pPoolItem) {
		if(pPoolItem == null) {
			throw new IllegalArgumentException("PoolItem already recycled!");
		} else if(!this.mPool.ownsPoolItem(pPoolItem)) {
			throw new IllegalArgumentException("PoolItem from another pool!");
		}

		this.mScheduledPoolItemQueue.enter(pPoolItem);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
