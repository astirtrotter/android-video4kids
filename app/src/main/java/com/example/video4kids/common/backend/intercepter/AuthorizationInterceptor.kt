package com.example.video4kids.common.backend.intercepter

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthorizationInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//
//        val isLogin = request.method() == "POST" && request.url().uri().path.contains("/auth/")
//        if (!isLogin) {
//            val newRequestBuilder = request.newBuilder().method(request.method(), request.body())
//            newRequestBuilder.header(AUTH_HEADER_KEY, AUTH_HEADER_VALUE + Pref.token)
//            return chain.proceed(newRequestBuilder.build())     // TODO: crash on uploader
//        }

        return chain.proceed(chain.request())
    }

//    companion object {
//        val AUTH_HEADER_KEY = "Authorization"
//        val AUTH_HEADER_VALUE = "Bearer "
//    }
}