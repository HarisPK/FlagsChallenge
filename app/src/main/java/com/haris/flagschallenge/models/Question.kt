package com.haris.flagschallenge.models

data class Question(
    val answer_id: Int,
    val countries: List<Country>,
    val country_code: String
)