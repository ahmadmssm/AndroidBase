package com.ams.androiddevkit.utils.kotlinLiveBus.core

fun <T> createLiveEvent(liveEventClass: Class<T>): BaseLiveEvent<T> = when (liveEventClass) {
    BusLiveEvent::class.java -> BusLiveEvent()
    LiveBusSingleLiveEvent::class.java -> LiveBusSingleLiveEvent()
    StickyLiveEvent::class.java -> StickyLiveEvent()
    StickySingleLiveEvent::class.java -> StickySingleLiveEvent()
    else -> BusLiveEvent()
}
