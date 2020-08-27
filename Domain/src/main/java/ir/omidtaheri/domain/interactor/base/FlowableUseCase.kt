package ir.omidtaheri.domain.interactor.base

import io.reactivex.Flowable

abstract class FlowableUseCase<in Params, Result>(val schedulers: Schedulers) {

    abstract fun buildSingle(params: Params): Flowable<Result>

    fun execute(params: Params): Flowable<Result> {

        return buildSingle(params)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
    }
}
