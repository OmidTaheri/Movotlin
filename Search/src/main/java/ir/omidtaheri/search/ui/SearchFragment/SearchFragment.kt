package ir.omidtaheri.search.ui.SearchFragment

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import ir.omidtaheri.androidbase.BaseFragment
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


class SearchFragment : BaseFragment(), SearchMovieAdapter.Callback {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerAdapter: SearchMovieAdapter
    private lateinit var viewModel: SearchViewModel

    private var _viewbinding: SearchFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var multiStatePage: MultiStatePage
    lateinit var searchbar: TextInputEditText

    var STATE_SearchRecyclerview: Parcelable? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val saveSharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("SearchFragmentState", Context.MODE_PRIVATE)


        val saved_query = saveSharedPreferences.getString("SEARCH_QUERY", "")


        val SEARCH_ViewSavedState =
            loadRecyclerViewState(saveSharedPreferences, "SEARCH_RECYCLERVIEW_STATE")

        SEARCH_ViewSavedState?.let {
            STATE_SearchRecyclerview = it
        }


        initRecyclerViews()
        setViewListners()



        saved_query?.let {
            if (!(it.isEmpty())) {
                searchbar.setText(it)
                viewModel.initSearch(it)
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
                                viewModel.initSearch(query)


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
            setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
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

        toolbar = _viewbinding!!.mainToolbar


        if (getDarkModeStatus(requireContext())) {
            toolbar.menu.findItem( R.id.change_theme).icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_enable_night)
        } else {
            toolbar.menu.findItem( R.id.change_theme).icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_disable_night)
        }


        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.change_theme -> {
                    switchThemeMode(requireContext())
                    if (getDarkModeStatus(requireContext())) {
                        menuItem.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_enable_night)
                    } else {
                        menuItem.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_disable_night)
                    }

                    true
                }
                else -> false
            }
        }
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

            STATE_SearchRecyclerview?.let {
                multiStatePage.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                STATE_SearchRecyclerview = null
            }


            val handler = Handler()
            val runnable: Runnable = object : Runnable {
                override fun run() {
                    if (recyclerAdapter.getItemCount() == 0) {
                        multiStatePage.toEmptyState()
                    }

                }
            }
            handler.postDelayed(runnable, 3000)

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

    override fun onDestroy() {
        super.onDestroy()

        val save: SharedPreferences =
            requireActivity().getSharedPreferences("SearchFragmentState", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()
        ed.clear().apply()

    }


    override fun onItemClick(movieId: Int) {
        val i = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("movotlin://detailmovie/" + movieId)
        )

        val options = ActivityOptions.makeCustomAnimation(
            requireContext(),
            R.anim.anim_fade_scale_in,
            R.anim.anim_fade_scale_out
        )
        requireContext().startActivity(i, options.toBundle())
    }

    override fun onStop() {
        super.onStop()

        val save: SharedPreferences =
            requireActivity().getSharedPreferences("SearchFragmentState", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()

        val searchRatedRecyclerState =
            multiStatePage.getRecyclerView().layoutManager?.onSaveInstanceState()

        saveRecyclerViewStat(
            ed,
            "SEARCH_RECYCLERVIEW_STATE",
            searchRatedRecyclerState as LinearLayoutManager.SavedState
        )


        ed.putString("SEARCH_QUERY", searchbar.text.toString())

        ed.commit()


        onDestroyGlide()
    }
}
