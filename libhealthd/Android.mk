# Copyright 2016 The Android Open Source Project


LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_SRC_FILES := healthd_board_msm8939.cpp
LOCAL_MODULE := libhealthd.msm8939
LOCAL_C_INCLUDES := system/core/healthd
LOCAL_CFLAGS := -Werror

include $(BUILD_STATIC_LIBRARY)
