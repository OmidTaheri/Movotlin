package ir.omidtaheri.domain.interactor.base

import io.reactivex.Scheduler

interface Schedulers {

    val subscribeOn: Scheduler
    val observeOn: Scheduler
}
