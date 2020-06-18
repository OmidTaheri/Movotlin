package ir.omidtaheri.domain.interactor.base

import io.reactivex.rxjava3.core.Scheduler

interface Schedulers {

    val subscribeOn :Scheduler
    val observeOn :Scheduler
}
