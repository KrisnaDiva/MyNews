package com.krisna.diva.mynews.favorite

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.diva.mynews.core.ui.NewsAdapter
import com.krisna.diva.mynews.detail.DetailNewsActivity
import com.krisna.diva.mynews.favorite.databinding.ActivityFavoriteBinding
import com.krisna.diva.mynews.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteActivity : AppCompatActivity() {
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(favoriteModule)
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        val newsAdapter = NewsAdapter()
        newsAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        favoriteViewModel.favoriteNews.observe(this) { dataNews ->
            newsAdapter.setData(dataNews)
            binding.viewEmpty.root.visibility =
                if (dataNews.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvNews) {
            layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(this@FavoriteActivity, 2)
            } else {
                LinearLayoutManager(this@FavoriteActivity)
            }
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }
}