package com.harper.capital.shelter.v2.domain

import com.harper.capital.shelter.v2.data.ExampleRepository
import kotlinx.coroutines.flow.Flow

internal class ExampleInteractor(private val repository: ExampleRepository) {

    fun getDataList(): Flow<List<Any>> = repository.getDataList()

    suspend fun fetchDataList() = repository.fetchDataList()
}
