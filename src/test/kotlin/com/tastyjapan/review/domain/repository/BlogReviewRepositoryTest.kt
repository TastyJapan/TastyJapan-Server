package com.tastyjapan.review.domain.repository

import com.tastyjapan.review.domain.BlogReview
import com.tastyjapan.review.domain.BlogSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class BlogReviewRepositoryTest {

    @Autowired
    lateinit var blogReviewRepository: BlogReviewRepository

    lateinit var blogReview: BlogReview

    @BeforeEach
    fun setUp() {
        blogReviewRepository.deleteAll()
        blogReview = BlogReview(
            url = "https://www.naver.com",
            source = BlogSource.NAVER,
        )
        blogReviewRepository.save(blogReview)
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 메뉴를 저장하면, 해당 ID의 메뉴는 후에 작성된 메뉴 정보로 업데이트 된다.")
    @Test
    fun saveSameId() {
        // given
        val blogReview2 = BlogReview(
            id = blogReview.id,
            url = "https://www.naver.com",
            source = BlogSource.NAVER,
        )
        // when
        blogReviewRepository.save(blogReview2)
        // then
        assertThat(blogReviewRepository.findById(blogReview.id).get().url).isEqualTo(blogReview2.url)
    }

    @DisplayName("id가 없는 Menu 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    fun saveNoIdGroup() {
        // given
        val blogReview2 = BlogReview(
            url = "https://www.naver.com",
            source = BlogSource.NAVER,
        )
        blogReviewRepository.save(blogReview2)
        // when&then
        val idDiff = blogReview2.id - blogReview.id
        assertThat(idDiff).isEqualTo(1L);
    }
}