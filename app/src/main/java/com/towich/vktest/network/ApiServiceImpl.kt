package com.example.exploremarks.network


import com.towich.vktest.network.ApiService
import io.ktor.client.HttpClient


class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

}