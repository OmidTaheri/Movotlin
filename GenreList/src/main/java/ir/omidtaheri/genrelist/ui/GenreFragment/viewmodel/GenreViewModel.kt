package ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy


import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.interactor.GetGenreList
import ir.omidtaheri.domain.interactor.GetMovieListByGenreId
import ir.omidtaheri.genrelist.entity.GenreUiEntity
import ir.omidtaheri.genrelist.entity.MultiMovieUiEntity
import ir.omidtaheri.genrelist.mapper.GenreEntityUiDomainMapper

import ir.omidtaheri.genrelist.mapper.MultiMovieEntityUiDomainMapper

class GenreViewModel(
    val getGenreList: GetGenreList,
    val genreEntityUiDomainMapper: GenreEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {


    private val _DataLive: MutableLiveData<List<GenreUiEntity>>
    val DataLive: LiveData<List<GenreUiEntity>>
        get() = _DataLive

    private val _GenreErrorState: MutableLiveData<Boolean>
    val GenreErrorState: LiveData<Boolean>
        get() = _GenreErrorState


    init {
        _DataLive = MutableLiveData()
        _GenreErrorState = MutableLiveData()
    }


    fun getMovieListByGenre() {
        //  _isLoading.value = true
        val disposable = getGenreList.execute(Unit).subscribeBy{ response ->
            when (response) {

                is DataState.SUCCESS -> {
                    //_isLoading.value = false
                    _DataLive.value = response.data?.map {
                        genreEntityUiDomainMapper.mapToUiEntity(it)
                    }

                }


                is DataState.ERROR -> {
                    // _isLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                HandleSnackBarError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.DIALOG -> {
                                _GenreErrorState.value = true
                            }
                        }


                    }


                }

            }
        }

        addDisposable(disposable)
    }


    private fun HandleSnackBarError(errorDataState: DataState.ERROR<Any>) {
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

    private fun HandleToastError(errorDataState: DataState.ERROR<Any>) {
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