package com.tastyjapan

import com.tastyjapan.city.City
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.menu.domain.Menu
import com.tastyjapan.menu.domain.MenuSort
import com.tastyjapan.menu.domain.repository.MenuRepository
import com.tastyjapan.restaurant.domain.Restaurant
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import com.tastyjapan.review.domain.Review
import com.tastyjapan.review.domain.repository.ReviewRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
@RequiredArgsConstructor
@Slf4j
class DataLoader(
    val memberRepository: MemberRepository,
    val restaurantRepository: RestaurantRepository,
    val reviewRepository: ReviewRepository,
    val menuRepository: MenuRepository
): ApplicationRunner {
    val dataLoaderOn: Boolean = true
    override fun run(args: ApplicationArguments) {
        if(dataLoaderOn)
            runDataLoader()
    }

    @Throws(InterruptedException::class)
    private fun runDataLoader() {
        val member1 = Member(
            name = "chocochip",
            email = "dev.chocochip@gmail.com",
            picture = "https://lh3.googleusercontent.com/ogw/AOLn63FIEWhJRLAtFKsz3zBacrt3-OlUmV5BkCBqbhvs=s64-c-mo",
            role = Role.USER
        )
        memberRepository.save(member1)

        val member2 = Member(
            name = "kyk",
            email = "kyk@gmail.com",
            picture = "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202211/28/ac86e174-fb97-4a5c-8c8c-0f03b04cb59b.jpg",
            role = Role.USER
        )
        memberRepository.save(member2)

        val restaurant = Restaurant(
            name = "Tasty Japan",
            longitude = 139.6917,
            latitude = 35.6895,
            address = "Tokyo, Japan",
            rating = 4.5,
            city = City.TOKYO,
            summary = "Great Place"
        )


        val menu = Menu(
            name = "Tonkotsu Ramen",
            price = 10000,
            menu_sort = MenuSort.RAMEN,
            pictures = mutableListOf("https://img-cf.kurly.com/shop/data/goodsview/20211125/gv00000248650_1.jpg"),
        )

        restaurant.addMenu(menu)
        menuRepository.save(menu)

        val review = Review(
            content = "내 최애 도쿄 라멘 맛집!",
            rating = 4.8
        )

        review.member = member1
        review.restaurant = restaurant
        reviewRepository.save(review)

        restaurant.reviews.add(review)

        restaurantRepository.save(restaurant)
    }
}