package cn.dream.opengltest;


import java.nio.FloatBuffer;

import lib.IBufferFactory;

/**
 * 静止动画的坐标
 */
public class StandAnimParams extends VertexParams{
	private float[] mVertexArrays;
	private FloatBuffer mVertexBuffer;
	public StandAnimParams(int x, int y, int width, int height) {
		super();
		mVertexArrays = VertexParams.toVertex(new int[][]{{x, y, width, height}});
		mVertexBuffer = IBufferFactory.newFloatBuffer(mVertexArrays);
	}

	@Override
	public FloatBuffer getVertexBuffer() {
		return mVertexBuffer;
	}
	
	@Override
	public float[] getVertexArrays() {
		return mVertexArrays;
	}
}
