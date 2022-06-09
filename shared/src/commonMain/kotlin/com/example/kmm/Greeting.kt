package com.example.kmm

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable





class Greeting {

    @Serializable
    data class NewsItem(val title: String, val text: String, val date: String, val url: String?)

    @Serializable
    data class NewsListResponseModel(
        val errorCode: Int?,
        val data: List<NewsItem>?
    )

    private val client = HttpClient()
    suspend fun getHtml(): String {
        val response = client.get("https://raw.githubusercontent.com/masha-b/api-testing/master/news-list.json")
        print(getNewsList())
        return response.bodyAsText()
    }

    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }

    suspend fun getNewsList(): List<NewsItem>? {
        val response: NewsListResponseModel = client.get("https://raw.githubusercontent.com/masha-b/api-testing/master/news-list.json").body()
        return response.data
    }
}