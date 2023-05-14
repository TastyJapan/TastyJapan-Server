package com.tastyjapan.member.domain

import lombok.Getter

@Getter
enum class Role(role: String) {
    ADMIN("admin"),
    USER("user")
}