package com.aston.domain.usecase.location

import com.aston.domain.repository.LocationRepository
import javax.inject.Inject

class FetchLocationsThoughServiceUseCase @Inject constructor(
    private val repository: LocationRepository,
) {

    operator fun invoke(locationName: String, locationType: String, locationDimension: String) =
        repository.fetchLocationsThoughService(locationName, locationType, locationDimension)

}