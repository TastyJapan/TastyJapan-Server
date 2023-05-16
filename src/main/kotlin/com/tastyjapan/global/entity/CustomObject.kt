package com.tastyjapan.global.entity

interface CustomObject {
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
    override fun toString(): String
}