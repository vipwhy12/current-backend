package com.current.main.article.ui

import com.current.main.article.infrastructure.RssClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val rssClient: RssClient
) {

    @GetMapping("/api/articles/rss")
    suspend fun fetchRss(@RequestParam url: String): String {
        rssClient.getNews(url)

        return "RSS 데이터를 성공적으로 콘솔에 출력했습니다."
    }
}
