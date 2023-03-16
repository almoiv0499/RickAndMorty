package com.aston.data.util.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

inline fun <DatabaseType, RemoteType> networkBoundResource(
    crossinline query: () -> Flow<DatabaseType>,
    crossinline fetch: suspend () -> RemoteType,
    crossinline saveFetchResult: suspend (RemoteType) -> Unit,
    crossinline shouldFetch: (DatabaseType) -> Boolean = { true },
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(data)
        try {
            saveFetchResult(fetch())
            query()
        } catch (throwable: Throwable) {
            query()
        }
    } else {
        query()
    }
    emitAll(flow)
}