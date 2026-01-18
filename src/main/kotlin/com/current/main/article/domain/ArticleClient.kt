package com.current.main.article.domain

interface ArticleClient {
    val sourceName: String

    suspend fun fetchLatestArticles(): List<Article>
}
