package com.example.rotinaapp.core.domain._util

import com.example.rotinaapp.core.domain.model.DataError

typealias RootError = Error
sealed class Result<out D, out E : RootError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>()
    data class Error<out E : RootError>(val error: E, val errorMessage: String? = null) : Result<Nothing, E>()

    inline fun fold(
        onError: (E, String?) -> Unit,
        onSuccess: (D) -> Unit
    ) {
        when (this) {
            is Error -> onError(error, errorMessage)
            is Success -> onSuccess(data)
        }
    }
}

fun <D, E : RootError> Result<D, E>.isSuccess(): Boolean = this is Result.Success

fun <D, E : RootError> Result<D, E>.isError(): Boolean = this is Result.Error

fun Result<*, DataError.Network>.asEmptyDataResult(): EmptyDataResult =
    when (this) {
        is Result.Error -> Result.Error(error as DataError.Network, errorMessage)
        is Result.Success -> Result.Success(data = Unit)
    }

inline fun <D, E : RootError, M> Result<D, E>.map(map: (D) -> M): Result<M, E> {
    return when (this) {
        is Result.Error -> Result.Error(error, errorMessage)
        is Result.Success -> Result.Success(data = map(data))
    }
}

fun <D> Result<D, *>.getOrNull(): D? = (this as? Result.Success)?.data

typealias EmptyDataResult = Result<Unit, DataError.Network>