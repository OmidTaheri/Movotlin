package ir.omidtaheri.domain.interactor.base

abstract class SuspendUseCase<in Params, Result>() {

    abstract suspend fun buildSingle(params: Params): Result

    suspend fun execute(params: Params): Result {

        return buildSingle(params)
    }
}
