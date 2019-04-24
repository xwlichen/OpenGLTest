package com.smart.im.media.opengltest.draw.texture;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;

import com.smart.im.media.opengltest.R;
import com.smart.im.opengl.Constants;
import com.smart.im.opengl.base.BaseShape;
import com.smart.im.opengl.utils.MatrixState;
import com.smart.im.opengl.utils.ShaderHelper;
import com.smart.im.opengl.utils.TextureHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @date : 2019/4/24 下午2:39
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class BaseCube extends BaseShape {
    private String U_VIEW_MATRIX = "u_ViewMatrix";
    private String U_MODEL_MATRIX = "u_ModelMatrix";
    private String U_PROJECTION_MATRIX = "u_ProjectionMatrix";
    private String A_POSITION = "a_Position";
    private String A_TEXTURE_COORDINATE = "a_TextureCoordinates";
    private String U_TEXTURE_UNIT = "u_TextureUnit";


    protected int uModelMatrixAttr = 0;
    protected int uViewMatrixAttr = 0;
    protected int uProjectionMatrixAttr = 0;
    protected int aPositionAttr = 0;
    protected int aTextureCoordinateAttr = 0;
    protected int uTextureUnitAttr = 0;

    private int[] mTextureId = null;


    FloatBuffer vertexFloatBuffer = ByteBuffer
            .allocateDirect(8 * 6 * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    FloatBuffer textureFloagBuffer = ByteBuffer
            .allocateDirect(8 * 6 * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    float CubeSize = 1.0f;

    float HalfCubeSize = CubeSize / 2;

    float eyeX = 0.0f;
    float eyeY = 0.0f;
    float eyeZ = 2.0f;

    float lookX = 0.0f;
    float lookY = 0.0f;
    float lookZ = 0.0f;

    float upX = 0.0f;
    float upY = 1.0f;
    float upZ = 0.0f;


    public BaseCube(Context context) {
        super(context);
        programId = ShaderHelper.buildProgramFromResource(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);

        GLES20.glUseProgram(programId);

        initVertexData();

        initTextureData();

        POSITION_COMPONENT_COUNT = 2;
    }


    // 六个面的顶点，都是一样的坐标，通过变换矩阵来转换位置进行绘制。
    private void initVertexData() {
        float faceLeft = -CubeSize / 2;
        float faceRight = -faceLeft;
        float faceTop = CubeSize / 2;
        float faceBottom = -faceTop;

        float[] vertices = {
                faceLeft, faceBottom,
                faceRight, faceBottom,
                faceLeft, faceTop,
                faceRight, faceTop
        };
        vertexFloatBuffer.put(vertices);
        vertexFloatBuffer.position(0);
    }

    // 六个面的纹理坐标，都是一样的坐标，通过变换矩阵来转换位置进行绘制。
    private void initTextureData() {
        float[] texCoords = {
                0.0f, 1.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f
        };
        textureFloagBuffer.put(texCoords);
        textureFloagBuffer.position(0);
    }


    private byte[] indices = {
            0, 1, 2, 3
    };

    private ByteBuffer byteBuffer;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        GLES20.glClearColor(0f, 0f, 0f, 1.0f);

        //打开深度检测
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        //打开背面剪裁，面剔除，优化显示速度
//        GLES30.glEnable(GLES30.GL_CULL_FACE)

        aPositionAttr = GLES20.glGetAttribLocation(programId, A_POSITION);
        uModelMatrixAttr = GLES20.glGetUniformLocation(programId, U_MODEL_MATRIX);
        uViewMatrixAttr = GLES20.glGetUniformLocation(programId, U_VIEW_MATRIX);
        uProjectionMatrixAttr = GLES20.glGetUniformLocation(programId, U_PROJECTION_MATRIX);

        aTextureCoordinateAttr = GLES20.glGetAttribLocation(programId, A_TEXTURE_COORDINATE);
        uTextureUnitAttr = GLES20.glGetUniformLocation(programId, U_TEXTURE_UNIT);

        int[] imageFileIDs = { // Image file IDs
                R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e,
                R.mipmap.f, R.mipmap.g};

        int[] animalIds = {
                R.mipmap.dog,
                R.mipmap.elephant, R.mipmap.frog,
                R.mipmap.monkey, R.mipmap.rabbit, R.mipmap.tortoise, R.mipmap.tigger
        };

        mTextureId = TextureHelper.loadCubeTexture(context, TextureHelper.CUBE, imageFileIDs);

        GLES20.glUniform1i(uTextureUnitAttr, 0);

        byteBuffer = ByteBuffer.allocateDirect(indices.length * Constants.BYTES_PRE_BYTE).put(indices);
        byteBuffer.position(0);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);

        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        float left = -ratio;
        float bottom = -1.0f;
        float top = 1.0f;
        float near = 1.0f;
        float far = 6.0f;

        MatrixState.setCamera(eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        MatrixState.setProjectFrustum(left, ratio, bottom, top, near, far);

        MatrixState.setInitStack();
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        vertexFloatBuffer.position(0);
        GLES20.glVertexAttribPointer(aPositionAttr, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexFloatBuffer);
        GLES20.glEnableVertexAttribArray(aPositionAttr);

        textureFloagBuffer.position(0);
        GLES20.glVertexAttribPointer(aTextureCoordinateAttr, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, textureFloagBuffer);
        GLES20.glEnableVertexAttribArray(aTextureCoordinateAttr);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glUniformMatrix4fv(uProjectionMatrixAttr, 1, false, MatrixState.getProMatrix(), 0);
        GLES20.glUniformMatrix4fv(uViewMatrixAttr, 1, false, MatrixState.getVMatrix(), 0);

        onDrawCubePre();

        MatrixState.pushMatrix();

        // 开始绘制立方体的每个面
        // 前面
        MatrixState.pushMatrix();
        MatrixState.translate(0f, 0f, HalfCubeSize);
        GLES20.glUniformMatrix4fv(uModelMatrixAttr, 1, false, MatrixState.getMMatrix(), 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId[0]);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, indices.length, GLES20.GL_UNSIGNED_BYTE, byteBuffer);
        MatrixState.popMatrix();

        // 后面
        MatrixState.pushMatrix();
        MatrixState.translate(0f, 0f, -HalfCubeSize);
        MatrixState.rotate(180f, 0f, 1f, 0f);
        GLES20.glUniformMatrix4fv(uModelMatrixAttr, 1, false, MatrixState.getMMatrix(), 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId[1]);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, indices.length, GLES20.GL_UNSIGNED_BYTE, byteBuffer);
        MatrixState.popMatrix();

        // 上面
        MatrixState.pushMatrix();
        MatrixState.translate(0f, HalfCubeSize, 0f);
        MatrixState.rotate(-90f, 1f, 0f, 0f);
        GLES20.glUniformMatrix4fv(uModelMatrixAttr, 1, false, MatrixState.getMMatrix(), 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId[2]);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, indices.length, GLES20.GL_UNSIGNED_BYTE, byteBuffer);
        MatrixState.popMatrix();


        //下面
        MatrixState.pushMatrix();
        MatrixState.translate(0f, -HalfCubeSize, 0f);
        MatrixState.rotate(90f, 1f, 0f, 0f);
        GLES20.glUniformMatrix4fv(uModelMatrixAttr, 1, false, MatrixState.getMMatrix(), 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId[3]);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, indices.length, GLES20.GL_UNSIGNED_BYTE, byteBuffer);
        MatrixState.popMatrix();


        // 左面
        MatrixState.pushMatrix();
        MatrixState.translate(HalfCubeSize, 0f, 0f);
        MatrixState.rotate(-90f, 1f, 0f, 0f);
        MatrixState.rotate(90f, 0f, 1f, 0f);
        GLES20.glUniformMatrix4fv(uModelMatrixAttr, 1, false, MatrixState.getMMatrix(), 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId[4]);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, indices.length, GLES20.GL_UNSIGNED_BYTE, byteBuffer);
        MatrixState.popMatrix();

        // 右面
        MatrixState.pushMatrix();
        MatrixState.translate(-HalfCubeSize, 0f, 0f);
        MatrixState.rotate(90f, 1f, 0f, 0f);
        MatrixState.rotate(-90f, 0f, 1f, 0f);
        GLES20.glUniformMatrix4fv(uModelMatrixAttr, 1, false, MatrixState.getMMatrix(), 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId[5]);
//        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, indices.size, GLES20.GL_UNSIGNED_BYTE, byteBuffer)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        MatrixState.popMatrix();


        MatrixState.popMatrix();

        GLES20.glDisableVertexAttribArray(aPositionAttr);
        GLES20.glDisableVertexAttribArray(aTextureCoordinateAttr);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public void onDrawCubePre() {
    }
}
