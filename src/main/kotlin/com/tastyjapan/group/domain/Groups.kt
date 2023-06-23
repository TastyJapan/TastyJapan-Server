package com.tastyjapan.group.domain

import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import com.tastyjapan.member.domain.Member
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class Groups(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    val id: Long = 0L,

    @Column(name = "save_title")
    val title: String,

    ) : BaseEntity(), CustomObject {
    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null

    @OneToMany(mappedBy = "groups", cascade = [CascadeType.ALL], orphanRemoval = true)
    var groupRestaurantList: MutableList<GroupRestaurant> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Groups) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "Groups(id=$id, title='$title', member=${member?.id})"
    }

    fun addMember(member: Member) {
        this.member = member
        member.groups.add(this)
    }

    fun addGroupRestaurant(groupRestaurant: GroupRestaurant){
        groupRestaurantList.add(groupRestaurant)
        groupRestaurant.groups = this
    }
}

