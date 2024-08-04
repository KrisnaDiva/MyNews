package com.krisna.diva.mynews.home

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.krisna.diva.mynews.R
import com.krisna.diva.mynews.core.data.Resource
import com.krisna.diva.mynews.core.ui.NewsAdapter
import com.krisna.diva.mynews.databinding.ActivityHomeBinding
import com.krisna.diva.mynews.detail.DetailNewsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val newsAdapter = NewsAdapter().apply {
            onItemClickListener = { selectedData ->
                val intent = Intent(this@HomeActivity, DetailNewsActivity::class.java).apply {
                    putExtra(DetailNewsActivity.EXTRA_DATA, selectedData)
                }
                startActivity(intent)
            }
        }

        with(binding.rvNews) {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(this@HomeActivity, 2)
                } else {
                    LinearLayoutManager(this@HomeActivity)
                }
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    private fun observeViewModel() {
        homeViewModel.news.observe(this) { news ->
            when (news) {
                is Resource.Loading -> showLoading()
                is Resource.Success -> {
                    hideLoading()
                    (binding.rvNews.adapter as NewsAdapter).setData(news.data)
                }

                is Resource.Error -> {
                    hideLoading()
                    showError()
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.viewError.root.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError() {
        binding.viewError.root.visibility = View.VISIBLE
        binding.viewError.tvError.text = getString(R.string.something_wrong)
        binding.viewError.btnRetry.setOnClickListener {
            binding.viewError.root.visibility = View.GONE
            homeViewModel.refreshNews()
        }
    }

    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleFavorite = "favorite"

        if (splitInstallManager.installedModules.contains(moduleFavorite)) {
            moveToFavoriteActivity()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        getString(R.string.success_installing_module),
                        Toast.LENGTH_SHORT
                    ).show()
                    moveToFavoriteActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        getString(R.string.error_installing_module),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun moveToFavoriteActivity() {
        val uri = Uri.parse("mynews://favorite")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                try {
                    installFavoriteModule()
                } catch (e: Exception) {
                    Toast.makeText(this, getString(R.string.module_not_found), Toast.LENGTH_SHORT)
                        .show()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}