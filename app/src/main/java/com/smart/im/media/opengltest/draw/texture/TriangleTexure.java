package com.smart.im.media.opengltest.draw.texture;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.blankj.utilcode.util.LogUtils;
import com.smart.im.media.opengltest.R;
import com.smart.im.opengl.base.BaseShape;
import com.smart.im.opengl.utils.ShaderHelper;
import com.smart.im.opengl.utils.TextureHelper;
import com.smart.im.opengl.utils.VertexArray;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.Matrix.setIdentityM;

/**
 * Created by glumes on 2017/7/30.
 */

public class TriangleTexure extends BaseShape {


    private float[] vertexArrayData = {

            0.5f, 0f,
            0f, 1.0f,
            1.0f, 1.0f
    };

    private float[] textureArrayData = {
            0.5f, 0f,
            0f, 1.0f,
            1.0f, 1.0f
    };


    private static final String U_VIEW_MATRIX = "u_ViewMatrix";
    private static final String U_MODEL_MATRIX = "u_ModelMatrix";
    private static final String U_PROJECTION_MATRIX = "u_ProjectionMatrix";
    private static final String A_POSITION = "a_Position";
    private static final String A_TEXTURE_COORDINATE = "a_TextureCoordinates";
    private static final String U_TEXTURE_UNIT = "u_TextureUnit";


    private int uModelMatrixAttr = 0;
    private int uViewMatrixAttr = 0;
    private int uProjectionMatrixAttr = 0;
    private int aPositionAttr = 0;
    private int aTextureCoordinateAttr = 0;
    private int uTextureUnitAttr = 0;
    private int mTextureId = 0;

    private VertexArray vertexArray;
    private VertexArray textureArray;


    public TriangleTexure(Context context) {
        super(context);

        programId = ShaderHelper.buildProgramFromResource(context, R.raw.texture_vertex_shader
                , R.raw.texture_fragment_shader);

        GLES20.glUseProgram(programId);

        vertexArray = new VertexArray(vertexArrayData);
        textureArray = new VertexArray(textureArrayData);

        POSITION_COMPONENT_COUNT = 2;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        ;
        GLES20.glClearColor(0f, 0f, 0f, 1.0f);
        aPositionAttr = glGetAttribLocation(programId, A_POSITION);
        uModelMatrixAttr = glGetUniformLocation(programId, U_MODEL_MATRIX);
        uViewMatrixAttr = glGetUniformLocation(programId, U_VIEW_MATRIX);
        uProjectionMatrixAttr = glGetUniformLocation(programId, U_PROJECTION_MATRIX);


        aTextureCoordinateAttr = glGetAttribLocation(programId, A_TEXTURE_COORDINATE);
        uTextureUnitAttr = glGetUniformLocation(programId, U_TEXTURE_UNIT);


        mTextureId = TextureHelper.loadTexture(context, R.mipmap.texture);

        GLES20.glUniform1i(uTextureUnitAttr, 0);


        IntBuffer intBuffer = IntBuffer.allocate(1);

        GLES20.glGetIntegerv(GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, intBuffer);

//        LogUtils.e("max combined texture image units " + intBuffer[0].)

        Matrix.setIdentityM(modelMatrix, 0);

//        Matrix.rotateM(modelMatrix,0,180f,1f,0f,0f)
        Matrix.translateM(modelMatrix, 0, 0f, 0.5f, 0f);

        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setIdentityM(projectionMatrix, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);


        LogUtils.e("width is " + width + " height is " + height);


        GLES20.glViewport(0, 0, width, height);

        float aspectRatio = width > height ? (float) width / (float) height : (float) height / (float) width;

        if (width > height) {
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, 0f, 10f);
        } else {
            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, 0f, 10f);
        }


    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        GLES20.glClearColor(0f, 0f, 0f, 1.0f);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        glUniformMatrix4fv(uModelMatrixAttr, 1, false, modelMatrix, 0);
        glUniformMatrix4fv(uViewMatrixAttr, 1, false, viewMatrix, 0);
        glUniformMatrix4fv(uProjectionMatrixAttr, 1, false, projectionMatrix, 0);

       vertexArray.setVertexAttribPointer(0, aPositionAttr, POSITION_COMPONENT_COUNT, 0);
        textureArray.setVertexAttribPointer(0, aTextureCoordinateAttr, POSITION_COMPONENT_COUNT, 0);

        GLES20. glActiveTexture(GL_TEXTURE0);

        GLES20. glBindTexture(GL_TEXTURE_2D, mTextureId);

        GLES20.glDrawArrays(GL_TRIANGLES, 0, 3);

        GLES20.glDisableVertexAttribArray(aPositionAttr);
        GLES20.glDisableVertexAttribArray(aTextureCoordinateAttr);
        GLES20.glBindTexture(GL_TEXTURE_2D, 0);

    }
}
