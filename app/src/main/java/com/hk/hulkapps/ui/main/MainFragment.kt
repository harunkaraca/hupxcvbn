package com.hk.hulkapps.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hk.hulkapps.MyApplication
import com.hk.hulkapps.R
import com.hk.hulkapps.databinding.FragmentMainBinding
import com.hk.hulkapps.ui.adapter.CategoryAdapter
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val categoryAdapter=CategoryAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.mainComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcv.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter=categoryAdapter
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing=false
            viewModel.refresh()
        }
        observerViewModels()
        viewModel.loadMovies()
    }
    private fun observerViewModels() {
        viewModel.items.observe(viewLifecycleOwner, Observer {category->
            category?.let {
                binding.rcv.visibility=View.VISIBLE
                categoryAdapter.updateData(it)
            }
        })
        viewModel.dataLoading.observe(viewLifecycleOwner, Observer {isLoading->
            isLoading?.let {
                binding.loadingView.visibility=if(it)View.VISIBLE else View.GONE
                if(it){
                    binding.listError.visibility=View.GONE
                    binding.rcv.visibility=View.GONE
                }
            }
        })
        viewModel.isDataLoadingError.observe(viewLifecycleOwner,{isDataLoadingError->
            isDataLoadingError?.let { binding.listError.visibility=if(it) View.VISIBLE else View.GONE }
        })
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
            }
    }
}