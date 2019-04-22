package com.smart.im.media.opengltest;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.smart.im.media.opengltest.renderer.LineRenderer;
import com.smart.im.media.opengltest.renderer.PointRenderer;

public class TestActivity_01 extends AppCompatActivity {

    private PointRenderer pointRenderer;
    private LineRenderer lineRenderer;
    PointGLSurfaceView pointGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_01);
        pointRenderer=new PointRenderer(this);
        lineRenderer=new LineRenderer(this);
        pointGLSurfaceView=new PointGLSurfaceView(this);

        FrameLayout baseContainer=findViewById(R.id.baseContainer);
        baseContainer.removeAllViews();
        baseContainer.addView(pointGLSurfaceView);
    }



    class PointGLSurfaceView extends GLSurfaceView{

        public PointGLSurfaceView(Context context) {
            super(context);
            setEGLContextClientVersion(2);

            setRenderer(lineRenderer);
            setRenderMode(RENDERMODE_WHEN_DIRTY);
        }
    }


}
