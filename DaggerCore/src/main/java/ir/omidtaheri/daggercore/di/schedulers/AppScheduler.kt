package ir.omidtaheri.daggercore.di.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import ir.omidtaheri.domain.interactor.base.Schedulers
import javax.inject.Inject

class AppScheduler @Inject constructor() : Schedulers {
    override val subscribeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.io()
    override val observeOn: Scheduler
        get() = AndroidSchedulers.mainThread()
}
