package com.smart.im.media.opengltest;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.smart.im.media.opengltest.draw.texture.RotateRectCub;
import com.smart.im.media.opengltest.renderer.RotateRectCubeRenderer;
import com.smart.im.media.opengltest.renderer.TextureRenderer;

public class TestActivity_01 extends AppCompatActivity {

    //    private PointRenderer pointRenderer;
//    private LineRenderer lineRenderer;
    TextureRenderer textureRenderer;
    RotateRectCubeRenderer rotateRectCubeRenderer;

    RotateRectCub rotateRectCub;
    PointGLSurfaceView pointGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_01);
//        pointRenderer=new PointRenderer(this);
//        lineRenderer=new LineRenderer(this);
        textureRenderer = new TextureRenderer(this);
        rotateRectCubeRenderer = new RotateRectCubeRenderer(this);

        rotateRectCub = new RotateRectCub(this);
        pointGLSurfaceView=new PointGLSurfaceView(this);

        FrameLayout baseContainer=findViewById(R.id.baseContainer);
        baseContainer.removeAllViews();
        baseContainer.addView(pointGLSurfaceView);
    }



    class PointGLSurfaceView extends GLSurfaceView{

        public PointGLSurfaceView(Context context) {
            super(context);
            setEGLContextClientVersion(2);

            setRenderer(rotateRectCubeRenderer);
            setRenderMode(RENDERMODE_CONTINUOUSLY);
        }
    }


}
