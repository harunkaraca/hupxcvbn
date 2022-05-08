package com.hk.hulkapps.ui.adapter

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

        class VideoViewHolder(view: View):RecyclerView.ViewHolder(view){

            private val txtName=view.findViewById<TextView>(R.id.txtName)
            private val txtDescription=view.findViewById<TextView>(R.id.txtDescription)
            private val iv=view.findViewById<ImageView>(R.id.iv)
            private val ivPlay=view.findViewById<ImageView>(R.id.ivPlay)
            private val videoView=view.findViewById<VideoView>(R.id.videoView)
            private val progressDrawable=getProgressDrawable(view.context)
            fun bind(video: Video){
                txtName.text=video.title
                txtDescription.text=video.description
                iv.loadImage(BuildConfig.BASE_IMAGE_URL+video.thumb,progressDrawable)
                ivPlay.setOnClickListener {
                    ivPlay.visibility=View.GONE
                    iv.visibility=View.GONE
                    videoView.visibility=View.VISIBLE
                    videoView.setVideoPath(video.sources[0])
                    videoView.start()
                    videoView.seekTo(video.watchedTime)
                }
                videoView.setOnClickListener {
                    video.watchedTime=videoView.currentPosition
                    ivPlay.visibility=View.VISIBLE
                    iv.visibility=View.VISIBLE
                    videoView.visibility=View.GONE
                    videoView.stopPlayback()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
            return VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false))
        }

        override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
            holder.bind(videos[position])
        }
        override fun getItemCount()=videos.size
    }


}