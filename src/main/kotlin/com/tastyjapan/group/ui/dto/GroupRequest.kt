package com.tastyjapan.group.ui.dto

data class GroupRequest (
    val title: String,
    val restaurantList: List<Long>
){
}