package com.ams.androiddevkit.utils.extensions

import com.ams.androiddevkit.utils.viewArguments.fragmentArgs.BaseFragmentArgs
import com.ams.androiddevkit.utils.viewArguments.fragmentArgs.FragmentArgs
import com.ams.androiddevkit.utils.viewArguments.fragmentArgs.NullableFragmentArgs

inline fun <reified A: BaseFragmentArgs> fragmentArgs() = FragmentArgs(A::class)

inline fun <reified A: BaseFragmentArgs> nullableFragmentArgs() = NullableFragmentArgs(A::class)