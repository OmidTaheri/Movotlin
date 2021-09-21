package ir.omidtaheri.genrelist.ui.GenreFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.androidbase.viewmodelutils.GenericSavedStateViewModelFactory
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.genrelist.R
import ir.omidtaheri.genrelist.databinding.GenreFragmentBinding
import ir.omidtaheri.genrelist.di.components.DaggerGenreComponent
import ir.omidtaheri.genrelist.ui.GenreFragment.adapters.GenreListAdapter
import ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel.GenreViewModel
import ir.omidtaheri.uibase.loadRecyclerViewState
import ir.omidtaheri.uibase.saveRecyclerViewStat
import ir.omidtaheri.uibase.switchThemeMode
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class GenreFragment : BaseFragment<GenreViewModel>(), GenreListAdapter.Callback {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var genreListAdapter: GenreListAdapter
    private var viewBinding: GenreFragmentBinding? = null
    private lateinit var multiStatePage: MultiStatePage
    private var stateGenreRecyclerview: Parcelable? = null

    private val viewModel: GenreViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveSharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("GenreFragmentState", Context.MODE_PRIVATE)

        val genreViewSavedState =
            loadRecyclerViewState(saveSharedPreferences, "GENRE_RECYCLERVIEW_STATE")

        genreViewSavedState?.let {
            stateGenreRecyclerview = it
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

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        viewBinding = GenreFragmentBinding.inflate(inflater, container, false)
        val view = viewBinding!!.root
        return view
    }

    override fun bindUiComponent() {
        multiStatePage = viewBinding!!.MultiStatePage
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
        DaggerGenreComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }


    override fun setLiveDataObserver() {

        viewModel.dataLive.observe(this, Observer {

            genreListAdapter.addItems(it)
            multiStatePage.toDateState()

            stateGenreRecyclerview?.let {
                multiStatePage.getRecyclerView().layoutManager?.onRestoreInstanceState(
                    it
                )
                stateGenreRecyclerview = null
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
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
