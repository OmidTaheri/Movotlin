package ir.omidtaheri.daggercore.di.schedulers

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import ir.omidtaheri.domain.interactor.base.Schedulers
import javax.inject.Inject

class AppScheduler @Inject constructor() : Schedulers {
    override val subscribeOn: Scheduler
        get() = io.reactivex.rxjava3.schedulers.Schedulers.io()
    override val observeOn: Scheduler
        get() = AndroidSchedulers.mainThread()
}