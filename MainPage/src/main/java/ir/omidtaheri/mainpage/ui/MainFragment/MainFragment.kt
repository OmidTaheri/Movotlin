package ir.omidtaheri.mainpage.ui.MainFragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.databinding.MainFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerMainComponent
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.GalleryViewAdapter
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModel
import ir.omidtaheri.uibase.loadRecyclerViewState
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.uibase.saveRecyclerViewStat
import ir.omidtaheri.viewcomponents.GalleryViewer.GalleryViewer


class MainFragment : BaseFragment(), GalleryViewAdapter.Callback {

    private lateinit var viewModel: MainViewModel


    private var _viewbinding: MainFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var galleryViewerTopRate: GalleryViewer
    lateinit var galleryViewerPopular: GalleryViewer
    lateinit var galleryViewerUpComing: GalleryViewer

    lateinit var adapterTopRate: GalleryViewAdapter
    lateinit var adapterPopular: GalleryViewAdapter
    lateinit var adapterUpComing: GalleryViewAdapter

    var STATE_TopRatedRecyclerview: Parcelable? = null
    var STATE_PopularRecyclerview: Parcelable? = null
    var STATE_UpcomingRecyclerview: Parcelable? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val saveSharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("MainFragmentState", MODE_PRIVATE)


        val TOPRATED_ViewSavedState =
            loadRecyclerViewState(saveSharedPreferences, "TOPRATED_RECYCLERVIEW_STATE")
        val POPULAR_ViewSavedState =
            loadRecyclerViewState(saveSharedPreferences, "POPULAR_RECYCLERVIEW_STATE")
        val UPCOMING_ViewSavedState =
            loadRecyclerViewState(saveSharedPreferences, "UPCOMING_RECYCLERVIEW_STATE")


        TOPRATED_ViewSavedState?.let {
            STATE_TopRatedRecyclerview = it
        }

        POPULAR_ViewSavedState?.let {
            STATE_PopularRecyclerview = it
        }

        UPCOMING_ViewSavedState?.let {
            STATE_UpcomingRecyclerview = it
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
            setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
            // toLoadingState()
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            )
            setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
        }
    }

    private fun fetchData() {
        viewModel.getPopularMovieList()
        viewModel.getTopRatedMovieList()
        viewModel.getUpComingMovieList()
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = MainFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        galleryViewerTopRate = _viewbinding!!.GalleryViewer1
        galleryViewerPopular = _viewbinding!!.GalleryViewer2
        galleryViewerUpComing = _viewbinding!!.GalleryViewer3
    }

    override fun ConfigDaggerComponent() {
        DaggerMainComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun setDataLiveObserver() {

        viewModel.poularLiveData.observe(this, Observer {
            adapterPopular.submitData(lifecycle, it)

            STATE_PopularRecyclerview?.let {
                galleryViewerPopular.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                STATE_PopularRecyclerview = null
            }

        })

        viewModel.topRateLiveData.observe(this, Observer {
            adapterTopRate.submitData(lifecycle, it)

            STATE_TopRatedRecyclerview?.let {

                galleryViewerTopRate.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                STATE_TopRatedRecyclerview = null
            }

        })

        viewModel.upComingLiveData.observe(this, Observer {
            adapterUpComing.submitData(lifecycle, it)

            STATE_UpcomingRecyclerview?.let {
                galleryViewerUpComing.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                STATE_UpcomingRecyclerview = null
            }
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
            requireActivity().getSharedPreferences("MainFragmentState", MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()
        ed.clear().apply()
    }


    override fun onStop() {
        super.onStop()

        val save: SharedPreferences =
            requireActivity().getSharedPreferences("MainFragmentState", MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()


        val topRatedRecyclerState =
            galleryViewerTopRate.getRecyclerView().layoutManager?.onSaveInstanceState()

        val popularRecyclerState =
            galleryViewerPopular.getRecyclerView().layoutManager?.onSaveInstanceState()


        val upcomingRecyclerState =
            galleryViewerUpComing.getRecyclerView().layoutManager?.onSaveInstanceState()


        saveRecyclerViewStat(
            ed,
            "TOPRATED_RECYCLERVIEW_STATE",
            topRatedRecyclerState as LinearLayoutManager.SavedState
        )
        saveRecyclerViewStat(
            ed,
            "POPULAR_RECYCLERVIEW_STATE",
            popularRecyclerState as LinearLayoutManager.SavedState
        )
        saveRecyclerViewStat(
            ed,
            "UPCOMING_RECYCLERVIEW_STATE",
            upcomingRecyclerState as LinearLayoutManager.SavedState
        )
        ed.commit()


        onDestroyGlide()
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
