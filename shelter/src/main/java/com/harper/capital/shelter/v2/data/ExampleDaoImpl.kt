package com.harper.capital.shelter.v2.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal interface ExampleDao {

    suspend fun insert(dataList: List<Any>)

    fun getDataList(): Flow<List<Any>>
}

internal class ExampleDaoImpl : ExampleDao {

    override suspend fun insert(dataList: List<Any>) {
        delay(1000L)
    }

    override fun getDataList(): Flow<List<Any>> {
        return flowOf(listOf(Any()))
    }
}
