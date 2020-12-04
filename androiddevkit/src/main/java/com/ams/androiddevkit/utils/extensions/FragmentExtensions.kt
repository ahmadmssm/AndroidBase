package com.ams.androiddevkit.utils.extensions

import com.ams.androiddevkit.utils.viewArguments.BaseViewArgs
import com.ams.androiddevkit.utils.viewArguments.fragmentArgs.FragmentArgs
import com.ams.androiddevkit.utils.viewArguments.fragmentArgs.NullableFragmentArgs

inline fun <reified A: BaseViewArgs> fragmentArgs() = FragmentArgs(A::class)

inline fun <reified A: BaseViewArgs> nullableFragmentArgs() = NullableFragmentArgs(A::class)