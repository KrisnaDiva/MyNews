package com.krisna.diva.mynews.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.krisna.diva.mynews.R
import com.krisna.diva.mynews.core.domain.model.News
import com.krisna.diva.mynews.core.utils.DateUtils
import com.krisna.diva.mynews.core.utils.StringUtils
import com.krisna.diva.mynews.databinding.ActivityDetailNewsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

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
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            lottieAnimationView.visibility = View.VISIBLE
                            lottieAnimationView.playAnimation()
                            ivImage.visibility = View.GONE
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
                            ivImage.visibility = View.VISIBLE
                            return false
                        }
                    })
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
