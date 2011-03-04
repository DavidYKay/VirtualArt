/* San Angeles Observation OpenGL ES version example
 * Copyright 2009 The Android Open Source Project
 * All rights reserved.
 *
 * This source is free software; you can redistribute it and/or
 * modify it under the terms of EITHER:
 *   (1) The GNU Lesser General Public License as published by the Free
 *       Software Foundation; either version 2.1 of the License, or (at
 *       your option) any later version. The text of the GNU Lesser
 *       General Public License is included with this source in the
 *       file LICENSE-LGPL.txt.
 *   (2) The BSD-style license that is included with this source in
 *       the file LICENSE-BSD.txt.
 *
 * This source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the files
 * LICENSE-LGPL.txt and LICENSE-BSD.txt for more details.
 */
#include <jni.h>
#include <sys/time.h>
#include <time.h>
#include <android/log.h>
#include <stdint.h>

static int  sWindowWidth  = 320;
static int  sWindowHeight = 480;

/* Call to initialize the graphics state */
void
Java_com_artmented_application_DemoRenderer_nativeInit( JNIEnv*  env )
{
    // Can call these because since they are declared as extern
    importGLInit();
    appInit();
}

/* Call when window is resized */
void
Java_com_artmented_application_DemoRenderer_nativeResize( JNIEnv*  env, jobject  thiz, jint w, jint h )
{
    sWindowWidth  = w;
    sWindowHeight = h;
    __android_log_print(ANDROID_LOG_INFO, "Artmented", "resize w=%d h=%d", w, h);
}

/* Call to finalize the graphics state */
void
Java_com_artmented_application_DemoRenderer_nativeDone( JNIEnv*  env )
{
    appDeinit();
    importGLDeinit();
}

/* This is called to indicate to the render loop that it should
 * stop as soon as possible.
 */
void
Java_com_artmented_application_DemoGLSurfaceView_nativePause( JNIEnv*  env )
{
    // TODO
}

/* Call to render the next GL frame */
void
Java_com_artmented_application_DemoRenderer_nativeRender( JNIEnv*  env )
{
    //__android_log_print(ANDROID_LOG_INFO, "Artmented", "curTime=%ld", curTime);

    appRender(sWindowWidth, sWindowHeight);
}
