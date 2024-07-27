package dev.seabat.android.helloarchitectureretrofit

import dev.seabat.android.helloarchitectureretrofit.domain.exception.ErrorType

object ErrorStringConverter {
    fun convertTo(errorType: ErrorType): String {
        return when (errorType) {
            ErrorType.CONNECTION_TIMEOUT,
            ErrorType.SOCKET_TIMEOUT,
            ErrorType.UNKNOWN_HOST,
            -> App.getApplicationContext()
                .getString(R.string.error_network_connection)

            ErrorType.NETWORK_IO_ERROR -> App.getApplicationContext()
                .getString(R.string.error_network_connecting)

            ErrorType.NETWORK_BAD_REQUEST -> App.getApplicationContext()
                .getString(R.string.error_network_bad_request)

            ErrorType.NETWORK_UNAUTHORIZED -> App.getApplicationContext()
                .getString(R.string.error_network_authorized)

            ErrorType.NETWORK_FORBIDDEN -> App.getApplicationContext()
                .getString(R.string.error_network_forbidden)

            ErrorType.NETWORK_NOT_FOUND -> App.getApplicationContext()
                .getString(R.string.error_network_not_found)

            ErrorType.NETWORK_NULL_RESPONSE_BODY -> App.getApplicationContext()
                .getString(R.string.error_network_null_response_body)

            ErrorType.NETWORK_UNKNOWN_ERROR -> App.getApplicationContext()
                .getString(R.string.error_network_unknown)

            ErrorType.UNKNOWN_ERROR -> App.getApplicationContext().getString(R.string.error_unknown)
        }
    }
}