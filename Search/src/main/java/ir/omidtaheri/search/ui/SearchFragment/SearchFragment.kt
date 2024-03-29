package ir.omidtaheri.search.ui.SearchFragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.androidbase.viewmodelutils.GenericSavedStateViewModelFactory
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.search.R
import ir.omidtaheri.search.databinding.SearchFragmentBinding
import ir.omidtaheri.search.di.components.DaggerSearchComponent
import ir.omidtaheri.search.ui.SearchFragment.adapters.FooterLoadStateAdapter
import ir.omidtaheri.search.ui.SearchFragment.adapters.MovieUiEntityComparator
import ir.omidtaheri.search.ui.SearchFragment.adapters.SearchMovieAdapter
import ir.omidtaheri.search.ui.SearchFragment.viewmodel.SearchViewModel
import ir.omidtaheri.uibase.*
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment<SearchViewModel>(), SearchMovieAdapter.Callback {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerAdapter: SearchMovieAdapter
    private var viewBinding: SearchFragmentBinding? = null
    private lateinit var multiStatePage: MultiStatePage
    private lateinit var searchbar: TextInputEditText
    private var stateSearchRecyclerview: Parcelable? = null
    private var isEnableAnimation = true
    private var savedQuery: String? = null

    private val viewModel: SearchViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (savedInstanceState != null) {
            isEnableAnimation = false
            stateSearchRecyclerview =
                savedInstanceState.getParcelable<LinearLayoutManager.SavedState?>("SearchRecycler")
            savedQuery =
                savedInstanceState.getString("SearchQuery")

        } else {

            savedQuery = viewModel.restoreSearchQuery()
            viewModel.restoreStateOfRecyclerView()?.let {
                stateSearchRecyclerview = it
                isEnableAnimation = false
            }
        }

        initRecyclerViews()
        setViewListeners()

        savedQuery?.let {
            if (checkSearchQuery(it)) {
                searchbar.setText(it)
                viewModel.searchQuery(it)
            }
        }
    }


    private fun initRecyclerViews() {
        multiStatePage.apply {
            recyclerAdapter = SearchMovieAdapter(MovieUiEntityComparator, requireContext())
            recyclerAdapter.apply {
                setCallback(this@SearchFragment)
                addLoadStateListener {

                    when (it.refresh) {

                        is LoadState.Loading -> {
                            toLoadingState()
                        }

                        is LoadState.Error -> {
                            toErrorState(View.OnClickListener {

                                val query = searchbar.text.toString()
                                viewModel.searchQuery(query)


                            })
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

            if (isEnableAnimation)
                setCustomLayoutAnimation(R.anim.layout_animation_fall_down)

            toEmptyState()
        }
    }


    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        viewBinding = SearchFragmentBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    override fun bindUiComponent() {
        multiStatePage = viewBinding!!.MultiStatePage
        searchbar = viewBinding!!.searchbar

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

    private fun setViewListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            whenStarted {
                searchbar.textChangedFlow().collectLatest {
                    showLoading(true)
                    viewModel.searchQuery(it)
                }
            }
        }

    }

    fun TextInputEditText.textChangedFlow(): Flow<String> = callbackFlow {

        val textWatcher = doOnTextChanged { text, _, _, _ ->
            offer(text.toString())
        }

        awaitClose { removeTextChangedListener(textWatcher) }

    }
        .debounce(1000)
        .filter {
            it.isNotEmpty() && it.isNotBlank()
        }
        .distinctUntilChanged()


    private fun checkSearchQuery(query: String): Boolean {
        val pattern = Regex("^(\\s)*\$")

        return query.isNotEmpty() &&
                query.isNotBlank() &&
                !pattern.matches(query)

    }


    override fun configDaggerComponent() {
        DaggerSearchComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }


    override fun setLiveDataObserver() {

        viewModel.dataLive.observe(this, Observer {
            recyclerAdapter.submitData(lifecycle, it)

            stateSearchRecyclerview?.let {
                multiStatePage.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                stateSearchRecyclerview = null
            }

            if (recyclerAdapter.itemCount == 0) {
                multiStatePage.toEmptyState()
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
        multiStatePage.toLoadingState()
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(viewBinding!!.root, message, BaseTransientBottomBar.LENGTH_LONG).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (::multiStatePage.isInitialized) {
            val searchRecyclerState =
                multiStatePage.getRecyclerView().layoutManager?.onSaveInstanceState()

            outState.putParcelable("SearchRecycler", searchRecyclerState)
        }

        outState.putString("SearchQuery", searchbar.text.toString())

    }


    override fun onDestroyView() {

        if (::multiStatePage.isInitialized) {
            val searchRecyclerState =
                multiStatePage.getRecyclerView().layoutManager?.onSaveInstanceState()

            viewModel.saveFragmentState(
                searchRecyclerState as LinearLayoutManager.SavedState?,
                searchbar.text.toString()
            )
        }


        onDestroyGlide()
        super.onDestroyView()
        viewBinding = null
    }


    override fun onItemClick(movieId: Int) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(movieId)
        findNavController().navigate(action)

    }

}
