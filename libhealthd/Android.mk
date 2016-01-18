# Copyright 2016 The Android Open Source Project


LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_SRC_FILES := healthd_board_cp8675.cpp
LOCAL_MODULE := libhealthd.cp8675
LOCAL_C_INCLUDES := system/core/healthd
LOCAL_CFLAGS := -Werror

include $(BUILD_STATIC_LIBRARY)
