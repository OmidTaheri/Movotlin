package ir.omidtaheri.domain.interactor.base

import io.reactivex.Single

abstract class SingleUseCase<in Params, Result>(val schedulers: Schedulers) {

    abstract fun buildSingle(params: Params): Single<Result>

    fun execute(params: Params): Single<Result> {

        return buildSingle(params)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
    }
}
