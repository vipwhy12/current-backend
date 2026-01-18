package com.current.main.article.application.scheduler

import com.current.main.article.application.ArticleService
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ArticleScheduleTasks(
    private val articleService: ArticleService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelayString = "\${rss.scheduler.interval:600000}")
    fun importTechCrunchArticles() = runBlocking {
        log.info("정기 뉴스 수집 시작...")
        articleService.syncAllArticles()
    }
}
