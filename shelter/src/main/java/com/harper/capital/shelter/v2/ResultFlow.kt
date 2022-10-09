package com.harper.capital.shelter.v2

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

fun <T : Any> Flow<T>.wrapAsResult(): Flow<Result<T>> = flow {
    catch { emit(Result.Error(null, it)) }
        .collect { emit(Result.Success(it)) }
}

fun <T : Any> Flow<Result<T>>.withLoading(onNext: (suspend (T?) -> Unit)? = null, onLoading: (Boolean) -> Unit) = flow {
    collect { result ->
        val isLoading = result is Result.Loading<T>
        onNext?.invoke(result.data)
        onLoading.invoke(isLoading)
        if (!isLoading) {
            emit(result)
        }
    }
}

fun <T : Any> Flow<Result<T>>.onError(
    onNext: (suspend (T?) -> Unit)? = null,
    onError: (Throwable) -> Unit
) = flow {
    collect { result ->
        onNext?.invoke(result.data)
        if (result is Result.Error<T>) {
            onError.invoke(result.error)
        } else {
            emit(result)
        }
    }
}

suspend fun <T : Any> Flow<Result<T>>.collect(onNext: suspend (T?) -> Unit) = collect { result ->
    if (result is Result.Success<T>) {
        onNext.invoke(result.data)
    }
}

sealed class Result<T>(val data: T?) {

    class Success<T>(data: T?) : Result<T>(data)

    class Loading<T>(data: T?) : Result<T>(data)

    class Error<T>(data: T?, val error: Throwable) : Result<T>(data)
}
