package ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetGenreList
import ir.omidtaheri.genrelist.entity.GenreUiEntity
import ir.omidtaheri.genrelist.mapper.GenreEntityUiDomainMapper

class GenreViewModel(
    val getGenreList: GetGenreList,
    val genreEntityUiDomainMapper: GenreEntityUiDomainMapper,
    application: Application
) :
    BaseAndroidViewModel(application) {

    private val _dataLive: MutableLiveData<List<GenreUiEntity>>
    val dataLive: LiveData<List<GenreUiEntity>>
        get() = _dataLive

    private val _genreErrorState: MutableLiveData<Boolean>
    val genreErrorState: LiveData<Boolean>
        get() = _genreErrorState

    init {
        _dataLive = MutableLiveData()
        _genreErrorState = MutableLiveData()
    }

    fun getMovieListByGenre() {
        //  _isLoading.value = true
        val disposable = getGenreList.execute(Unit).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                    // _isLoading.value = false
                    _dataLive.value = response.data?.map {
                        genreEntityUiDomainMapper.mapToUiEntity(it)
                    }
                }

                is DataState.ERROR -> {
                    // _isLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                handleSnackBarError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.TOAST -> {
                                handleToastError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.DIALOG -> {
                                _genreErrorState.value = true
                            }
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }

    private fun handleSnackBarError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _ErrorSnackBar.value =
                    messageHolder.message
                is MessageHolder.Res -> _ErrorSnackBar.value =
                    ApplicationClass.getString(
                        messageHolder.resId
                    )
            }
        }
    }

    private fun handleToastError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _ErrorToast.value =
                    messageHolder.message
                is MessageHolder.Res -> _ErrorToast.value =
                    ApplicationClass.getString(
                        messageHolder.resId
                    )
            }
        }
    }
}
