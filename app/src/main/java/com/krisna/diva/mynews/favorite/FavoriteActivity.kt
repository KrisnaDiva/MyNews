package com.krisna.diva.mynews.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.diva.mynews.core.ui.NewsAdapter
import com.krisna.diva.mynews.core.ui.ViewModelFactory
import com.krisna.diva.mynews.databinding.ActivityFavoriteBinding
import com.krisna.diva.mynews.detail.DetailNewsActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        val factory = ViewModelFactory.getInstance(this)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        favoriteViewModel.favoriteNews.observe(this) { dataNews ->
            newsAdapter.setData(dataNews)
            binding.viewEmpty.root.visibility =
                if (dataNews.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvNews) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }
}