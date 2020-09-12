package ir.omidtaheri.search.ui.SearchFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.Disposable
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.search.databinding.SearchFragmentBinding
import ir.omidtaheri.search.di.components.DaggerSearchComponent
import ir.omidtaheri.search.ui.SearchFragment.adapters.FooterLoadStateAdapter
import ir.omidtaheri.search.ui.SearchFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.search.ui.SearchFragment.adapters.SearchMovieAdapter
import ir.omidtaheri.search.ui.SearchFragment.viewmodel.SearchViewModel
import ir.omidtaheri.uibase.onDestroyGlide
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
        setViewListners()
    }


    private fun initRecyclerViews() {
        multiStatePage.apply {
            recyclerAdapter = SearchMovieAdapter(MovieUiEntityComparator,requireContext())
            recyclerAdapter.apply {
                setCallback(this@SearchFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            toLoadingState()
                        }

                        is LoadState.Error -> {
                            toErrorState()
                        }

                        is LoadState.NotLoading -> {
                            toDateState()
                        }
                    }
                }

                withLoadStateFooter(
                    FooterLoadStateAdapter((::retry))
                )
            }

            configRecyclerView(
                recyclerAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                GridLayoutManager(context, 2)
            )
            toEmptyState()
        }
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

    private fun setViewListners() {

        viewModel.setSearchSubjectObserver()

        searchbar.doOnTextChanged { text, start, before, count ->
            viewModel.searchSubject.onNext(text.toString())
        }

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

        viewModel.dataLive.observe(this, Observer {
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
        multiStatePage.toLoadingState()
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

    override fun onItemClick(movieId: Int) {
        val i = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("movotlin://detailmovie/" + movieId)
        )
        requireContext().startActivity(i)
    }
    override fun onStop() {
        super.onStop()
        onDestroyGlide()
    }
}
