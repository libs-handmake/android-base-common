package common.hoangdz.lib.datasouce

/**
 * Created by HoangDepTrai on 22, November, 2022 at 3:22 PM
 */
open class LocalDataSource : DataSource {

    override suspend fun <T> launchData(call: suspend () -> T): Source<T> {
        return try {
            val res = call.invoke()
            res?.let { Source.Success(res) }
                ?: Source.Failure(errorType = Source.ErrorType.OTHER)
        } catch (e: Throwable) {
            Source.Failure(errorType = Source.ErrorType.OTHER)
        }
    }

    class LocalDataSourceException : Exception("Error while handling data :)")

}