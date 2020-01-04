package com.example.a22housexam2.Networking.RetrofitCreator

import com.example.a22housexam2.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCreator{
    companion object{
        val API_BASE_URL = "https://api.github.com/"

        private fun defaultRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOKHttpClient())
                .build()
        }

        fun <T> create(service : Class<T>) : T {
            return defaultRetrofit().create(service)
        }

        private fun createOKHttpClient() : OkHttpClient{
            val interceptor = HttpLoggingInterceptor()
            if(BuildConfig.DEBUG){
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            else {
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }

            return OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()
        }

    }
}