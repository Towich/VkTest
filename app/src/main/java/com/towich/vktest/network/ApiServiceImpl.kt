package com.towich.vktest.network


import com.towich.vktest.network.ApiService
import com.towich.vktest.ui.screen.main.MainScreenUiState
import io.ktor.client.HttpClient


class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

}