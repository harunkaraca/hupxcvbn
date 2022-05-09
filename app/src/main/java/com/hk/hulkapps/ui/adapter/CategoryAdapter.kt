package com.hk.hulkapps.ui.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hk.hulkapps.BuildConfig
import com.hk.hulkapps.R
import com.hk.hulkapps.data.model.Category
import com.hk.hulkapps.data.model.Video
import com.hk.hulkapps.util.getProgressDrawable
import com.hk.hulkapps.util.loadImage
import java.io.File
import java.net.URL

class CategoryAdapter(var categories:MutableList<Category>):RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    fun updateData(newData:List<Category>){
        categories.clear()
        categories.addAll(newData)
        notifyDataSetChanged()
    }

    class CategoryViewHolder(view: View):RecyclerView.ViewHolder(view){

        private val txtName=view.findViewById<TextView>(R.id.txtName)
        private val rcv=view.findViewById<RecyclerView>(R.id.rcv)
        fun bind(category: Category){
            txtName.text=category.name
            var videoAdapter=VideoAdapter(category.videos)
            rcv.apply {
                layoutManager= LinearLayoutManager(context)
                adapter=videoAdapter
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }


    class VideoAdapter(var videos:MutableList<Video>):RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
        private var context: Context?=null
        class VideoViewHolder(view: View):RecyclerView.ViewHolder(view){
            val txtName=view.findViewById<TextView>(R.id.txtName)
            val txtDescription=view.findViewById<TextView>(R.id.txtDescription)
            val iv=view.findViewById<ImageView>(R.id.iv)
            val ivPlay=view.findViewById<ImageView>(R.id.ivPlay)
            val videoView=view.findViewById<VideoView>(R.id.videoView)
            val progressDrawable=getProgressDrawable(view.context)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
            this.context=parent.context
            return VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false))
        }

        override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
            val video=videos[position]
            holder.txtName.text=video.title
            holder.txtDescription.text=video.description
            holder.iv.loadImage(BuildConfig.BASE_IMAGE_URL+video.thumb,holder.progressDrawable)
            holder.ivPlay.setOnClickListener {
                holder.ivPlay.visibility=View.GONE
                holder.iv.visibility=View.GONE
                holder.videoView.visibility=View.VISIBLE
                if(video.didDownload){
                    var url=video.sources[0]
                    val fileName = url.substring( url.lastIndexOf('/')+1, url.length )
                    var fileUri=Uri.fromFile(File(context!!.filesDir.absolutePath+"/"+fileName))
                    holder.videoView.setVideoURI(fileUri)
                    Log.i("asd","get from local")
                }
                else
                    holder.videoView.setVideoPath(video.sources[0])
                holder.videoView.start()
                holder.videoView.seekTo(video.watchedTime)
            }
            holder.videoView.setOnClickListener {
                video.watchedTime=holder.videoView.currentPosition
                holder.ivPlay.visibility=View.VISIBLE
                holder.iv.visibility=View.VISIBLE
                holder.videoView.visibility=View.GONE
                holder.videoView.stopPlayback()
            }
        }
        override fun getItemCount()=videos.size
    }


}