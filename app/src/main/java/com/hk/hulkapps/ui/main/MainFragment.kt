package com.hk.hulkapps.ui.main

import android.os.Bundle
import android.util.Log
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
import com.hk.hulkapps.MainActivity
import com.hk.hulkapps.MyApplication
import com.hk.hulkapps.R
import com.hk.hulkapps.data.model.Video
import com.hk.hulkapps.databinding.FragmentMainBinding
import com.hk.hulkapps.ui.adapter.CategoryAdapter
import com.tonyodev.fetch2.*
import javax.inject.Inject
import com.tonyodev.fetch2.Fetch.Impl.getInstance

import com.tonyodev.fetch2core.Func
import com.tonyodev.fetch2.Download

import com.tonyodev.fetch2.FetchListener
import com.tonyodev.fetch2core.DownloadBlock


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
    private var fetch: Fetch? = null
    private val categoryAdapter=CategoryAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.mainComponent().create().inject(this)
        val fetchConfiguration: FetchConfiguration = FetchConfiguration.Builder(context!!).build()
        fetch = getInstance(fetchConfiguration)
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
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            // do whatever you need to do when the switch is toggled here
            if(isChecked){
                binding.switchButton.text=getString(R.string.offline_mode_on)
                //Download all file
                downloadVideos()
            }else{
                binding.switchButton.text=getString(R.string.offline_mode_off)
            }
        }
        observerViewModels()
        viewModel.loadMovies()
    }

    private fun downloadVideos() {
        var requests: MutableList<Request> = mutableListOf()
        var id=0
        viewModel.items.value?.forEach { category ->
            category.videos.forEach { video ->
                val url=video.sources[0]
                val fileName = url.substring( url.lastIndexOf('/')+1, url.length )
                val file = context!!.filesDir.absolutePath+"/"+fileName
                val request = Request(url, file)
                request.priority=Priority.HIGH
                request.networkType=NetworkType.ALL
                request.tag=id.toString()
                fetch!!.enqueue(request)
                requests.add(request)
            }
        }
//        fetch!!.enqueue(requests)
        val fetchListener: FetchListener = object : FetchListener {
            override fun onAdded(download: Download) {
                Log.i("asd","onAdded"+download.file)
            }

            override fun onCancelled(download: Download) {
                Log.i("asd","onCancelled"+download.file)
            }

            override fun onCompleted(download: Download) {
                Log.i("asd","downloaded"+download.file)
                var id=0
                viewModel.items.value?.forEach { category ->
                    category.videos.forEach { video ->
                        if(id.toString().equals(download.tag)){
                            video.didDownload=true
                            return@forEach
                        }
                        id++
                    }
                }
            }

            override fun onDeleted(download: Download) {
                Log.i("asd","onDeleted"+download.file)
            }

            override fun onDownloadBlockUpdated(
                download: Download,
                downloadBlock: DownloadBlock,
                totalBlocks: Int
            ) {
            }

            override fun onError(download: Download, error: Error, throwable: Throwable?) {
                Log.i("asd","error"+error.name)
            }

            override fun onPaused(download: Download) {
                Log.i("asd","onPaused"+download.file)
            }

            override fun onProgress(
                download: Download,
                etaInMilliSeconds: Long,
                downloadedBytesPerSecond: Long
            ) {
                val progress: Int = download.progress
                Log.i("bsd","progress= "+progress)
            }

            override fun onQueued(download: Download, waitingOnNetwork: Boolean) {
                Log.i("asd","onQueued"+download.file)
            }

            override fun onRemoved(download: Download) {
            }

            override fun onResumed(download: Download) {
            }

            override fun onStarted(
                download: Download,
                downloadBlocks: List<DownloadBlock>,
                totalBlocks: Int
            ) {
                Log.i("asd","downloadStarted")
            }

            override fun onWaitingNetwork(download: Download) {
                Log.i("asd","onWaitingNetwork"+download.file)
            }

        }
        fetch!!.addListener(fetchListener)
    }

    private fun observerViewModels() {
        viewModel.items.observe(viewLifecycleOwner, Observer {category->
            category?.let {
                binding.rcv.visibility=View.VISIBLE
                binding.switchButton.visibility=View.VISIBLE
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