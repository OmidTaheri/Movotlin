package ir.omidtaheri.favorite.ui.FavoriteFragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.androidbase.viewmodelutils.GenericSavedStateViewModelFactory
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.favorite.R
import ir.omidtaheri.favorite.databinding.FavoriteFragmentBinding
import ir.omidtaheri.favorite.di.components.DaggerFavoriteComponent
import ir.omidtaheri.favorite.ui.FavoriteFragment.adapters.FavoritedMovieAdapter
import ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel.FavoriteViewModel
import ir.omidtaheri.uibase.*
import ir.omidtaheri.viewcomponents.SwipeRefreshMultiState.SwipeRefreshMultiStatePage

class FavoriteFragment : BaseFragment<FavoriteViewModel>(), FavoritedMovieAdapter.Callback {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerAdapter: FavoritedMovieAdapter
    private var viewBinding: FavoriteFragmentBinding? = null
    private lateinit var swipeRefreshmultiStatePage: SwipeRefreshMultiStatePage
    private var stateFavoritedRecyclerView: Parcelable? = null
    private var isEnableAnimation = true

    private val viewModel: FavoriteViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            isEnableAnimation = false
            stateFavoritedRecyclerView =
                savedInstanceState.getParcelable<LinearLayoutManager.SavedState?>("recyclerState")
        } else {
            viewModel.restoreStateOfRecyclerView()?.let {
                stateFavoritedRecyclerView = it
                isEnableAnimation = false
            }
        }

        initRecyclerViews()
        fetchData()
    }

    private fun initRecyclerViews() {
        swipeRefreshmultiStatePage.apply {
            recyclerAdapter = FavoritedMovieAdapter(requireContext())
            recyclerAdapter.setCallback(this@FavoriteFragment)
            configRecyclerView(
                recyclerAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                GridLayoutManager(context, 2)
            )
            getSwipeRefresh().setOnRefreshListener {
                fetchData()
            }
            if (isEnableAnimation)
                setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
            toLoadingState()
        }
    }

    private fun fetchData() {
        viewModel.getFavoritedMovieList()
    }

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        viewBinding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    override fun bindUiComponent() {
        swipeRefreshmultiStatePage = viewBinding!!.swipeRefreshMultiStatePage
        toolbar = viewBinding!!.mainToolbar

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.change_theme -> {
                    switchThemeMode(requireContext())
                    true
                }
                else -> false
            }
        }

    }

    override fun configDaggerComponent() {
        DaggerFavoriteComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }


    override fun setLiveDataObserver() {

        viewModel.dataLive.observe(this, Observer {
            recyclerAdapter.clear()

            if (it != null && it.isNotEmpty()) {

                recyclerAdapter.addItems(it)

                swipeRefreshmultiStatePage.apply {
                    configRecyclerView(
                        recyclerAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                        GridLayoutManager(context, 2)
                    )

                    if (isEnableAnimation)
                        setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
                }

                stateFavoritedRecyclerView?.let {
                    swipeRefreshmultiStatePage.getRecyclerView().layoutManager?.onRestoreInstanceState(
                        it
                    )
                    stateFavoritedRecyclerView = null
                }
                swipeRefreshmultiStatePage.toDateState()
            } else {

                swipeRefreshmultiStatePage.apply {
                    configRecyclerView(
                        recyclerAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                        GridLayoutManager(context, 1)
                    )
                }
                swipeRefreshmultiStatePage.toDateState()
            }


        })

        viewModel.favoriteErrorState.observe(this, Observer {
            swipeRefreshmultiStatePage.toErrorState(View.OnClickListener {
                viewModel.getFavoritedMovieList()
            })
        })
    }

    override fun setSnackBarMessageLiveDataObserver() {
        viewModel.messageSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastMessageLiveDataObserver() {
        viewModel.messageToast.observe(this, Observer {
            showToast(it)
        })
    }

    override fun setSnackBarErrorLivaDataObserver() {
        viewModel.errorSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastErrorLiveDataObserver() {
        viewModel.errorToast.observe(this, Observer {
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
        Snackbar.make(viewBinding!!.root, message, BaseTransientBottomBar.LENGTH_LONG).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val recyclerState =
            swipeRefreshmultiStatePage.getRecyclerView().layoutManager?.onSaveInstanceState()

        outState.putParcelable("recyclerState", recyclerState)

    }

    override fun onDestroyView() {

        val recyclerState =
            swipeRefreshmultiStatePage.getRecyclerView().layoutManager?.onSaveInstanceState()

        viewModel.saveFragmentState(
            recyclerState as LinearLayoutManager.SavedState?
        )
        onDestroyGlide()
        super.onDestroyView()
        viewBinding = null
    }


    override fun onItemClick(movieId: Int) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

}
