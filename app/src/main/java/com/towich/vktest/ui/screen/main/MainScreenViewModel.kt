package com.towich.vktest.ui.screen.main

import androidx.lifecycle.ViewModel
import com.towich.vktest.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

}