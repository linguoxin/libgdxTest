package cn.dream.opengltest;


import java.nio.FloatBuffer;

import lib.IBufferFactory;

public abstract class VertexParams {
	private static final float[] coordsArray = new float[] {
		0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f,
	};
	private static final float[] coordsArrayBig = new float[] {
		-0.1f, -0.1f, 1.1f, -0.1f, 1.1f, 1.1f, -0.1f, 1.1f,
	};
	private static FloatBuffer mCoordsBuffer = IBufferFactory.newFloatBuffer(coordsArray);
	private static FloatBuffer mCoordsBufferBig = IBufferFactory.newFloatBuffer(coordsArrayBig);
	
	public static int COUNTER = -1;
	private int id;
	public VertexParams() {
		this.id = COUNTER;
		COUNTER++;
	}
	
	public int getId() {
		return id;
	}
	
	public abstract float[] getVertexArrays();
	public abstract FloatBuffer getVertexBuffer();
	
	/**
	 * 获取单张纹理的纹理坐标
	 * @return
	 */
	public static final FloatBuffer getCoordsBuffer () {
		return mCoordsBuffer;
	}
	
	/**
	 * 获取单张放大纹理的纹理坐标
	 * @return
	 */
	public static final FloatBuffer getCoordsBufferBig () {
		return mCoordsBufferBig;
	}
	
	/**
	 * 将普通坐标转化为opengl用的坐标
	 * @param array 整型数组 必须按照[x, y, w, h]的格式
	 * @return float数组的坐标
	 */
	public static final float[] toVertex(int[][] array) {
		return toVertex(array, 1280, 800);
 	}
	
	/**
	 * 将普通坐标转化为opengl用的坐标
	 * @param array 整型数组 必须按照[x, y, w, h]的格式
	 * @param z Z轴坐标
	 * @return float数组的坐标
	 */
	public static final float[] toVertex(int[][] array, float z) {
		return toVertex(array, 1280, 800, z);
 	}
	
	/**
	 * 将普通坐标转化为opengl用的坐标
	 * @param array 整型数组 必须按照[x, y, w, h]的格式
	 * @param W view的宽
	 * @param H view的高
	 * @return float数组的坐标
	 */
	public static final float[] toVertex(int[][] array, int W, int H) {
		return toVertex(array, W, H, 0f);
 	}
	
	/**
	 * 将普通坐标转化为opengl用的坐标
	 * @param array 整型数组 必须按照[x, y, w, h]的格式
	 * @param W view的宽
	 * @param H view的高
	 * @param z Z轴坐标
	 * @return float数组的坐标
	 */
	public static final float[] toVertex(int[][] array, int W, int H, float z) {
		int length = array.length;
		W /=2;
		H /=2;
		
		float[] arrays = new float[12*length];
		
		for(int i=0; i<length; i++) {
			float x = array[i][0];
			float y = array[i][1];
			float x2 = array[i][2] + x;
			float y2 = array[i][3] + y;
			
			arrays[i*12+0] = arrays[i*12+9] = (x - W)/W;
			arrays[i*12+3] = arrays[i*12+6] = (x2 - W)/W;
			arrays[i*12+1] = arrays[i*12+4] = (H - y)/H;
			arrays[i*12+7] = arrays[i*12+10] = (H - y2)/H;
			arrays[i*12+2] = arrays[i*12+5] = z;
			arrays[i*12+8] = arrays[i*12+11] = z;
		}
		
		return arrays;
 	}
	
	/**
	 * 将普通坐标转化为纹理用的坐标
	 * @return float数组的坐标
	 */
	public static final float[] toTexCoord(int[][] array) {
		int length = array.length;
		
		return toTexCoord(length);
 	}
	/**
	 * 将普通坐标转化为纹理用的放大坐标
	 * @return float数组的坐标
	 */
	public static final float[] toBigTexCoord(int[][] array) {
		int length = array.length;
		
		return toBigTexCoord(length);
 	}
	
	public static final float[] toTexCoord(int num) {
		float[] coords = new float[8*num];
		
		for(int i=0; i<num; i++) {
			coords[i*8 + 0] = 0f;
			coords[i*8 + 1] = 0f;
			coords[i*8 + 2] = 1f;
			coords[i*8 + 3] = 0f;
			coords[i*8 + 4] = 1f;
			coords[i*8 + 5] = 1f;
			coords[i*8 + 6] = 0f;
			coords[i*8 + 7] = 1f;
		}
		
		return coords;
	}

	public static final float[] toBigTexCoord(int num) {
		float[] coords = new float[8*num];
		
		for(int i=0; i<num; i++) {
			coords[i*8 + 0] = 0.05f;
			coords[i*8 + 1] = 0.05f;
			coords[i*8 + 2] = 0.95f;
			coords[i*8 + 3] = 0.05f;
			coords[i*8 + 4] = 0.95f;
			coords[i*8 + 5] = 0.95f;
			coords[i*8 + 6] = 0.05f;
			coords[i*8 + 7] = 0.95f;
		}
		
		return coords;
	}
	
	public static final float[] toAnimVertex(int[][] array) {
		int length = array.length;
		
		float[] arrays = new float[12*length];
		
		for(int i=0; i<length; i++) {
			float x = array[i][0];
			float y = array[i][1];
			float x2 = array[i][2] + x;
			float y2 = array[i][3] + y;
			
			arrays[i*12+0] = arrays[i*12+9] = ((x - 98f) - 525f)/525f * 0.5633f;
			arrays[i*12+3] = arrays[i*12+6] = ((x2 - 98f) - 525f)/525f * 0.5633f;
			arrays[i*12+1] = arrays[i*12+4] = (312f-(y-53f))/312f * 0.536f + 0.01f;
			arrays[i*12+7] = arrays[i*12+10] = (312f-(y2-53f))/312f * 0.536f + 0.01f;
			arrays[i*12+2] = arrays[i*12+5] = 1.0f;
			arrays[i*12+8] = arrays[i*12+11] = 1.0f;
		}
		
		return arrays;
	}
}
