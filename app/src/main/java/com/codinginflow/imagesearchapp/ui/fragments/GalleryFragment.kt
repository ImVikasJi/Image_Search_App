package com.codinginflow.imagesearchapp.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.adapter.UnsplashPhotoAdapter
import com.codinginflow.imagesearchapp.adapter.UnsplashPhotoLoadStateAdapter
import com.codinginflow.imagesearchapp.databinding.FragmentGalleryBinding
import com.codinginflow.imagesearchapp.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.unsplash_photo_load_state_footer.*

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        var rvAdapter = UnsplashPhotoAdapter()

        binding.rvRecyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null
            adapter = rvAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { rvAdapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { rvAdapter.retry() },
            )
        }

        binding.btnRetry.setOnClickListener {
            rvAdapter.retry()
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            rvAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        rvAdapter.addLoadStateListener { loadState ->
            binding.apply {
                pbProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    rvAdapter.itemCount < 1) {
                    rvRecyclerView.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.menuSearch)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.rvRecyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}