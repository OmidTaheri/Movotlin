package ir.omidtaheri.domain.interactor.base

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

abstract class ObservableUseCase<in Params, Result>(val schedulers: Schedulers) {

    abstract fun buildSingle(params: Params): Observable<Result>

    fun execute(params: Params): Observable<Result> {

        return buildSingle(params)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)


    }
}