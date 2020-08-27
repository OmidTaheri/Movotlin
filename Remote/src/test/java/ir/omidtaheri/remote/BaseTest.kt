package ir.omidtaheri.remote

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class BaseTest {

    lateinit var retrofit: Retrofit
    lateinit var mockWebServer: MockWebServer
    lateinit var okHttpClient: OkHttpClient

    @Before
    open fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        okHttpClient = buildOkHttpClient()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @After
    open fun shutDown() {
        mockWebServer.shutdown()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }
}
