LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := artmented

LOCAL_CFLAGS := -DANDROID_NDK \
                -DDISABLE_IMPORTGL

LOCAL_SRC_FILES := \
    importgl.c \
    demo.c \
    app-android.c \

# Android automatically links the C, Math, and C++ libraries.
# Include the libraries (in-order)
# - OpenGL ES 1.x Library <GLES/gl.h> and <GLES/gtext.h> (Android API level 4)
#   NOTE: OpenGL ES 2.0 containing headers <GLES2/gl2.h> and <GLES2/gl2ext.h> 
#   is in Android API level 5. The flag for this would be '-lGLESv2
# - Android Dynamic Linker <dlfcn.h>
# - Android-specific Log Support <android/log.h>
LOCAL_LDLIBS := -lGLESv1_CM -ldl -llog

include $(BUILD_SHARED_LIBRARY)
