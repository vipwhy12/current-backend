package com.current.main.article.infrastructure

import com.current.main.article.domain.Article
import com.current.main.article.domain.ArticleClient
import com.prof18.rssparser.RssParser
import org.springframework.beans.factory.annotation.Value

import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Component
class TechCrunchClient(
    @Value("\${rss.techcrunch.url}") private val rssUrl: String
) : ArticleClient {
    override val sourceName: String = "TechCrunch"
    private val parser = RssParser()

    override suspend fun fetchLatestArticles(): List<Article> {
        val channel = parser.getRssChannel(rssUrl)

        // RSS Item을 우리 도메인 모델인 Article로 변환합니다.
        return channel.items.map { item ->
            Article(
                title = item.title ?: "제목 없음",
                link = item.link ?: "",
                author = item.author,
                description = item.description,
                publishedAt = parseDate(item.pubDate),
                source = sourceName
            )
        }
    }

    // RSS의 날짜 문자열을 OffsetDateTime으로 변환하는 유틸 로직
    private fun parseDate(dateString: String?): OffsetDateTime? {
        return try {
            dateString?.let {
                //RFC_1123 형식을 사용합니다 (ex: Wed, 02 Oct 2024 10:00:00 +0000)
                OffsetDateTime.parse(it, DateTimeFormatter.RFC_1123_DATE_TIME)
            }
        } catch (e: java.time.format.DateTimeParseException) {
            // 파싱 실패 시 현재 시간으로 대체
            null
        }
    }

}
