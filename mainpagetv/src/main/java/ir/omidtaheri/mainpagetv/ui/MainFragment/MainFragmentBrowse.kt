package ir.omidtaheri.mainpagetv.ui.MainFragment

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.keyIterator
import androidx.fragment.app.viewModels
import androidx.leanback.widget.*
import androidx.lifecycle.Observer
import ir.omidtaheri.androidbase.BaseBrowseTvFragment
import ir.omidtaheri.androidbase.viewmodelutils.GenericSavedStateViewModelFactory
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpagetv.R
import ir.omidtaheri.mainpagetv.di.components.DaggerMainComponent
import ir.omidtaheri.mainpagetv.entity.MovieUiEntity
import ir.omidtaheri.mainpagetv.presenters.MovieCardPresenter
import ir.omidtaheri.mainpagetv.ui.DetailFragment.DetailsActivity
import ir.omidtaheri.mainpagetv.ui.MainFragment.viewmodel.MainViewModel
import ir.omidtaheri.mainpagetv.utils.GlideBackgroundManager

/**
 * Loads a grid of cards with movies to browse.
 */
class MainFragmentBrowse : BaseBrowseTvFragment<MainViewModel>() {

    private lateinit var mBackgroundManager: GlideBackgroundManager
    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val TOP_RATED = 1
    private val POPULAR = 2
    private val UPCOMING = 3
    private lateinit var mRows: SparseArray<MovieRow>

    private val viewModel: MainViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBackgroundManager = GlideBackgroundManager(requireActivity())

        setupUIElements()
        createDataRows()
        setupRowsAdapter()
        setupEventListeners()
        fetchTopRatedMovies()
        fetchPopularMovies()
        fetchUpcomingMovies()
    }

    override fun setLiveDataObserver() {
        viewModel.poularLiveData.observe(this, Observer {

            val popularMovieRow = mRows.get(POPULAR)
            popularMovieRow.page = popularMovieRow.page + 1

            it.results.forEach {
                popularMovieRow.adapter.add(it)
            }
            startEntranceTransition()
        })

        viewModel.topRateLiveData.observe(this, Observer {

            val topRatedMovieRow = mRows.get(TOP_RATED)
            topRatedMovieRow.page = topRatedMovieRow.page + 1

            it.results.forEach {
                topRatedMovieRow.adapter.add(it)
            }
            startEntranceTransition()
        })

        viewModel.upComingLiveData.observe(this, Observer {

            val upcomingMovieRow = mRows.get(TOP_RATED)
            upcomingMovieRow.page = upcomingMovieRow.page + 1

            it.results.forEach {
                upcomingMovieRow.adapter.add(it)
            }
            startEntranceTransition()
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

    override fun configDaggerComponent() {
        DaggerMainComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }


    override fun showSnackBar(message: String) {
        TODO("Not yet implemented")
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }

    private fun createDataRows() {

        mRows = SparseArray()
        val moviecardPresenter = MovieCardPresenter()

        mRows.put(
            TOP_RATED, MovieRow(
                TOP_RATED, "Top Rated", 1, ArrayObjectAdapter(moviecardPresenter)
            )
        )

        mRows.put(
            POPULAR, MovieRow(
                POPULAR, "Popular", 1, ArrayObjectAdapter(moviecardPresenter)
            )
        )

        mRows.put(
            UPCOMING, MovieRow(
                UPCOMING, "Upcoming", 1, ArrayObjectAdapter(moviecardPresenter)
            )
        )
    }

    private fun setupRowsAdapter() {
        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        createRows()
        adapter = rowsAdapter
    }

    private fun createRows() {

        for (key in mRows.keyIterator()) {
            val movieRow = mRows.get(key)
            val headerItem = HeaderItem(movieRow.id.toLong(), movieRow.title)
            val listRow = ListRow(headerItem, movieRow.adapter)
            rowsAdapter.add(listRow)
        }
    }

    private fun fetchUpcomingMovies() {
        viewModel.getUpComingMovieList()
    }

    private fun fetchPopularMovies() {
        viewModel.getPopularMovieList()
    }

    private fun fetchTopRatedMovies() {
        viewModel.getTopRatedMovieList()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBackgroundManager.cancelBackgroundChange()
    }

    private fun setupUIElements() {
        title = getString(R.string.browse_title)
        // over title
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(requireContext(), R.color.tv_fastlane_background)
        // set search icon color
        searchAffordanceColor = ContextCompat.getColor(requireContext(), R.color.tv_search_opaque)
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            Toast.makeText(activity, "Implement your own in-app search", Toast.LENGTH_LONG)
                .show()
        }

        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {

            if (item is MovieUiEntity) {
                val intent = Intent(activity, DetailsActivity::class.java)

                intent.putExtra(DetailsActivity.MOVIEID, item.id)

                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    (itemViewHolder.view as ImageCardView).mainImageView,
                    DetailsActivity.SHARED_ELEMENT_NAME
                )
                    .toBundle()
                activity?.startActivity(intent, bundle)
            }
        }
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {
            if (item is MovieUiEntity) {
                if (item.backdropPath != null) {
                    mBackgroundManager.loadImageBackground(item.backdropPath)
                } else {
                    mBackgroundManager.setBackground(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.default_background
                        )!!
                    )
                }
            }
        }
    }
}
