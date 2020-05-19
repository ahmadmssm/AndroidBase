package com.ams.androiddevkit.utils.kotlinLiveBus.core

import com.ams.khdmaApp.common.utils.kotlinLiveBus.utils.assertMainThread
import com.ams.khdmaApp.common.utils.kotlinLiveBus.utils.exceptionWrapper

@Suppress("unused", "UNCHECKED_CAST", "UNUSED_PARAMETER", "MemberVisibilityCanBePrivate")
object KotlinLiveBus {

    private const val CAST_EXCEPTION_MESSAGE = "LiveEvent casting exception! LiveEventBase saved on the " + "bus doesn't have the LiveEvent type"
    private var eventsMap: HashMap<String, BaseLiveEvent<out Any?>> = HashMap()

    fun <T> postStickyEvent(tag: String, eventValue: T) { setLiveEventValue(tag, eventValue, StickyLiveEvent::class.java) }

    fun <T> postSingleEvent(tag: String, eventValue: T) { setLiveEventValue(tag, eventValue, SingleLiveEvent::class.java) }

    fun <T> postEvent(tag: String, eventValue: T) { setLiveEventValue(tag, eventValue, LiveEvent::class.java) }

    fun removeEvent(tag: String) { if (!eventsMap.contains(tag)) eventsMap.remove(tag) }

    /**
     * Removes the event equals to @param liveEvent from the Bus.
     * @return true if the Bus contains the `LiveEvent` passed to the function,
     * false otherwise
     */
    fun <T> removeEvent(liveEvent: BaseLiveEvent<T>): Boolean {
        if (!eventsMap.containsValue(liveEvent)) return false
        for (entry in eventsMap.entries) {
            if (entry.value != liveEvent) continue
            eventsMap.remove(entry.key)
            return true
        }
        return false
    }

    /**
     * Removes the live event specified by the tag
     * @return true if an event with the passed tag exists and removed, false otherwise
     */
    fun removeEventWithTag(tag: String): Boolean {
        eventsMap.remove(tag) ?: return false
        return true
    }

    private fun <T, K> setLiveEventValue(tag: String, eventValue: T, liveEventType: Class<K>) {
        assertMainThread(liveEventType.name)
        setValue(tag, eventValue, liveEventType, fun(liveEvent, eventValue) {
            liveEvent?.value = eventValue
        })
    }

    private fun <T, K> postLiveEventValue(tag: String, eventValue: T, liveEventType: Class<K>) {
        setValue(tag, eventValue, liveEventType, fun(liveEvent, eventValue) {
            liveEvent?.postValue(eventValue)
        })
    }

    private fun <T, K> setValue(tag: String, eventValue: T, liveEventType: Class<K>, func: (liveEvent: BaseLiveEvent<T>?, value: T) -> Unit) {
        if (!eventsMap.contains(tag)) eventsMap[tag] = createLiveEvent(liveEventType)
        exceptionWrapper(CAST_EXCEPTION_MESSAGE) { func(eventsMap[tag] as BaseLiveEvent<T>, eventValue) }
    }

    /**
     * This function creates a LiveEvent object and adds it to the
     * mEvents hashMap if necessary, otherwise it just updates the event's value
     *
     * @param tag The tag for the event
     * @param eventValue the value to be set to the event
     */
    fun <T> setLiveEventValue(tag: String, eventValue: T) {
        setLiveEventValue(tag, eventValue, LiveEvent::class.java)
    }

    /**
     * This function creates a LiveEvent object and adds it to the mEvents hashMap
     * if necessary, otherwise it just updates the event's value on the background thread
     *
     * @param tag The tag for the event
     * @param eventValue the value to be set to the event
     */
    fun <T> postLiveEventValue(tag: String, eventValue: T) {
        postLiveEventValue(tag, eventValue, LiveEvent::class.java)
    }

    /**
     * This function creates a `SingleLiveEvent` object and adds it to the
     * mEvents hashMap if necessary, otherwise it just updates the event's value
     *
     * @param tag The tag for the event
     * @param eventValue the value to be set to the event
     */
    fun <T> setSingleLiveEventValue(tag: String, eventValue: T) {
        setLiveEventValue(tag, eventValue, SingleLiveEvent::class.java)
    }

    /**
     * This function creates a `SingleLiveEvent` object and adds it to the mEvents hashMap
     * if necessary, otherwise it just updates the event's value on the background thread
     *
     * @param tag The tag for the event
     * @param eventValue the value to be set to the event
     */
    fun <T> postSingleLiveEventValue(tag: String, eventValue: T) {
        postLiveEventValue(tag, eventValue, SingleLiveEvent::class.java)
    }

