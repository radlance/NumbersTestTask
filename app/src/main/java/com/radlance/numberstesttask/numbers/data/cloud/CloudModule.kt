package com.radlance.numberstesttask.numbers.data.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface CloudModule {

    fun <T : Any> service(clazz: Class<T>): T

    abstract class Abstract : CloudModule {

        protected abstract fun level(): HttpLoggingInterceptor.Level

        protected open fun baseUrl(): String = "http://numbersapi.com/"

        override fun <T : Any> service(clazz: Class<T>): T {
            val interceptor = HttpLoggingInterceptor().apply { level = level() }

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            return retrofit.create(clazz)
        }
    }

    class Debug : Abstract() {
        override fun level(): HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
    }


    class Release : Abstract() {
        override fun level(): HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
    }
}