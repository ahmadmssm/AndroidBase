package com.ams.androiddevkit.utils.kotlinLiveBus.core

fun <T> createLiveEvent(liveEventClass: Class<T>): BaseLiveEvent<T> = when (liveEventClass) {
    LiveEvent::class.java -> LiveEvent()
    SingleLiveEvent::class.java -> SingleLiveEvent()
    StickyLiveEvent::class.java -> StickyLiveEvent()
    StickySingleLiveEvent::class.java -> StickySingleLiveEvent()
    else -> LiveEvent()
}
