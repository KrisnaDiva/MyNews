package com.krisna.diva.mynews.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.krisna.diva.mynews.R
import com.krisna.diva.mynews.core.domain.model.News
import com.krisna.diva.mynews.core.utils.DateUtils
import com.krisna.diva.mynews.core.utils.StringUtils
import com.krisna.diva.mynews.databinding.ActivityDetailNewsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DetailNewsActivity : AppCompatActivity() {

    private val detailNewsViewModel: DetailNewsViewModel by viewModel()
    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        displayNewsDetails()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }
    }

    private fun displayNewsDetails() {
        val detailNews = intent.getParcelableExtra<News>(EXTRA_DATA)
        detailNews?.let {
            binding.apply {
                tvTitle.text = it.title
                tvDate.text = it.publishedAt?.let { date -> DateUtils.formatDate(date) }
                tvAuthor.text = it.author
                tvContent.apply {
                    text = StringUtils.getTruncatedContentWithReadMore(it.content, it.url)
                    movementMethod = LinkMovementMethod.getInstance()
                }
                Glide.with(this@DetailNewsActivity)
                    .load(it.urlToImage)
                    .error(R.drawable.no_image)
                    .into(ivImage)

                var isFavorite = it.isFavorite
                setFavoriteStatus(isFavorite)
                fabFavorite.setOnClickListener {
                    isFavorite = !isFavorite
                    detailNewsViewModel.setFavoriteNews(detailNews, isFavorite)
                    setFavoriteStatus(isFavorite)
                }
            }
        }
    }

    private fun setFavoriteStatus(isFavorite: Boolean) {
        val drawableRes = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, drawableRes))
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
