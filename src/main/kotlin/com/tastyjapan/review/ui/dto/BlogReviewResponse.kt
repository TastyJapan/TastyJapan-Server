package com.tastyjapan.review.ui.dto

import com.tastyjapan.review.domain.BlogSource

data class BlogReviewResponse (
    val url: String,
    val source: BlogSource
){
}