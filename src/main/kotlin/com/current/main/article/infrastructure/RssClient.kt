package com.current.main.article.infrastructure

import org.springframework.stereotype.Component

@Component
class RssClient {
    private val parser = com.prof18.rssparser.RssParser()

    suspend fun getNews(url: String) {
        val channel = parser.getRssChannel(url)
        channel.items.forEach {
            println("제목: ${it.title}")
        }
    }
}
