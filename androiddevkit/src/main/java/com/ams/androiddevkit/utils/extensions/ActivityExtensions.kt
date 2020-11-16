package com.ams.androiddevkit.utils.extensions

import com.ams.androiddevkit.utils.viewArguments.BaseViewArgs
import com.ams.androiddevkit.utils.viewArguments.activityArgs.ActivityArgs
import com.ams.androiddevkit.utils.viewArguments.activityArgs.NullableActivityArgs

inline fun <reified A: BaseViewArgs> activityArgs() = ActivityArgs(A::class)

inline fun <reified A: BaseViewArgs> nullableActivityArgs() = NullableActivityArgs(A::class)