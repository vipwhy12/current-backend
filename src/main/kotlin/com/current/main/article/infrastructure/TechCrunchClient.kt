package com.current.main.article.infrastructure

import com.current.main.article.domain.Article
import com.current.main.article.domain.ArticleClient
import com.prof18.rssparser.RssParser
import org.slf4j.LoggerFactory
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

    private val log = LoggerFactory.getLogger(javaClass)

    override suspend fun fetchLatestArticles(): List<Article> {
        return try {
            val channel = parser.getRssChannel(rssUrl)

            channel.items.mapNotNull { item ->
                val title = item.title ?: return@mapNotNull null
                val link = item.link ?: return@mapNotNull null

                Article(
                    title = title,
                    link = link,
                    author = item.author,
                    description = item.description,
                    publishedAt = parseDate(item.pubDate),
                    source = sourceName
                )
            }
        } catch (e: Exception) {
            log.error("TechCrunch RSS 피드를 가져오는 중 에러 발생: URL={}, message={}", rssUrl, e.message)
            emptyList()
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
            // 파싱 실패 시 null을 반환합니다.
            null
        }
    }
}

