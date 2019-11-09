package com.ams.androiddevkit.baseClasses.networking

interface IRetrofitClient {
    fun <APIsInterface> getRetrofitClient(restAPIsInterface: Class<APIsInterface>): APIsInterface
}