package com.example.video4kids.common.backend.intercepter

import com.example.video4kids.common.Constants
import com.example.video4kids.common.extensions.log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        var response: Response
        var retry = 0

        do {

            response = chain.proceed(request)

            if (response.isSuccessful || response.isRedirect) break

            val code = response.code()

            // auth error
            if (code == 401) break

            log("Retrying... $code ${response.message()}")

            Thread.sleep(Constants.RETRY_DELAY)
            retry++

        } while (retry < Constants.MAX_RETRY_COUNT)

        return response
    }
}