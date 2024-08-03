package com.krisna.diva.mynews.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.diva.mynews.R
import com.krisna.diva.mynews.core.data.Resource
import com.krisna.diva.mynews.core.ui.NewsAdapter
import com.krisna.diva.mynews.databinding.ActivityHomeBinding
import com.krisna.diva.mynews.detail.DetailNewsActivity
import com.krisna.diva.mynews.favorite.FavoriteActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        val newsAdapter = NewsAdapter()
        newsAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        homeViewModel.news.observe(this) { news ->
            if (news != null) {
                when (news) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        newsAdapter.setData(news.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            news.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }

        with(binding.rvNews) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}