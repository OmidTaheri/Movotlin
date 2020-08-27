package ir.omidtaheri.domain.interactor.base

import io.reactivex.Observable

abstract class ObservableUseCase<in Params, Result>(val schedulers: Schedulers) {

    abstract fun buildSingle(params: Params): Observable<Result>

    fun execute(params: Params): Observable<Result> {

        return buildSingle(params)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
    }
}
