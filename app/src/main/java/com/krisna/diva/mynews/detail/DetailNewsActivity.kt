package com.krisna.diva.mynews.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.krisna.diva.mynews.R
import com.krisna.diva.mynews.core.domain.model.News
import com.krisna.diva.mynews.databinding.ActivityDetailNewsBinding
import java.text.SimpleDateFormat
import java.util.Locale

class DetailNewsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    //    private lateinit var detailNewsViewModel: DetailNewsViewModel
    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

//        val factory = ViewModelFactory.getInstance(this)
//        detailNewsViewModel = ViewModelProvider(this, factory)[DetailNewsViewModel::class.java]

        val detailNews = intent.getParcelableExtra<News>(EXTRA_DATA)
        showDetailNews(detailNews)
    }

    private fun showDetailNews(detailNews: News?) {
        detailNews?.let {
            binding.tvTitle.text = detailNews.title
            binding.tvDate.text = detailNews.publishedAt?.let { it1 -> formatDate(it1) }
            binding.tvAuthor.text = detailNews.author
            binding.tvContent.apply {
                text = getTruncatedContentWithReadMore(detailNews.content, detailNews.url)
                movementMethod = LinkMovementMethod.getInstance()
            }

            Glide.with(this@DetailNewsActivity)
                .load(detailNews.urlToImage)
                .into(binding.ivImage)

//            var statusFavorite = detailNews.isFavorite
//            setStatusFavorite(statusFavorite)
//            binding.fab.setOnClickListener {
//                statusFavorite = !statusFavorite
//                detailNewsViewModel.setFavoriteNews(detailNews, statusFavorite)
//                setStatusFavorite(statusFavorite)
//            }
        }
    }

//    private fun setStatusFavorite(statusFavorite: Boolean) {
//        if (statusFavorite) {
//            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
//        } else {
//            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
//        }
//    }

    private fun getTruncatedContentWithReadMore(content: String?, url: String?): SpannableString {
        val truncatedContent = content?.substringBefore("…") ?: content
        val readMoreText = "… read more"
        val fullText = "$truncatedContent$readMoreText"

        return SpannableString(fullText).apply {
            truncatedContent?.let {
                setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        widget.context.startActivity(browserIntent)
                    }
                }, it.length, fullText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return date?.let { outputFormat.format(it) } ?: dateString
    }
}