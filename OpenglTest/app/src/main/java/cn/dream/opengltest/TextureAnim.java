package cn.dream.opengltest;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 纹理变换类
 */
public class TextureAnim {
	
	private Context mContext;
	private String mPath[];
	private int animNum;
	
	private String[][] fileName;
	private int textureId;
	
	private int mAnimIndex;
	private int mIndex;
	private int mPreIndex = -1;
	private int mLength[];
	private int mOneSet[];
	
	private boolean isAnim = false;
	
	private int mTime = -1;
	
	private int mCurrentBitmap;
	private int mBitmapBufferIndex = 0;
	private Bitmap[] mBitmapBuffer = new Bitmap[2];
	
	private Object mTask = new Object();
	
	public TextureAnim(Context context, String[] path, int[] num, boolean start) {
		mContext = context;
		mPath = path;
		animNum = mPath.length;
		
		fileName = new String[animNum][];
		mLength = new int[animNum]; 
		mOneSet = new int[animNum];
		for(int i=0; i<animNum; i++) {
			fileName[i] = getAssetsFileList(mPath[i], num[i]);
			mLength[i] = num[i];
			mOneSet[i] = -1;
		}
		
		isAnim = start;
	}
	
	public TextureAnim(Context context, String[] path, int[] num) {
		this(context, path, num, false);
	}
	
	/**
	 * 启动动画
	 */
	public void start() {
		isAnim = true;
		
//		newFrame();
	}
	/**
	 * 停止动画
	 */
	public void stop() {
		isAnim = false;
	}
	
	/**
	 * 恢复初始并启动
	 */
	public void reset() {
		mIndex = 0;
		mAnimIndex = 0;
		isAnim = true;
		recycle();
	}
	
	/**开启某段动画**/
	public int start(int i, int num) {
		if(i >= animNum) {
			return -1;
		}
		
		if(mAnimIndex != i) {
			mOneSet[i] = num;
			mAnimIndex = i;
			mIndex = 0;
			return 1;
		}
		
		return 0;
//		newFrame();
	}
	
//	public int getTexture() {
////		if(mTime != time) {
////			mTime = time;
////
////			if(isAnim) {
////				if(++mIndex >= mLength[mAnimIndex]) {
////					// 切换回原始状态的动画
////					if(mOneSet[mAnimIndex] != -1) {
////						if(--mOneSet[mAnimIndex] <= 0) {
////							mOneSet[mAnimIndex] = 0;
////							mAnimIndex = 0;
////						}
////					}
////					mIndex = 0;
////				}
////			}
////		}
//
//		Bitmap bitmap = null;
//
//		if(textureId == 0) {
//			synchronized (mTask) {
//				bitmap = readBitmapFromAssets(mContext, fileName[mAnimIndex][mIndex], null);
//			}
//			if(bitmap != null) {
//				textureId = TextureCache.loadTextures(bitmap);
//			}
//		} else {
//			if(mPreIndex != mIndex) {
//				try {
//					synchronized (mTask) {
//						bitmap = readBitmapFromAssets(mContext, fileName[mAnimIndex][mIndex], null);
//					}
//				} catch (ArrayIndexOutOfBoundsException e) {
//
//				}
//
//				if(bitmap != null) {
//					TextureCache.subTextures(bitmap, textureId);
//				}
//				mPreIndex = mIndex;
//			}
//		}
//
//		if(bitmap != null) {
//			bitmap.recycle();
//			bitmap = null;
//		}
//
//		return textureId;
//	}
	
	private void newFrame() {
		if(mBitmapBuffer[mBitmapBufferIndex] != null) {
			mBitmapBuffer[mBitmapBufferIndex].recycle();
			mBitmapBuffer[mBitmapBufferIndex] = null;
		}
		
		mBitmapBuffer[mBitmapBufferIndex] = readBitmapFromAssets(mContext, fileName[mAnimIndex][mIndex], null);
		mCurrentBitmap = mBitmapBufferIndex;
		if(++mBitmapBufferIndex > 1){
			mBitmapBufferIndex = 0;
		}
	}
	
