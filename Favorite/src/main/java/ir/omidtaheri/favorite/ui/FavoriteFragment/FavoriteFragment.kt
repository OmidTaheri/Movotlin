package ir.omidtaheri.favorite.ui.FavoriteFragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.favorite.databinding.FavoriteFragmentBinding
import ir.omidtaheri.favorite.di.components.DaggerFavoriteComponent
import ir.omidtaheri.favorite.ui.FavoriteFragment.adapters.FavoritedMovieAdapter
import ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel.FavoriteViewModel
import ir.omidtaheri.uibase.loadRecyclerViewState
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.uibase.saveRecyclerViewStat
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class FavoriteFragment : BaseFragment(), FavoritedMovieAdapter.Callback {

    private lateinit var recyclerAdapter: FavoritedMovieAdapter
    private lateinit var viewModel: FavoriteViewModel

    private var _viewbinding: FavoriteFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var multiStatePage: MultiStatePage

    var STATE_FavoriteRecyclerview: Parcelable? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveSharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("FavoriteFragmentState", Context.MODE_PRIVATE)


        val FAVORIT_ViewSavedState =
            loadRecyclerViewState(saveSharedPreferences, "FAVORITE_RECYCLERVIEW_STATE")

        FAVORIT_ViewSavedState?.let {
            STATE_FavoriteRecyclerview = it
        }

        initRecyclerViews()
        fetchData()
    }

    private fun initRecyclerViews() {
        multiStatePage.apply {
            recyclerAdapter = FavoritedMovieAdapter(requireContext())
            recyclerAdapter.setCallback(this@FavoriteFragment)
            configRecyclerView(
                recyclerAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                GridLayoutManager(context, 2)
            )
            toLoadingState()
        }
    }

    private fun fetchData() {
        viewModel.getFavoritedMovieListByFlowable()
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = FavoriteFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        multiStatePage = _viewbinding!!.MultiStatePage
    }

    override fun ConfigDaggerComponent() {
        DaggerFavoriteComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
    }

    override fun setDataLiveObserver() {

        viewModel.dataLive.observe(this, Observer {
            if (it != null && it.size > 0) {
                recyclerAdapter.addItems(it)
                multiStatePage.toDateState()

                STATE_FavoriteRecyclerview?.let {
                    multiStatePage.getRecyclerView().layoutManager?.onRestoreInstanceState(
                        it
                    )
                    STATE_FavoriteRecyclerview = null
                }



            } else {
                multiStatePage.toEmptyState()
            }

        })

        viewModel.favoriteErrorState.observe(this, Observer {
            multiStatePage.toErrorState()
        })
    }

    override fun setSnackBarMessageLiveDataObserver() {
        viewModel.MessageSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastMessageLiveDataObserver() {
        viewModel.MessageToast.observe(this, Observer {
            showToast(it)
        })
    }

    override fun setSnackBarErrorLivaDataObserver() {
        viewModel.ErrorSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastErrorLiveDataObserver() {
        viewModel.ErrorToast.observe(this, Observer {
            showToast(it)
        })
    }

    override fun setLoadingLiveDataObserver() {
        viewModel.isLoading.observe(this, Observer {
            showLoading(it)
        })
    }

    override fun showLoading(show: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(viewbinding.root, message, BaseTransientBottomBar.LENGTH_LONG).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewbinding = null
    }

    override fun onDestroy() {
        super.onDestroy()

        val save: SharedPreferences =
            requireActivity().getSharedPreferences("FavoriteFragmentState", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()
        ed.clear().apply()

    }


    override fun onItemClick(movieId: Int) {
        val i = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("movotlin://detailmovie/" + movieId)
        )
        requireContext().startActivity(i)
    }
    override fun onStop() {
        super.onStop()

        val save: SharedPreferences =
            requireActivity().getSharedPreferences("FavoriteFragmentState", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()

        val RecyclerState =
            multiStatePage.getRecyclerView().layoutManager?.onSaveInstanceState()


        saveRecyclerViewStat(
            ed,
            "FAVORITE_RECYCLERVIEW_STATE",
            RecyclerState as LinearLayoutManager.SavedState
        )

        ed.commit()


        onDestroyGlide()
    }
}
