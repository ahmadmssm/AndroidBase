package com.ams.androiddevkit.baseClasses.designPatterns.mvp

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.ams.androiddevkit.utils.RuntimePermissionsManger

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity<Presenter: BasePresenter<*>?>: AppCompatActivity(), BaseView {

    protected var runtimePermissionsManger: RuntimePermissionsManger? = null
    protected var presenter: Presenter? = null
        private set

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        runtimePermissionsManger = RuntimePermissionsManger(this)
        presenter = initPresenter()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        runtimePermissionsManger = null
    }

    @get:LayoutRes
    protected abstract val layout: Int

    protected abstract fun initPresenter(): Presenter

    override fun showLoading() {}
    override fun hideLoading() {}
}