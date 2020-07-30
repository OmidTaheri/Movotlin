package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.kotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.androidbase.singleLiveData.SingleLiveData
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.interactor.GetPopularMovies
import ir.omidtaheri.domain.interactor.GetTopRatedMovies
import ir.omidtaheri.domain.interactor.GetUpcomingMovies
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.entity.MultiMovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MultiMovieEntityUiDomainMapper

class MainViewModel(
    val GetPopularMoviesUseCase: GetPopularMovies,
    val GetTopRatedMoviesUseCase: GetTopRatedMovies,
    val GetUpcomingMoviesUseCase: GetUpcomingMovies,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {


    private val _PoularLiveData: MutableLiveData<MultiMovieUiEntity>
    val PoularLiveData: LiveData<MultiMovieUiEntity>
        get() = _PoularLiveData

    private val _isPoularLoading: MutableLiveData<Boolean>
    val isPoularLoading: LiveData<Boolean>
        get() = _isPoularLoading

    private val _PoularErrorState: MutableLiveData<Boolean>
    val PoularErrorState: LiveData<Boolean>
        get() = _PoularErrorState


    private val _TopRateLiveData: MutableLiveData<MultiMovieUiEntity>
    val TopRateLiveData: LiveData<MultiMovieUiEntity>
        get() = _TopRateLiveData

    private val _isTopRateLoading: MutableLiveData<Boolean>
    val isTopRateLoading: LiveData<Boolean>
        get() = _isTopRateLoading

    private val _TopRateErrorState: MutableLiveData<Boolean>
    val TopRateErrorState: LiveData<Boolean>
        get() = _TopRateErrorState


    private val _UpComingLiveData: MutableLiveData<MultiMovieUiEntity>
    val UpComingLiveData: LiveData<MultiMovieUiEntity>
        get() = _UpComingLiveData


    private val _isUpComingLoading: MutableLiveData<Boolean>
    val isUpComingLoading: LiveData<Boolean>
        get() = _isUpComingLoading

    private val _UpComingErrorState: MutableLiveData<Boolean>
    val UpComingErrorState: LiveData<Boolean>
        get() = _UpComingErrorState


    init {
        _PoularLiveData = MutableLiveData()
        _TopRateLiveData = MutableLiveData()
        _UpComingLiveData = MutableLiveData()
        _isPoularLoading = MutableLiveData()
        _isTopRateLoading = MutableLiveData()
        _isUpComingLoading = MutableLiveData()
        _PoularErrorState = MutableLiveData()
        _TopRateErrorState = MutableLiveData()
        _UpComingErrorState = MutableLiveData()
    }


    fun getPopularMovieList(page: Int) {
       // _isPoularLoading.value = true
        val disposable = GetPopularMoviesUseCase.execute(page).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                   // _isPoularLoading.value = false
                    _PoularLiveData.value =
                        multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
                }


                is DataState.ERROR -> {
                    //_isPoularLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                HandleSnackBarError(errorDataState)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState)
                            }

                            is UiComponentType.DIALOG -> {
                                _PoularErrorState.value = true
                            }

                        }


                    }


                }

            }
        }

        addDisposable(disposable)
    }


    fun getTopRatedMovieList(page: Int) {
       // _isTopRateLoading.value = true
        val disposable = GetTopRatedMoviesUseCase.execute(page).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                  //  _isTopRateLoading.value = false
                    _TopRateLiveData.value = multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
                }


                is DataState.ERROR -> {
                   // _isTopRateLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                HandleSnackBarError(errorDataState)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState)
                            }

                            is UiComponentType.DIALOG -> {
                                _TopRateErrorState.value = true
                            }

                        }


                    }


                }

            }
        }

        addDisposable(disposable)
    }


    fun getUpComingMovieList(page: Int) {
        //_isUpComingLoading.value = true
        val disposable = GetUpcomingMoviesUseCase.execute(page).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                   // _isUpComingLoading.value = false
                    _UpComingLiveData.value = multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
                }


                is DataState.ERROR -> {
                  //  _isUpComingLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                HandleSnackBarError(errorDataState)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState)
                            }


                            is UiComponentType.DIALOG -> {
                                _UpComingErrorState.value = true
                            }
                        }


                    }


                }

            }
        }

        addDisposable(disposable)
    }


    private fun HandleSnackBarError(errorDataState: DataState.ERROR<MultiMovieDomainEntity>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _ErrorSnackBar.value =
                    messageHolder.Message
                is MessageHolder.Res -> _ErrorSnackBar.value =
                    ApplicationClass.getString(
                        messageHolder.ResId
                    )


            }

        }
    }

    private fun HandleToastError(errorDataState: DataState.ERROR<MultiMovieDomainEntity>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _ErrorToast.value =
                    messageHolder.Message
                is MessageHolder.Res -> _ErrorToast.value =
                    ApplicationClass.getString(
                        messageHolder.ResId
                    )


            }

        }
    }
}