package com.zjl.zjlplayer.effect

import android.opengl.GLSurfaceView
import com.zjl.zjlplayer.MyGLSurfaceView

/**
 * Project Name: ZJLPlayer
 * ClassName:    NoEffect
 *
 * Description:
 *
 * @author  zjl
 * @date    2021年07月13日 16:54
 *
 * Copyright (c) 2021年, 4399 Network CO.ltd. All Rights Reserved.
 */
class NoEffect : MyGLSurfaceView.IShader {
    override fun getShader(glSurfaceView: GLSurfaceView): String {
        return "#extension GL_OES_EGL_image_external : require\n" +
                "precision mediump float;\n" +
                "varying vec2 vTextureCoord;\n" +
                "uniform samplerExternalOES sTexture;\n" + "void main() {\n" +
                "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
                "}\n";
    }
}