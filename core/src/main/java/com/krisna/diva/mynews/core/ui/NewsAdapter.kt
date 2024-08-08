package com.krisna.diva.mynews.core.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.krisna.diva.core.R
import com.krisna.diva.core.databinding.ItemNewsBinding
import com.krisna.diva.mynews.core.domain.model.News

class NewsAdapter : ListAdapter<News, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.newsId == newItem.newsId
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }

    var onItemClickListener: ((News) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNewsBinding.bind(itemView)
        fun bind(news: News) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            lottieAnimationView.visibility = View.VISIBLE
                            lottieAnimationView.playAnimation()
                            ivNews.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            lottieAnimationView.visibility = View.GONE
                            lottieAnimationView.pauseAnimation()
                            ivNews.visibility = View.VISIBLE
                            return false
                        }
                    })
                    .into(ivNews)

                tvTitle.text = news.title
                tvDescription.text = news.description
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(getItem(adapterPosition))
            }
        }
    }
}