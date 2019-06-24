package com.example.video4kids.common.backend

import com.example.video4kids.common.Constants
import com.example.video4kids.common.backend.api.API
import com.example.video4kids.common.backend.api.VideoItem
import com.example.video4kids.common.backend.intercepter.AuthorizationInterceptor
import com.example.video4kids.common.backend.intercepter.RetryInterceptor
import com.example.video4kids.common.extensions.log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object BackendManager {
    private val api: API

    init {
        val gsonDateTimeFormat = GsonBuilder()
            .setDateFormat(Constants.DATE_TIME_FORMAT)
            .create()

        val client = OkHttpClient.Builder()
            .connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(RetryInterceptor())
            .build()

        api = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonDateTimeFormat))
            .client(client)
            .build()
            .create(API::class.java)
    }

    private fun <T> respond(subscriber: Subscriber<in T?>, response: Response<T>, apiName: String) {
        if (response.isSuccessful) {
            log("${apiName}-end: success")
            subscriber.onNext(response.body()!!)
        } else {
            val errMsg = response.message()
            log("${apiName}-end: failure - " + errMsg)
            subscriber.onError(Throwable(errMsg))
        }

        subscriber.onCompleted()
    }

    fun getSearchVideo(name: String, userkey: String): Observable<List<VideoItem>> {

        val observable: Observable<List<VideoItem>> = Observable.create { subscriber ->
            respond<List<VideoItem>>(
                subscriber,
                api.getSearchVideo(name, userkey).execute(),
                "getSearchVideo")
        }

        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun getVideos(path: String, kind: String): Observable<List<VideoItem>> {

        val observable: Observable<List<VideoItem>> = Observable.create { subscriber ->
            respond<List<VideoItem>>(
                subscriber,
                api.getVideos(path, kind).execute(),
                "getVideos")
        }

        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun getVideosMore(path: String, kind: String): Observable<List<VideoItem>> {

        val observable: Observable<List<VideoItem>> = Observable.create { subscriber ->
            respond<List<VideoItem>>(
                subscriber,
                api.getVideosMore(path, kind).execute(),
                "getVideosMore")
        }

        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}