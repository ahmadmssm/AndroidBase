package com.ams.androiddevkit.utils.services.rxSchedulersService

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

open class RxSchedulersServiceImpl: RxSchedulersService {
    override val ioThread: Scheduler = Schedulers.io()
    override val singleThread: Scheduler = Schedulers.single()
    override val newThread: Scheduler = Schedulers.newThread()
    override val uiThread: Scheduler = AndroidSchedulers.mainThread()
    override val trampolineThread: Scheduler = Schedulers.trampoline()
    override val computationThread: Scheduler = Schedulers.computation()
}
