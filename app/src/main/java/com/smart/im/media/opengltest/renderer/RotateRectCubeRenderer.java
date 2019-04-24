package com.smart.im.media.opengltest.renderer;

import android.content.Context;

import com.smart.im.media.opengltest.draw.texture.RotateRectCub;
import com.smart.im.opengl.base.BaseRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @date : 2019/4/24 下午4:30
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class RotateRectCubeRenderer extends BaseRenderer {
    private Context context;
    RotateRectCub rotateRectCub;

    public RotateRectCubeRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        rotateRectCub = new RotateRectCub(context);
        rotateRectCub.onSurfaceCreated(gl, config);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        rotateRectCub.onSurfaceChanged(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        rotateRectCub.onDrawFrame(gl);

    }
}
