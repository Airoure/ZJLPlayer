package com.zjl.zjlplayer.render

import android.graphics.SurfaceTexture
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.zjl.zjlplayer.MyGLSurfaceView

/**
 * Project Name: ZJLPlayer
 * ClassName:    BaseGLRender
 *
 * Description:
 *
 * @author  zjl
 * @date    2021年07月13日 16:30
 *
 * Copyright (c) 2021年, 4399 Network CO.ltd. All Rights Reserved.
 */
abstract class BaseGLRender : GLSurfaceView.Renderer,SurfaceTexture.OnFrameAvailableListener{
    protected fun loadShader(shaderType: Int, source: String?): Int {
        var shader = GLES20.glCreateShader(shaderType)
        if (shader != 0) {
            GLES20.glShaderSource(shader, source)
            GLES20.glCompileShader(shader)
        }
        return shader
    }

    protected open fun createProgram(vertexSource: String?, fragmentSource: String?): Int {
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource)
        val pixelShader = loadShader(
            GLES20.GL_FRAGMENT_SHADER,
            fragmentSource
        )
        val program = GLES20.glCreateProgram()
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader)
            GLES20.glAttachShader(program, pixelShader)
            GLES20.glLinkProgram(program)
        }
        return program
    }


}