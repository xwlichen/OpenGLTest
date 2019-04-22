package com.smart.im.media.opengltest.renderer;

import android.content.Context;
import android.opengl.GLES20;

import com.smart.im.media.opengltest.draw.graphics.Line;
import com.smart.im.media.opengltest.draw.texture.TriangleTexure;
import com.smart.im.opengl.base.BaseRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @date : 2019/4/20 上午10:25
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class TextureRenderer extends BaseRenderer {
    private Context context;
    private Line line;
    private TriangleTexure triangleTexure;

    public TextureRenderer(Context context) {
        this.context = context;

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        line=new Line(context);
        triangleTexure=new TriangleTexure(context);
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
//        line.onSurfaceCreated(gl,config);
        triangleTexure.onSurfaceCreated(gl,config);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        triangleTexure.onSurfaceChanged(gl,width,height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        triangleTexure.onDrawFrame(gl);

    }
}
