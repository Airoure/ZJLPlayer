package com.zjl.zjlplayer

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.SurfaceView

/**
 * Project Name: ZJLPlayer
 * ClassName:    MyGLSurfaceView
 *
 * Description:
 *
 * @author  zjl
 * @date    2021年07月13日 16:21
 *
 * Copyright (c) 2021年, 4399 Network CO.ltd. All Rights Reserved.
 */
class MyGLSurfaceView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : GLSurfaceView(context, attributeSet) {

    private var mRenderer: GLSurfaceView.Renderer? = null

    fun initRenderer(renderer: GLSurfaceView.Renderer) {
        mRenderer = renderer
        setRenderer(renderer)

    }

    interface IShader {
        fun getShader(glSurfaceView: GLSurfaceView): String
    }
}