	public void nextFrame() {
		synchronized (mTask) {
			if(isAnim) {
				if(++mIndex >= mLength[mAnimIndex]) {
					// 切换回原始状态的动画
					if(mOneSet[mAnimIndex] != -1) {
						if(--mOneSet[mAnimIndex] <= 0) {
							mOneSet[mAnimIndex] = 0;
							mAnimIndex = 0;
						}
					}
					mIndex = 0;
				}
			}
		}

		
//		if(mPreIndex != mIndex) {
//			synchronized (mTask) {
//				if(mBitmapBuffer[mBitmapBufferIndex] != null) {
//					mBitmapBuffer[mBitmapBufferIndex].recycle();
//					mBitmapBuffer[mBitmapBufferIndex] = null;
//				}
//			}
//			
//			mBitmapBuffer[mBitmapBufferIndex] = readBitmapFromAssets(mContext, fileName[mAnimIndex][mIndex], null);
//			mCurrentBitmap = mBitmapBufferIndex;
//			if(++mBitmapBufferIndex > 1){
//				mBitmapBufferIndex = 0;
//			}
// 			mPreIndex = mIndex;
//		}
	}
	
//	public int getTexture() {
//		Bitmap bitmap = null;
//		synchronized (mTask) {
//			if(mBitmapBuffer[mCurrentBitmap] != null && !mBitmapBuffer[mCurrentBitmap].isRecycled()) {
//				bitmap = Bitmap.createBitmap(mBitmapBuffer[mCurrentBitmap]);
//			}
//		}
//		
//		if(bitmap != null){
//			if(textureId == 0) {
//				textureId = TextureCache.loadTextures(bitmap);
//			} else {
//				TextureCache.subTextures(bitmap, textureId);
//			}
//			bitmap.recycle();
//			bitmap = null;
//		}
//		
////		if(mBitmap != null) {
////			if(textureId != 0) {
////				try{
////					GLES20.glDeleteTextures(1, new int[]{textureId}, 0);
////				} finally {
////					
////				}
////			}
////			
////			textureId = TextureCache.loadTextures(mBitmap);
////		}
//		
//		return textureId;
//	}
	
	public void recycle() {
		for(int i=0; i<2; i++) {
			if(mBitmapBuffer[i] != null) {
				if(!mBitmapBuffer[i].isRecycled()) {
					mBitmapBuffer[i].recycle();
					mBitmapBuffer[i] = null;
				}
			}
		}
	}
	
	/**
	 * 获取目录下的所有文件
	 * @param context
	 * @param fileName
	 * @return
	 */
	private String[] getAssetsFileList(Context context, String fileName){
		String[] assetsList = null;
		AssetManager assets = context.getAssets();
		try {
			assetsList = assets.list(fileName);
			if(assetsList == null){
				throw new RuntimeException("Conldn't load list from asset '" + fileName + "'");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return assetsList;
	}
	
	private String[] getAssetsFileList(String fileName, int num) {
		String[] assetsList = new String[num];
		
		for(int i=0; i<num; i++) {
			assetsList[i] = num2String(i) + ".png";
		}
		
		return assetsList;
	}
	
	private String num2String(int num) {
		String string = Integer.valueOf(num).toString();
		if(num >= 10000) {
			return string;
		} else if(num >= 1000) {
			return "0" + string;
		} else if(num >= 100) {
			return "00" + string;
		} else if(num >= 10) {
			return "000" + string;
		} else {
			return "0000" + string;
		}
	}
	
	/**
	 * 载入图片
	 * @param context
	 * @param fileName
	 * @param config
	 * @return
	 */
	private Bitmap readBitmapFromAssets(Context context, String fileName, Config config) {
		if(config == null){
			config = Config.ARGB_8888;
		}
		String filePath;
		filePath = mPath[mAnimIndex] + File.separator + fileName;
		AssetManager assets = context.getAssets();
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = config;
		opt.inPurgeable = true;
		InputStream in = null;
		Bitmap bitmap = null;
		try{
			in = assets.open(filePath);
			bitmap = BitmapFactory.decodeStream(in, null, opt);
		}catch (IOException e) {
			//e.printStackTrace();
			//throw new RuntimeException("Conldn't load bitmap from asset '" + fileName + "'");
		}finally{
			if(in != null){
				try{
					in.close();
				}catch (IOException e) {
				}
			}
		}
		
		return bitmap;
	}
}
