package com.radlance.numberstesttask.numbers.sl

import com.radlance.numberstesttask.main.sl.Core
import com.radlance.numberstesttask.main.sl.Module
import com.radlance.numberstesttask.numbers.data.BaseNumbersRepository
import com.radlance.numberstesttask.numbers.data.HandleDataRequest
import com.radlance.numberstesttask.numbers.data.HandleDomainError
import com.radlance.numberstesttask.numbers.data.NumberDataToDomain
import com.radlance.numberstesttask.numbers.data.cache.NumberDataToCache
import com.radlance.numberstesttask.numbers.data.cache.NumbersCacheDataSource
import com.radlance.numberstesttask.numbers.data.cloud.NumbersCloudDataSource
import com.radlance.numberstesttask.numbers.data.cloud.NumbersService
import com.radlance.numberstesttask.numbers.domain.HandleError
import com.radlance.numberstesttask.numbers.domain.HandleRequest
import com.radlance.numberstesttask.numbers.domain.NumbersInteractor
import com.radlance.numberstesttask.numbers.domain.NumbersRepository
import com.radlance.numberstesttask.numbers.presentation.DetailsUi
import com.radlance.numberstesttask.numbers.presentation.HandleNumbersRequest
import com.radlance.numberstesttask.numbers.presentation.NumberUiMapper
import com.radlance.numberstesttask.numbers.presentation.NumbersCommunications
import com.radlance.numberstesttask.numbers.presentation.NumbersListCommunications
import com.radlance.numberstesttask.numbers.presentation.NumbersResultMapper
import com.radlance.numberstesttask.numbers.presentation.NumbersStateCommunications
import com.radlance.numberstesttask.numbers.presentation.NumbersViewModel
import com.radlance.numberstesttask.numbers.presentation.ProgressCommunication

class NumbersModule(
    private val core: Core,
    private val provideNumbersRepository: ProvideNumbersRepository
) : Module<NumbersViewModel.Base> {

    override fun viewModel(): NumbersViewModel.Base {
        val communications = NumbersCommunications.Base(
            progress = ProgressCommunication.Base(),
            numbersState = NumbersStateCommunications.Base(),
            numbersList = NumbersListCommunications.Base()
        )


        return NumbersViewModel.Base(
            handleResult = HandleNumbersRequest.Base(
                dispatchers = core.provideDispatchers(),
                communications = communications,
                mapper = NumbersResultMapper(
                    communications = communications,
                    numberUiMapper = NumberUiMapper()
                )
            ),
            manageResources = core,
            communications = communications,
            interactor = NumbersInteractor.Base(
                repository = provideNumbersRepository.provideNumbersRepository(),
                handleRequest = HandleRequest.Base(
                    handleError = HandleError.Base(core),
                    repository = provideNumbersRepository.provideNumbersRepository()
                ),
                numberFactDetails = core.provideNumberDetails()
            ),
            navigationCommunication = core.provideNavigation(),
            detailMapper = DetailsUi
        )
    }
}

interface ProvideNumbersRepository {
    fun provideNumbersRepository() : NumbersRepository

    class Base(private val core: Core) : ProvideNumbersRepository {
        override fun provideNumbersRepository(): NumbersRepository {
            val cacheDataSource = NumbersCacheDataSource.Base(
                dao = core.provideDatabase().numbersDao(),
                dataToCache = NumberDataToCache()
            )
            val mapperToDomain = NumberDataToDomain()
            return BaseNumbersRepository(
                NumbersCloudDataSource.Base(
                    service = core.service(NumbersService::class.java),
                    randomApiHeader = core.provideRandomApiHeader()
                ),
                cacheDataSource = cacheDataSource,
                handleDataRequest = HandleDataRequest.Base(
                    cacheDataSource = cacheDataSource,
                    handleError = HandleDomainError(),
                    mapperToDomain = mapperToDomain
                ),
                mapperToDomain = mapperToDomain
            )
        }

    }
}