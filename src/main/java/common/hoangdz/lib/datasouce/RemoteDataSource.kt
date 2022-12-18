package common.hoangdz.lib.datasouce

import common.hoangdz.lib.R
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

/**
 * Created by Hoangkute123xyz on 08/12/2022 at 1:42 SA.
 */
abstract class RemoteDataSource<S> : DataSource {

    abstract fun getBaseUrl(): String

    val service by lazy { createRetrofitService() }

    protected val defaultRemoteDataException by lazy { DataRemoteException() }

    private fun createRetrofitService(): S {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<S>
        return Retrofit.Builder().baseUrl(getBaseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build()
            ).build()
            .create(type)
    }

    override suspend fun <T> launchData(call: suspend () -> T): Source<T> {
        return try {
            Source.Success(call.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    Source.Failure(
                        R.string.server_down,
                        errorType = Source.ErrorType.SERVER
                    )
                }

                is ConnectException, is UnknownHostException, is SocketTimeoutException -> {
                    Source.Failure(
                        R.string.check_your_internet,
                        errorType = Source.ErrorType.ERROR_NET_WORK
                    )
                }

                else -> {
                    Source.Failure(
                        R.string.error_occurred,
                        errorType = Source.ErrorType.OTHER
                    )
                }
            }
        }
    }

    class DataRemoteException : Exception("Error when handling data")
}