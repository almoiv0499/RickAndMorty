package com.aston.domain.usecase.location

import com.aston.domain.repository.LocationRepository
import javax.inject.Inject

class FetchLocationsThoughDatabaseUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    operator fun invoke(locationName: String, locationType: String, locationDimension: String) =
        repository.fetchLocationsThoughDatabase(locationName, locationType, locationDimension)

}