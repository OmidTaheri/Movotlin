package ir.omidtaheri.genrelist.ui.GenreFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.genrelist.databinding.GenreFragmentBinding
import ir.omidtaheri.genrelist.di.components.DaggerGenreComponent
import ir.omidtaheri.genrelist.ui.GenreFragment.adapters.GenreListAdapter
import ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel.GenreViewModel
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage

class GenreFragment : BaseFragment(), GenreListAdapter.Callback {

    private lateinit var genreListAdapter: GenreListAdapter
    private lateinit var viewModel: GenreViewModel

    private var _viewbinding: GenreFragmentBinding? = null

    private val viewbinding
        get() = _viewbinding!!

    lateinit var multiStatePage: MultiStatePage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        })

        viewModel.genreErrorState.observe(this, Observer {
            multiStatePage.toErrorState()
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

    override fun onItemClick(genreId: Int) {
        val action = GenreFragmentDirections.actionGenreFragmentToMovieListFragment(genreId)
        findNavController().navigate(action)
    }
}
