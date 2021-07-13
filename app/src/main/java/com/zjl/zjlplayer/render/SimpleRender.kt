package com.zjl.zjlplayer.render

import android.graphics.SurfaceTexture
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.zjl.zjlplayer.MyGLSurfaceView
import com.zjl.zjlplayer.effect.NoEffect
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Project Name: ZJLPlayer
 * ClassName:    SimpleRender
 *
 * Description:
 *
 * @author  zjl
 * @date    2021年07月13日 16:35
 *
 * Copyright (c) 2021年, 4399 Network CO.ltd. All Rights Reserved.
 */
class SimpleRender : BaseGLRender() {

    private val mTriangleVerticesData = floatArrayOf(
        -1.0f, -1.0f, 0.0f,
        0.0f, 0.0f, 1.0f,
        -1.0f, 0.0f, 1.0f,
        0.0f, -1.0f, 1.0f,
        0.0f, 0.0f, 1.0f,
        1.0f, 1.0f, 0.0f,
        1.0f, 1.0f
    )

    private var mEffectFilter: MyGLSurfaceView.IShader = NoEffect()

    protected var mGLSurfaceView: GLSurfaceView? = null

    protected var mMVPMatrix = FloatArray(16)

    protected var mSTMatrix = FloatArray(16)

    protected val GL_TEXTURE_EXTERNAL_OES = 0x8D65

    private val mVertexShader = "uniform mat4 uMVPMatrix;\n" +
            "uniform mat4 uSTMatrix;\n" +
            "attribute vec4 aPosition;\n" +
            "attribute vec4 aTextureCoord;\n" +
            "varying vec2 vTextureCoord;\n" +
            "void main() {\n" +
            "  gl_Position = uMVPMatrix * aPosition;\n" +
            "  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n" +
            "}\n";

    private var mProgram = 0

    private val mTextureID = intArrayOf()

    private var mTriangleVertices: FloatBuffer

    private var maPositionHandle: Int = 0

    private var maTextureHandle: Int = 0

    private var muMVPMatrixHandle: Int = 0

    private var muSTMatrixHandle: Int = 0

    private var mUpdateSurface: Boolean = false

    private var mSurface: SurfaceTexture? = null

    init {
        mTriangleVertices =
            ByteBuffer.allocateDirect(4 * mTriangleVerticesData.size).order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        mTriangleVertices.put(mTriangleVerticesData).position(0)

        Matrix.setIdentityM(mMVPMatrix,0)
        Matrix.setIdentityM(mSTMatrix,0)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mProgram = createProgram(getVertexShader(), getFragmentShader())
        if (mProgram == 0) {
            return
        }
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition")
        maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord")
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        muSTMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uSTMatrix")
        GLES20.glGenTextures(2, mTextureID, 0)
        GLES20.glBindTexture(
            GL_TEXTURE_EXTERNAL_OES,
            mTextureID[0]
        )

        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE
        )

        mSurface = SurfaceTexture(mTextureID[0])
        mSurface?.setOnFrameAvailableListener(this)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        if (mUpdateSurface) {
            mSurface?.updateTexImage()
            mSurface?.getTransformMatrix(mSTMatrix)
            mUpdateSurface = false
        }

        initDrawFrame()
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        mUpdateSurface = true
    }

    protected fun initDrawFrame() {
        
    }

    protected fun getVertexShader() = mVertexShader

    protected fun getFragmentShader(): String {
        return mEffectFilter.getShader(mGLSurfaceView!!)
    }

    fun setGLSurfaceView(glSurfaceView: GLSurfaceView) {
        mGLSurfaceView = glSurfaceView
    }

    fun setEffect(effectFilter: MyGLSurfaceView.IShader) {
        mEffectFilter = effectFilter
    }
}