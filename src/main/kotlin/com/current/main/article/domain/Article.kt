package com.current.main.article.domain

import java.time.OffsetDateTime

data class Article (
    //제목
    val title: String,

    //원문 주소
    val link: String,

    //작성자
    val author: String?,

    //요약 내용
    val description: String?,

    //발행일
    val publishedAt: OffsetDateTime?,

    //출처
    val source: String,

    // 긁어온 시각
    val fetchedAt: OffsetDateTime = OffsetDateTime.now(),

    // 데이터가 변경된 시각
    val lastUpdatedAt: OffsetDateTime = OffsetDateTime.now()
)
