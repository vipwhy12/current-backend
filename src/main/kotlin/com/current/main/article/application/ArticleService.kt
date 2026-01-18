package com.current.main.article.application

import com.current.main.article.domain.ArticleClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleClients: List<ArticleClient>
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun syncAllArticles() {
        log.info("모든 매체로부터 기사 동기화 시작")

        articleClients.forEach { client ->
            try {
                log.info("${client.sourceName} 수집 시작...")
                val fetchedArticles = client.fetchLatestArticles()

                // 지금은 저장 대신 로그로 확인
                log.info("${client.sourceName} 수집 완료: ${fetchedArticles.size}개 발견")

                fetchedArticles.take(1).forEach {
                    log.info("수집 샘플: [${it.title}] - ${it.link}")
                }

                // TODO: 중복 체크 로직 (Repository 구현 후 추가)
                // 1. DB에서 기존에 저장된 링크인지 확인
                // 2. 새로운 기사만 필터링
                // 3. articleRepository.saveAll(newArticles)

            } catch (e: Exception) {
                log.error("${client.sourceName} 수집 중 에러 발생", e)
            }
        }

        log.info("모든 기사 동기화 프로세스 종료")
    }
}
