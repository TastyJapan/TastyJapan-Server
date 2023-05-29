package com.tastyjapan.fixture

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role

object MemberFixture {
    fun createMemberChoco(): Member {
        return Member(
            name = "chocochip",
            email = "dev.chocochip@gmail.com",
            picture = "https://lh3.googleusercontent.com/ogw/AOLn63FIEWhJRLAtFKsz3zBacrt3-OlUmV5BkCBqbhvs=s64-c-mo",
            role = Role.USER
        )
    }

    fun createMemberKyk(): Member {
        return Member(
            name = "kyk",
            email = "kyk@gmail.com",
            picture = "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202211/28/ac86e174-fb97-4a5c-8c8c-0f03b04cb59b.jpg",
            role = Role.USER
        )
    }
}