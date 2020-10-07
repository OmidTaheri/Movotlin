package ir.omidtaheri.genrelist.ui.GenreFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.genrelist.R
import ir.omidtaheri.genrelist.databinding.GenreFragmentBinding
import ir.omidtaheri.genrelist.di.components.DaggerGenreComponent
import ir.omidtaheri.genrelist.ui.GenreFragment.adapters.GenreListAdapter
import ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel.GenreViewModel
import ir.omidtaheri.uibase.getDarkModeStatus
import ir.omidtaheri.uibase.loadRecyclerViewState
import ir.omidtaheri.uibase.saveRecyclerViewStat
import ir.omidtaheri.uibase.switchThemeMode
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class GenreFragment : BaseFragment(), GenreListAdapter.Callback {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var genreListAdapter: GenreListAdapter
    private lateinit var viewModel: GenreViewModel

    private var _viewbinding: GenreFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var multiStatePage: MultiStatePage

    var STATE_GenreRecyclerview: Parcelable? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveSharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("GenreFragmentState", Context.MODE_PRIVATE)


        val GENRE_ViewSavedState =
            loadRecyclerViewState(saveSharedPreferences, "GENRE_RECYCLERVIEW_STATE")

        GENRE_ViewSavedState?.let {
            STATE_GenreRecyclerview = it
        }

        initRecyclerViews()
        fetchData()
    }

    private fun initRecyclerViews() {
        multiStatePage.apply {
            genreListAdapter = GenreListAdapter()
            genreListAdapter.setCallback(this@GenreFragment)
            configRecyclerView(
                genreListAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            )
            setCustomLayoutAnimation(R.anim.layout_animation_fall_down)
            toLoadingState()
        }
    }

    private fun fetchData() {
        viewModel.getMovieListByGenre()
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = GenreFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {
        multiStatePage = _viewbinding!!.MultiStatePage

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

    override fun ConfigDaggerComponent() {
        DaggerGenreComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(GenreViewModel::class.java)
    }

    override fun setDataLiveObserver() {

        viewModel.dataLive.observe(this, Observer {

            genreListAdapter.addItems(it)
            multiStatePage.toDateState()

            STATE_GenreRecyclerview?.let {
                multiStatePage.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                STATE_GenreRecyclerview = null
            }


        })

        viewModel.genreErrorState.observe(this, Observer {
            multiStatePage.toErrorState(
                View.OnClickListener {
                    multiStatePage.toLoadingState()
                    viewModel.getMovieListByGenre()
                }

            )
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
            requireActivity().getSharedPreferences("GenreFragmentState", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()
        ed.clear().apply()
    }


    override fun onStop() {
        super.onStop()

        val save: SharedPreferences =
            requireActivity().getSharedPreferences("GenreFragmentState", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()

        val genreRatedRecyclerState =
            multiStatePage.getRecyclerView().layoutManager?.onSaveInstanceState()

        saveRecyclerViewStat(
            ed,
            "GENRE_RECYCLERVIEW_STATE",
            genreRatedRecyclerState as LinearLayoutManager.SavedState
        )

        ed.commit()

    }


    override fun onItemClick(genreId: Int) {
        val action = GenreFragmentDirections.actionGenreFragmentToMovieListFragment(genreId)
        findNavController().navigate(action)
    }
}
