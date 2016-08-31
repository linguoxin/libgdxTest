package cn.dream.opengltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片纹理储存
 */
public class TextureArray {
    private static final String TAG = "TextureArray";

    private int type;
    private Context mContext;
    private int size;
    private String resPath;
    private String fileName;
    private int[] textArray;
    private int[] resArray;
    private Bitmap[] bitmapArray;
    private Bitmap[] tempBitmaps=new Bitmap[40];

    private boolean isUpdate;

    public TextureArray(Context context, int[] res) {
        type = 1;
        mContext = context;
        resArray = res;
        size = res.length;
        textArray = new int[size];
    }

    //    public TextureArray(Context context, String path, int length) {
//        type = 2;
//        mContext = context;
//        size = length;
//        resPath = path;
//        textArray = new int[size];
//    }
    public TextureArray(Context context, String name, int length) {
        type = 3;
        mContext = context;
        size = length;
        fileName = name;
        textArray = new int[size];
    }

    public int getSize() {
        return size;
    }

    public int getFileSize(String fileName) {
        int size = 0;
        File file = mContext.getFileStreamPath(fileName);
        if (file.exists()) {
            size = (int) file.length();
        } else {

        }
        return size;
    }

    /**
     * 获取纹理
     *
     * @param gl
     * @param id
     * @return
     */
    public int getTexture(int id) {

        try {
            if (textArray[id] == 0 || textArray[id] == -1 || isUpdate) {
                if (bitmapArray != null && bitmapArray[id] != null && !isUpdate) {
                    textArray[id] = TextureCache.loadTextures(bitmapArray[id]);
                    bitmapArray[id].recycle();
                } else {
                    if (type == 1) {
                        textArray[id] = TextureCache.loadTextures(mContext, resArray[id]);
                    } else if (type == 3) {
                        textArray[id] = TextureCache.loadTexturesData(mContext, fileName + id + ".png");
                    } else {
                        textArray[id] = TextureCache.loadTextures(mContext, resPath + "/" + id + ".png");
                    }
                }
            }
            return textArray[id];
        } catch (IndexOutOfBoundsException e) {
         //   Log.e("TextureArray", e.getMessage());
            return getTexture(0);
        }
    }

    /**
     * 获取纹理
     *
     * @param id
     * @return
     */
    public int getTexture(int id, Bitmap bitmap) {
        try {
            if(bitmap==null) return -1;
            if(textArray[id]==0||tempBitmaps[id]!=bitmap) {
                textArray[id] = TextureCache.loadTextures(bitmap);
            }
            tempBitmaps[id]=bitmap;
            return textArray[id];
        } catch (IndexOutOfBoundsException e) {
           // Log.e("TextureArray", e.getMessage());
            return getTexture(0);
        }
    }

    /**
     * 预加载图片
     *
     * @param gl
     */
    public void initTexture() {
        if (bitmapArray == null) {
            bitmapArray = new Bitmap[size];
        }

        for (int i = 0; i < size; i++) {
            if (textArray[i] == 0) {
                if (type == 1) {
                    bitmapArray[i] = BitmapFactory.
                            decodeResource(mContext.getResources(), resArray[i]);
                } else {
                    try {
                        InputStream input = mContext.getAssets().open(resPath + "/" + i + ".png");
                        BufferedInputStream bis = new BufferedInputStream(input);
                        bitmapArray[i] = BitmapFactory.decodeStream(bis);
                    } catch (IOException e) {
                        Log.e(TAG, "open img file wrong");
                    }
                }
            }
        }

    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

}
