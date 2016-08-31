package cn.dream.opengltest;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

import cn.dream.opengltest.tools.TextureAtlas;
import lib.IBufferFactory;

/**
 * Created by lingu on 2016/8/22.
 */
public class Glview extends GLSurfaceView implements GLSurfaceView.Renderer,
        GLSurfaceView.EGLConfigChooser {
    private int[] mBtnRes = {R.drawable.ic_launcher};
    private int[][] mBtnCoordinate = new int[][]{
            {72, 72, 100, 100},
    };
    private final int[] s_configAttribs = new int[]{
            EGL10.EGL_SAMPLE_BUFFERS, 1,
            EGL10.EGL_SAMPLES, 8,
            EGL10.EGL_RED_SIZE, 8,
            EGL10.EGL_GREEN_SIZE, 8,
            EGL10.EGL_BLUE_SIZE, 8,
            EGL10.EGL_ALPHA_SIZE, 8,
            EGL10.EGL_DEPTH_SIZE, 16,
            EGL10.EGL_STENCIL_SIZE, 0,
            EGL10.EGL_NONE,
    };
    private final int[] s_configAttribs2 = new int[]{
            EGL10.EGL_SAMPLE_BUFFERS, 1,
            EGL10.EGL_SAMPLES, 4,
            EGL10.EGL_RED_SIZE, 8,
            EGL10.EGL_GREEN_SIZE, 8,
            EGL10.EGL_BLUE_SIZE, 8,
            EGL10.EGL_ALPHA_SIZE, 8,
            EGL10.EGL_DEPTH_SIZE, 16,
            EGL10.EGL_STENCIL_SIZE, 0,
            EGL10.EGL_NONE,
    };

    private float[] mBtnCoords = VertexParams.toTexCoord(mBtnCoordinate);
    private FloatBuffer mBtnBuffer; // 按钮的顶点坐标Buffer
    private FloatBuffer mBtnCoordBuffer; // 按钮的纹理坐标Buffer
    private float[] mBtnArrays = VertexParams.toVertex(mBtnCoordinate);
    private TextureArray mBtnText = new TextureArray(getContext(), mBtnRes); // 按钮的纹理

    public Glview(Context context) {
        super(context);
        init();
    }

    public Glview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
        int[] num_config = new int[1];
        EGLConfig[] configs = null;
        int numConfigs = 0;

        egl.eglChooseConfig(display, s_configAttribs, null, 0, num_config);
        numConfigs = num_config[0];
        if (numConfigs > 0) {
            configs = new EGLConfig[numConfigs];
            egl.eglChooseConfig(display, s_configAttribs, configs, numConfigs, num_config);
        } else {
            egl.eglChooseConfig(display, s_configAttribs2, null, 0, num_config);
            numConfigs = num_config[0];

            if (numConfigs <= 0) {
                throw new IllegalArgumentException("No config match configSpec");
            }
            configs = new EGLConfig[numConfigs];
            egl.eglChooseConfig(display, s_configAttribs2, configs, numConfigs, num_config);
        }
        return configs[0];
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES10.glShadeModel(GLES10.GL_SMOOTH);
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glHint(GLES10.GL_PERSPECTIVE_CORRECTION_HINT, GLES20.GL_NICEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
        GLES10.glLoadIdentity();

        GLES10.glVertexPointer(3, GLES20.GL_FLOAT, 0, mBtnBuffer);
        GLES10.glTexCoordPointer(2, GLES20.GL_FLOAT, 0, mBtnCoordBuffer);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mBtnText.getTexture(0));
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);


    }

    private void init() {
        setEGLConfigChooser(this);
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        mBtnBuffer = IBufferFactory.newFloatBuffer(mBtnArrays);
        mBtnCoordBuffer = IBufferFactory.newFloatBuffer(mBtnCoords);
    }

}