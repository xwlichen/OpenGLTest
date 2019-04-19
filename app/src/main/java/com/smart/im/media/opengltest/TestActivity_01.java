package com.smart.im.media.opengltest;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smart.im.media.opengltest.Renderer.PointRenderer;

public class TestActivity_01 extends AppCompatActivity {

    private PointRenderer pointRenderer;
    PointGLSurfaceView pointGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pointRenderer=new PointRenderer(this);
        pointGLSurfaceView=new PointGLSurfaceView(this);
        setContentView(pointGLSurfaceView);
    }



    class PointGLSurfaceView extends GLSurfaceView{

        public PointGLSurfaceView(Context context) {
            super(context);

            setRenderer(pointRenderer);
            setRenderMode(RENDERMODE_WHEN_DIRTY);
        }
    }
}
