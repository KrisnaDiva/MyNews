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

        setupActionBar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        val newsAdapter = NewsAdapter().apply {
            onItemClickListener = { selectedData ->
                val intent = Intent(this@FavoriteActivity, DetailNewsActivity::class.java).apply {
                    putExtra(DetailNewsActivity.EXTRA_DATA, selectedData)
                }
                startActivity(intent)
            }
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

    private fun observeViewModel() {
        favoriteViewModel.favoriteNews.observe(this) { dataNews ->
            (binding.rvNews.adapter as NewsAdapter).setData(dataNews)
            binding.viewEmpty.root.visibility = if (dataNews.isNotEmpty()) View.GONE else View.VISIBLE
        }
    }
}
