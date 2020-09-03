package com.ams.androiddevkit.utils.services.rxSchedulersService

import io.reactivex.rxjava3.core.Scheduler

interface RxSchedulersService {
    val uiThread: Scheduler
    val ioThread: Scheduler
    val singleThread: Scheduler
    val newThread: Scheduler
    val trampolineThread: Scheduler
    val computationThread: Scheduler
}
