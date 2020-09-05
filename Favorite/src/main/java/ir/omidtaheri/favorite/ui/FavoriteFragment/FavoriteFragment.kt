package ir.omidtaheri.favorite.ui.FavoriteFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.favorite.databinding.FavoriteFragmentBinding
import ir.omidtaheri.favorite.di.components.DaggerFavoriteComponent
import ir.omidtaheri.favorite.ui.FavoriteFragment.adapters.FavoritedMovieAdapter
import ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel.FavoriteViewModel
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class FavoriteFragment : BaseFragment(), FavoritedMovieAdapter.Callback {

    private lateinit var recyclerAdapter: FavoritedMovieAdapter
    private lateinit var viewModel: FavoriteViewModel

    private var _viewbinding: FavoriteFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var multiStatePage: MultiStatePage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        fetchData()
    }

    private fun initRecyclerViews() {
        multiStatePage.apply {
            recyclerAdapter = FavoritedMovieAdapter()
            recyclerAdapter.setCallback(this@FavoriteFragment)
            configRecyclerView(
                recyclerAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                GridLayoutManager(context, 2)
            )
            toLoadingState()
        }
    }

    private fun fetchData() {
        viewModel.getFavoritedMovieList()
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
            recyclerAdapter.addItems(it)
            multiStatePage.toDateState()
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
        Snackbar.make(viewbinding.root, message, Snackbar.LENGTH_LONG).show()
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

    override fun onItemClick(movieId: Int) {
        TODO("Not yet implemented")
    }
}
