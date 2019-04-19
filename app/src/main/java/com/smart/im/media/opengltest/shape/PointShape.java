package com.smart.im.media.opengltest.shape;

import android.content.Context;
import android.opengl.GLES20;

import com.smart.im.media.opengltest.R;
import com.smart.im.opengl.base.BaseShape;
import com.smart.im.opengl.utils.ShaderHelper;
import com.smart.im.opengl.utils.VertexArray;

import static android.opengl.GLES20.GL_POINTS;

/**
 * @date : 2019/4/18 下午3:35
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class PointShape extends BaseShape {

    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Posititon";

    private int aColorLocation;
    private int aPositionLocation;

    float[] pointVertex = {
            0f, 0f
    };

    public PointShape(Context context) {
        super(context);

        programId = ShaderHelper.buildProgramFromResource(context,
                R.raw.point_vertex_shader,
                R.raw.point_fragment_shader);
        GLES20.glUseProgram(programId);

        vertexArray = new VertexArray(pointVertex);
        POSITION_COMPONENT_COUNT = 2;

    }

    public void bindData(){
        aColorLocation =  GLES20.glGetUniformLocation(programId, U_COLOR);
        aPositionLocation =  GLES20.glGetAttribLocation(programId, A_POSITION);
        // 给绑定的值赋值，也就是从顶点数据那里开始读取，每次读取间隔是多少
        vertexArray.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT,
                0);

    }

    public void draw() {
        // 给绑定的值赋值
        GLES20.glUniform4f(aColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GL_POINTS, 0, 1);
    }

}
