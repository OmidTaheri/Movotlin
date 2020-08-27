package ir.omidtaheri.search.ui.SearchFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.search.databinding.SearchFragmentBinding
import ir.omidtaheri.search.di.components.DaggerSearchComponent
import ir.omidtaheri.search.ui.SearchFragment.adapters.FooterLoadStateAdapter
import ir.omidtaheri.search.ui.SearchFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.search.ui.SearchFragment.adapters.SearchMovieAdapter
import ir.omidtaheri.search.ui.SearchFragment.viewmodel.SearchViewModel
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class SearchFragment : BaseFragment(), SearchMovieAdapter.Callback {

    private lateinit var recyclerAdapter: SearchMovieAdapter
    private lateinit var viewModel: SearchViewModel

    private var _viewbinding: SearchFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var multiStatePage: MultiStatePage
    lateinit var searchbar: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
    }

    private fun initRecyclerViews() {
        multiStatePage.apply {
            recyclerAdapter = SearchMovieAdapter(MovieUiEntityComparator)
            recyclerAdapter.apply {
                SetCallback(this@SearchFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            ToLoadingState()
                        }

                        is LoadState.Error -> {
                            ToErrorState()
                        }

                        is LoadState.NotLoading -> {
                            ToDateState()
                        }
                    }
                }

                withLoadStateFooter(
                    FooterLoadStateAdapter((::retry))
                )
            }

            ConfigRecyclerView(
                recyclerAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                GridLayoutManager(context, 2)
            )
        }
    }

    private fun fetchData(query: String, page: Int) {
        viewModel.SearchMovieByQuery(query, page)
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = SearchFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        multiStatePage = _viewbinding!!.MultiStatePage

        searchbar = _viewbinding!!.searchbar
    }

    override fun ConfigDaggerComponent() {
        DaggerSearchComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    override fun setDataLiveObserver() {

        viewModel.DataLive.observe(this, Observer {
            recyclerAdapter.submitData(lifecycle, it)
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
        TODO("Not yet implemented")
    }

    override fun showToast(message: String) {
        TODO("Not yet implemented")
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewbinding = null
    }

    override fun OnItemClick(MovieId: Int) {
        TODO("Not yet implemented")
    }
}
