package com.example.kmm

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

@Serializable
data class NewsItem(
    val title: String,
    val image: String,
    val text: String,
    val date: String
)

@Serializable
data class NewsListResponseModel(
    val errorCode: Int?,
    val data: List<NewsItem>
)


class Greeting {



    private val client = HttpClient()
    suspend fun getHtml(): String {
        var text: String = ""
        getNewsListFromContentTypeText().forEach {
            text += it.title
        }
        println(getNewsListFromContentTypeText())
        return text
        /*val response =
            client.get("https://raw.githubusercontent.com/masha-b/api-testing/master/news-list.json")

        return response.bodyAsText()*/
    }

    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }

    suspend fun getNewsList(): List<NewsItem> {
        val response: NewsListResponseModel =
            client.get("https://raw.githubusercontent.com/masha-b/api-testing/master/news-list.json").body()
        return response.data
    }

    suspend fun getNewsListFromContentTypeText(): List<NewsItem> {
        val response: String =
            client.get("https://raw.githubusercontent.com/masha-b/api-testing/master/news-list.json")
                .body()
        val json = Json {
            ignoreUnknownKeys = true
        }
        val result: NewsListResponseModel = json.decodeFromString(response)
        return result.data
    }
}