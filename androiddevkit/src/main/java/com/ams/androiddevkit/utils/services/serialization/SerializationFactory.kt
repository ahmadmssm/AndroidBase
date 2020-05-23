package com.ams.androiddevkit.utils.services.serialization

/**
 * Singleton class that will provide only one instance of serialization service. Very useful inside
 * custom adapters we create like Room adapters. As this will help us to use a single
 */
object SerializationFactory {
    @JvmStatic
    val serializationService: SerializationService by lazy { GsonSerializationService() }
}