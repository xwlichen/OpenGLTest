package com.smart.im.media.opengltest.renderer;

import android.content.Context;
import android.opengl.GLES20;

import com.blankj.utilcode.util.LogUtils;
import com.smart.im.media.opengltest.shape.PointShape;
import com.smart.im.opengl.base.BaseRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

/**
 * @date : 2019/4/18 下午2:09
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class PointRenderer extends BaseRenderer {
    private PointShape point;
    private Context context;

    public PointRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        point=new PointShape(context);
        point.bindData();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        LogUtils.d("width is " + width + " height is " + height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清屏
        GLES20.glClear(GL_COLOR_BUFFER_BIT);
        // 绘制
        point.draw();
    }
}
