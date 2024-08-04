package com.krisna.diva.mynews.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.diva.core.R
import com.krisna.diva.core.databinding.ItemNewsBinding
import com.krisna.diva.mynews.core.domain.model.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList = ArrayList<News>()
    var onItemClickListener: ((News) -> Unit)? = null

    fun setData(newData: List<News>?) {
        if (newData == null) return
        newsList.clear()
        newsList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNewsBinding.bind(itemView)
        fun bind(news: News) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .error(R.drawable.no_image)
                    .into(ivNews)
                tvTitle.text = news.title
                tvDescription.text = news.description
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(newsList[adapterPosition])
            }
        }
    }
}
