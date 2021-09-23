package ir.omidtaheri.mainpage.ui.MainFragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.androidbase.viewmodelutils.GenericSavedStateViewModelFactory
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.databinding.MainFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerMainComponent
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.GalleryViewAdapter
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModel
import ir.omidtaheri.uibase.*
import ir.omidtaheri.viewcomponents.GalleryViewer.GalleryViewer


class MainFragment : BaseFragment<MainViewModel>(), GalleryViewAdapter.Callback {

    private var viewBinding: MainFragmentBinding? = null
    private lateinit var galleryViewerTopRate: GalleryViewer
    private lateinit var galleryViewerPopular: GalleryViewer
    private lateinit var galleryViewerUpComing: GalleryViewer
    private lateinit var toolbar: MaterialToolbar
    private lateinit var adapterTopRate: GalleryViewAdapter
    private lateinit var adapterPopular: GalleryViewAdapter
    private lateinit var adapterUpComing: GalleryViewAdapter
    private var stateTopRatedRecyclerview: Parcelable? = null
    private var statePopularRecyclerview: Parcelable? = null
    private var stateUpcomingRecyclerview: Parcelable? = null
    private var isEnableAnimation = true

    private val viewModel: MainViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {

            isEnableAnimation = false
            stateTopRatedRecyclerview =
                savedInstanceState.getParcelable<LinearLayoutManager.SavedState?>("TopRated")
            statePopularRecyclerview =
                savedInstanceState.getParcelable<LinearLayoutManager.SavedState?>("Popular")
            stateUpcomingRecyclerview =
                savedInstanceState.getParcelable<LinearLayoutManager.SavedState?>("Upcoming")


        } else {

            val recyclerViewsState = viewModel.restoreStateOfRecyclerViews()

            if (recyclerViewsState.size > 0) {
                isEnableAnimation = false

                recyclerViewsState[0]?.let {
                    stateTopRatedRecyclerview = it
                }

                recyclerViewsState[1]?.let {
                    statePopularRecyclerview = it
                }

                recyclerViewsState[2]?.let {
                    stateUpcomingRecyclerview = it
                }
            }

        }

        initRecyclerViews()
        fetchData()
    }

    private fun initRecyclerViews() {

        galleryViewerTopRate.apply {

            adapterTopRate = GalleryViewAdapter(MovieUiEntityComparator, requireContext())
            adapterTopRate.apply {
                setCallback(this@MainFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            toLoadingState()
                        }

                        is LoadState.Error -> {
                            toErrorState(View.OnClickListener {
                                viewModel.getTopRatedMovieList()
                            })
                        }

                        is LoadState.NotLoading -> {
                            toDateState()
                        }
                    }
                }
            }
            configRecyclerView(
                adapterTopRate as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            )
            if (isEnableAnimation)
                setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
        }

        galleryViewerPopular.apply {
            adapterPopular = GalleryViewAdapter(MovieUiEntityComparator, requireContext())
            adapterPopular.apply {
                setCallback(this@MainFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            toLoadingState()
                        }

                        is LoadState.Error -> {
                            toErrorState(View.OnClickListener {
                                viewModel.getPopularMovieList()
                            })
                        }

                        is LoadState.NotLoading -> {
                            toDateState()
                        }
                    }
                }
            }
            configRecyclerView(
                adapterPopular as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            )
            if (isEnableAnimation)
                setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
        }

        galleryViewerUpComing.apply {
            adapterUpComing = GalleryViewAdapter(MovieUiEntityComparator, requireContext())
            adapterUpComing.apply {
                setCallback(this@MainFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            toLoadingState()
                        }

                        is LoadState.Error -> {
                            toErrorState(View.OnClickListener {
                                viewModel.getUpComingMovieList()
                            })
                        }

                        is LoadState.NotLoading -> {
                            toDateState()
                        }
                    }
                }
            }

            configRecyclerView(
                adapterUpComing as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            )
            if (isEnableAnimation)
                setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
        }
    }

    private fun fetchData() {
        viewModel.getPopularMovieList()
        viewModel.getTopRatedMovieList()
        viewModel.getUpComingMovieList()
    }

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        viewBinding = MainFragmentBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    override fun bindUiComponent() {
        galleryViewerTopRate = viewBinding!!.GalleryViewer1
        galleryViewerPopular = viewBinding!!.GalleryViewer2
        galleryViewerUpComing = viewBinding!!.GalleryViewer3
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
        DaggerMainComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }


    override fun setLiveDataObserver() {

        viewModel.poularLiveData.observe(this, Observer {
            adapterPopular.submitData(lifecycle, it)

            statePopularRecyclerview?.let {
                galleryViewerPopular.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                statePopularRecyclerview = null
            }

        })

        viewModel.topRateLiveData.observe(this, Observer {
            adapterTopRate.submitData(lifecycle, it)

            stateTopRatedRecyclerview?.let {

                galleryViewerTopRate.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                stateTopRatedRecyclerview = null
            }

        })

        viewModel.upComingLiveData.observe(this, Observer {
            adapterUpComing.submitData(lifecycle, it)

            stateUpcomingRecyclerview?.let {
                galleryViewerUpComing.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                stateUpcomingRecyclerview = null
            }
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

        val topRatedRecyclerState =
            galleryViewerTopRate.getRecyclerView().layoutManager?.onSaveInstanceState()

        val popularRecyclerState =
            galleryViewerPopular.getRecyclerView().layoutManager?.onSaveInstanceState()


        val upcomingRecyclerState =
            galleryViewerUpComing.getRecyclerView().layoutManager?.onSaveInstanceState()

        outState.putParcelable("TopRated", topRatedRecyclerState)
        outState.putParcelable("Popular", popularRecyclerState)
        outState.putParcelable("Upcoming", upcomingRecyclerState)
    }

    override fun onDestroyView() {

        val topRatedRecyclerState =
            galleryViewerTopRate.getRecyclerView().layoutManager?.onSaveInstanceState()

        val popularRecyclerState =
            galleryViewerPopular.getRecyclerView().layoutManager?.onSaveInstanceState()


        val upcomingRecyclerState =
            galleryViewerUpComing.getRecyclerView().layoutManager?.onSaveInstanceState()


        viewModel.saveStateOfRecyclerViews(
            topRatedRecyclerState as LinearLayoutManager.SavedState?,
            popularRecyclerState as LinearLayoutManager.SavedState?,
            upcomingRecyclerState as LinearLayoutManager.SavedState?
        )

        onDestroyGlide()
        super.onDestroyView()
        viewBinding = null
    }


    override fun onItemClick(movieId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    fun goToMovieFullList(categoryId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToMovieFullListFragment(categoryId)
        findNavController().navigate(action)
    }

}
