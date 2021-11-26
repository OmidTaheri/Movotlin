package ir.omidtaheri.domain.interactor.base

import kotlinx.coroutines.flow.Flow

abstract class FlowPagingDataUseCase<in Params, Result>() {

    abstract fun buildSingle(params: Params): Flow<Result>

    fun execute(params: Params): Flow<Result> {

        return buildSingle(params)
    }
}
