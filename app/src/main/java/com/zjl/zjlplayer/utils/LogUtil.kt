package com.zjl.zjlplayer.utils

import android.util.Log

/**
 * Project Name: ZJLPlayer
 * ClassName:    LogUtil
 *
 * Description:
 *
 * @author  zjl
 * @date    2021年07月13日 15:35
 *
 * Copyright (c) 2021年, 4399 Network CO.ltd. All Rights Reserved.
 */
object LogUtil {
    private const val DEBUG = true

    fun w(msg: String) {
        if (DEBUG) {
            Log.w(Exception().stackTrace[1].className,msg)
        }
    }
}