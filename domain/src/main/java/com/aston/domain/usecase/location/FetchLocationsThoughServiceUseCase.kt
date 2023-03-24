package com.aston.domain.usecase.location

import com.aston.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchLocationsThoughServiceUseCase @Inject constructor(
    private val repository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(locationName: String, locationType: String, locationDimension: String) =
        repository.fetchLocationsThoughService(locationName, locationType, locationDimension)
            .flowOn(ioDispatcher)

}