    /**
     * This function creates a `StickySingleLiveEvent` object and adds it to the
     * mEvents hashMap if necessary, otherwise it just updates the event's value
     *
     * @param tag The tag for the event
     * @param eventValue the value to be set to the event
     */
    fun <T> setStickySingleLiveEventValue(tag: String, eventValue: T) {
        setLiveEventValue(tag, eventValue, StickySingleLiveEvent::class.java)
    }

    /**
     * This function creates a `StickySingleLiveEvent` object and adds it to the mEvents hashMap
     * if necessary, otherwise it just updates the event's value on the background thread
     *
     * @param tag The tag for the event
     * @param eventValue the value to be set to the event
     */
    fun <T> postStickySingleLiveEventValue(tag: String, eventValue: T) {
        postLiveEventValue(tag, eventValue, StickySingleLiveEvent::class.java)
    }

    /**
     * This function creates a `StickyLiveEvent` object and adds it to the
     * mEvents hashMap if necessary, otherwise it just updates the event's value
     *
     * @param tag The tag for the event
     * @param eventValue the value to be set to the event
     */
    fun <T> setStickyLiveEventValue(tag: String, eventValue: T) {
        setLiveEventValue(tag, eventValue, StickyLiveEvent::class.java)
    }

    /**
     * This function creates a `StickyLiveEvent` object and adds it to the mEvents hashMap
     * if necessary, otherwise it just updates the event's value on the background thread
     *
     * @param tag The tag for the event
     * @param eventValue the value to be set to the event
     */
    fun <T> postStickyLiveEventValue(tag: String, eventValue: T) {
        postLiveEventValue(tag, eventValue, StickyLiveEvent::class.java)
    }

    fun getEvent(tag: String): BaseLiveEvent<out Any?>? {
        if (eventsMap.containsKey(tag)) {
            return eventsMap[tag]
        }
        return null
    }

    /**
     * Returns the `LiveEvent` object, creates one if necessary
     * @return the LiveEvent object specified by the @param tag
     */
    fun <T> getLiveEvent(tag: String, type: Class<T>): BaseLiveEvent<T> = if (eventsMap.containsKey(tag)) {
        exceptionWrapper(CAST_EXCEPTION_MESSAGE, fun(): BaseLiveEvent<T> {
            return eventsMap[tag] as LiveEvent<T>
        })
    } else {
            val liveEvent = LiveEvent<T>()
            eventsMap[tag] = liveEvent
            liveEvent
    }

    /**
     * Returns the `SingleLiveEvent` object, creates one if necessary
     * @return the `SingleLiveEvent` object specified by the @param tag
     */
    fun <T> getSingleLiveEvent(tag: String, type: Class<T>): BaseLiveEvent<T> {
        return if (eventsMap.containsKey(tag)) {
            exceptionWrapper(CAST_EXCEPTION_MESSAGE, fun(): BaseLiveEvent<T> {
                return eventsMap[tag] as SingleLiveEvent<T>
            })
        } else {
            val liveEvent = SingleLiveEvent<T>()
            eventsMap[tag] = liveEvent
            liveEvent
        }
    }

    /**
     * Returns the `StickyLiveEvent` object, creates one if necessary
     * @return the `StickyLiveEvent` object specified by the @param tag
     */
    fun <T> getStickyLiveEvent(tag: String, type: Class<T>): BaseLiveEvent<T> {
        return if (eventsMap.containsKey(tag)) {
            exceptionWrapper(CAST_EXCEPTION_MESSAGE, fun(): BaseLiveEvent<T> {
                return eventsMap[tag] as StickyLiveEvent<T>
            })
        } else {
            val liveEvent = StickyLiveEvent<T>()
            eventsMap[tag] = liveEvent
            liveEvent
        }
    }

    /**
     * Returns the `StickySingleLiveEvent` object, creates one if necessary
     * @return the `StickySingleLiveEvent` object specified by the @param tag
     */
    fun <T> getStickySingleLiveEvent(tag: String, type: Class<T>): BaseLiveEvent<T> {
        return if (eventsMap.containsKey(tag)) {
            exceptionWrapper(CAST_EXCEPTION_MESSAGE, fun(): BaseLiveEvent<T> {
                return eventsMap[tag] as StickySingleLiveEvent<T>
            })
        } else {
            val liveEvent = StickySingleLiveEvent<T>()
            eventsMap[tag] = liveEvent
            liveEvent
        }
    }
}