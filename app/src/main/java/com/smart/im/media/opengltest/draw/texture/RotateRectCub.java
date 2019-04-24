package com.smart.im.media.opengltest.draw.texture;

import android.content.Context;
import android.opengl.GLES20;

import com.smart.im.opengl.utils.MatrixState;

import java.util.concurrent.TimeUnit;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @date : 2019/4/24 下午3:27
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class RotateRectCub extends BaseCube {
    float eyeDistance = 2.0f;
    int num = 0;
    int RotateNum = 180;
    float radian = (float) (2 * Math.PI / RotateNum);

    public RotateRectCub(Context context) {
        super(context);
    }

    boolean isPlus = true;
    float distance = eyeZ;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (isPlus) {

                            distance = distance + 0.1f;
                        } else {
                            distance = distance - 0.1f;

                        }


                        if (distance < 2.0f) {
                            isPlus = true;
                        }

                        if (distance > 5.0f) {
                            isPlus = false;
                        }
//                    eyeZ = distance

                        eyeX = (float) (eyeDistance * Math.sin((double) (radian * num)));
                        eyeZ = (float) (distance * Math.cos((double) (radian * num)));
                        num++;
                        if (num > 360) {
                            num = 0;
                        }
                        updateCamera();
                        // 将物体调整一下，可以看到三个面
                        MatrixState.rotate(-45f, 0f, 1f, 0f);
                        MatrixState.rotate(45f, 1f, 0f, 0f);
                    }
                });

    }


    private void updateCamera() {
        MatrixState.setCamera(eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
        GLES20.glUniformMatrix4fv(uViewMatrixAttr, 1, false, MatrixState.getVMatrix(), 0);
    }
}
