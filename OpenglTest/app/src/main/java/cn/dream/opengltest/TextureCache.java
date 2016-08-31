package cn.dream.opengltest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

/**
 * AP图标的储存
 */
@SuppressLint("UseSparseArrays")
public class TextureCache {
	private static final String TAG = "Launcher.TextureCache";
	
	private static class CacheEntry {
	    public int nTexture = -1;
	    public int pTexture = -1;
	}
	
    private final HashMap<Integer, CacheEntry> mCache =
            new HashMap<Integer, CacheEntry>();
    private final Context mContext;
    
    private int TextClick = 0;

    
    /**
     * 不能构造在Application里面
     * @param context
     */
    public TextureCache(Context context) {
    	mContext = context;
    }
    
    /**
     * Empty out the cache.
     */
    public void flush() {
        synchronized (mCache) {
            mCache.clear();
        }
    }
    
    /**
     * Remove any records for the supplied ComponentName.
     */
    public void remove(int id) {
        synchronized (mCache) {
            mCache.remove(id);
        }
    }
    
    public int getCTexture() {
//    	if(TextClick == 0) {
//    		TextClick = loadTextures(mContext, R.drawable.flash_icon_click);
//    	}
//    	
    	return TextClick;
    }
    

    /**
     * 获取图片纹理
     * @param gl
     * @param context
     * @param id
     * @return
     */
	public static int loadTextures(Context context, int id) {
		if(id == -1) {
			return -1;
		}
		
		int texture = -1;
		
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);

		if(bitmap != null) {
			texture = loadTextures(bitmap);
			bitmap.recycle();
		}

		return texture;
	}
	
	/**
	 * 获取图片纹理
	 * @param gl
	 * @param context
	 * @param path
	 * @return
	 */
	public static int loadTextures(Context context, String path) {
		if(path == null) {
			return -1;
		}
		
		int texture = -1;
		
		Bitmap bitmap = null;
		try {
			InputStream input = context.getAssets().open(path);
			BufferedInputStream bis = new BufferedInputStream(input);
			bitmap = BitmapFactory.decodeStream(bis);
		} catch (IOException e) {
			Log.e(TAG, "open img file wrong");
		}

		if(bitmap != null) {
			texture = loadTextures(bitmap);
			bitmap.recycle();
		}

		return texture;
	}

	public static int loadTexturesData(Context context, String name) {
		if(name == null) {
			return -1;
		}

		int texture = -1;

		Bitmap bitmap = null;
		try {
			InputStream input = context.openFileInput(name);
			BufferedInputStream bis = new BufferedInputStream(input);
			bitmap = BitmapFactory.decodeStream(bis);
			input.close();
		} catch (IOException e) {
			Log.e(TAG, "open img file wrong");
		}

		if(bitmap != null) {
			texture = loadTextures(bitmap);
			bitmap.recycle();
		}

		return texture;
	}
	public static int loadTextures(Bitmap bitmap) {
		IntBuffer buffer = IntBuffer.allocate(1);
		
		GLES20.glGenTextures(1, buffer);		

		int texture = buffer.get();
		
		if(texture == -1) {
			return texture;
		}
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
		
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		
		GLES10.glTexParameterx(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES10.glTexParameterx(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES10.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES10.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		
		return texture;
	}

	public static void subTextures(Bitmap bitmap, int textureID) {
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID);
		
		GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, bitmap);
		
		GLES10.glTexParameterx(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES10.glTexParameterx(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES10.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES10.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
	}
	
	public static Buffer getPixels(final Bitmap pBitmap, final ByteOrder pByteOrder) {
		final int[] pixelsARGB_8888 = getPixelsARGB_8888(pBitmap);
		
		return ShortBuffer.wrap(convertARGB_8888toRGBA_4444(pixelsARGB_8888, pByteOrder));
	}
	
	public static int[] getPixelsARGB_8888(final Bitmap pBitmap) {
		final int w = pBitmap.getWidth();
		final int h = pBitmap.getHeight();

		final int[] pixelsARGB_8888 = new int[w * h];
		pBitmap.getPixels(pixelsARGB_8888, 0, w, 0, 0, w, h);

		return pixelsARGB_8888;
	}
	
	public static short[] convertARGB_8888toRGBA_4444(final int[] pPixelsARGB_8888, final ByteOrder pByteOrder) {
		final short[] pixelsRGBA_4444 = new short[pPixelsARGB_8888.length];
		if(pByteOrder == ByteOrder.LITTLE_ENDIAN) {
			for(int i = pPixelsARGB_8888.length - 1; i >= 0; i--) {
				final int pixel = pPixelsARGB_8888[i];

				/* [A][R][G][B] to [BA][RG] */
				/* From : [ A7 A6 A5 A4 A3 A2 A1 A0  |  R7 R6 R5 R4 R3 R2 R1 R0  |  G7 G6 G5 G4 G3 G2 G1 G0  |  B7 B6 B5 B4 B3 B2 B1 B0 ] */
				/* To   :                                                         [ B7 B6 B5 B4 A7 A6 A5 A4  |  R7 R6 R5 R4 G7 G6 G5 G4 ] */

				/* pixelsRGBA_4444[i] = (short)(red | green | blue | alpha) */
				pixelsRGBA_4444[i] = (short)(((pixel >> 16) & 0x00F0) | ((pixel >> 12) & 0x000F) | ((pixel << 8) & 0xF000) | ((pixel >> 20) & 0x0F00));
			}
		} else {
			for(int i = pPixelsARGB_8888.length - 1; i >= 0; i--) {
				final int pixel = pPixelsARGB_8888[i];

				/* [A][R][G][B] to [RG][BA] */
				/* From : [ A7 A6 A5 A4 A3 A2 A1 A0  |  R7 R6 R5 R4 R3 R2 R1 R0  |  G7 G6 G5 G4 G3 G2 G1 G0  |  B7 B6 B5 B4 B3 B2 B1 B0 ] */
				/* To   :                                                         [ R7 R6 R5 R4 G7 G6 G5 G4  |  B7 B6 B5 B4 A7 A6 A5 A4 ] */

				/* pixelsRGBA_4444[i] = (short)(red | green | blue | alpha) */
				pixelsRGBA_4444[i] = (short)(((pixel >> 8) & 0xF000) | ((pixel >> 4) & 0x0F00) | ((pixel) & 0x00F0) | ((pixel >> 28) & 0x0000F));
			}
		}
		return pixelsRGBA_4444;
	}
}
