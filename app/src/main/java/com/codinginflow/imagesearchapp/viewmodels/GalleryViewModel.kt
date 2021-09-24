package com.codinginflow.imagesearchapp.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.codinginflow.imagesearchapp.repository.UnsplashRepository

class GalleryViewModel @ViewModelInject constructor(private val unsplashRepository: UnsplashRepository) :
    ViewModel() {

}