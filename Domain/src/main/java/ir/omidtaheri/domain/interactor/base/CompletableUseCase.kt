package ir.omidtaheri.domain.interactor.base

import io.reactivex.rxjava3.core.Completable


abstract class CompletableUseCase<in Params>(val schedulers: Schedulers) {


    abstract fun buildCompletable(params: Params?): Completable

    fun execute(params: Params?): Completable {

        return buildCompletable(params)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)


    }


}