package com.current.main.article.application

import com.current.main.article.domain.ArticleClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleClients: List<ArticleClient>
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun syncAllArticles() = coroutineScope {
        log.info("모든 매체로부터 기사 동기화 시작")

        val results = articleClients.map { client ->
            async {
                try {
                    log.info("${client.sourceName} 수집 시작...")
                    val fetchedArticles = client.fetchLatestArticles()

                    log.info("${client.sourceName} 수집 완료: ${fetchedArticles.size}개 발견")
                    fetchedArticles.take(1).forEach {
                        log.info("수집 샘플: [${it.title}] - ${it.link}")
                    }

                    fetchedArticles // 결과 반환
                } catch (e: Exception) {
                    log.error("${client.sourceName} 수집 중 에러 발생", e)
                    emptyList() // 에러 발생 시 빈 리스트 반환
                }
            }
        }.awaitAll() // 모든 클라이언트의 작업이 끝날 때까지 대기

        log.info("모든 기사 동기화 프로세스 종료 (총 수집 건수: ${results.flatten().size})")
    }
}
