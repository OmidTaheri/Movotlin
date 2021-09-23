package ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetGenreList
import ir.omidtaheri.genrelist.entity.GenreUiEntity
import ir.omidtaheri.genrelist.mapper.GenreEntityUiDomainMapper

class GenreViewModel(
    private val getGenreList: GetGenreList,
    private val genreEntityUiDomainMapper: GenreEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    private var recyclerViewState: LinearLayoutManager.SavedState? = null


    private val _dataLive: MutableLiveData<List<GenreUiEntity>> = MutableLiveData()
    val dataLive: LiveData<List<GenreUiEntity>>
        get() = _dataLive

    private val _genreErrorState: MutableLiveData<Boolean> = MutableLiveData()
    val genreErrorState: LiveData<Boolean>
        get() = _genreErrorState

    fun getMovieListByGenre() {
        val disposable = getGenreList.execute(Unit).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                    _dataLive.value = response.data?.map {
                        genreEntityUiDomainMapper.mapToUiEntity(it)
                    }
                }

                is DataState.ERROR -> {
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
                is MessageHolder.MESSAGE -> _errorSnackBar.value =
                    messageHolder.message
                is MessageHolder.Res -> _errorSnackBar.value =
                    mApplication.applicationContext.getString(
                        messageHolder.resId
                    )
            }
        }
    }

    private fun handleToastError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _errorToast.value =
                    messageHolder.message
                is MessageHolder.Res -> _errorToast.value =
                    mApplication.applicationContext.getString(
                        messageHolder.resId
                    )
            }
        }
    }

    fun saveFragmentState(
        layoutManagerState: LinearLayoutManager.SavedState?
    ) {
        recyclerViewState = layoutManagerState
    }

    fun restoreStateOfRecyclerView(): LinearLayoutManager.SavedState? {
        return recyclerViewState
    }

}
