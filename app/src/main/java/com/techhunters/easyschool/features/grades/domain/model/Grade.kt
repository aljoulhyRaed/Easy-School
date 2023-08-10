package com.techhunters.easyschool.features.grades.domain.model

data class Grade(
    val id: String="",
    val name: String="",
    val segmentName: String="",
    val teachersIds: List<String> = emptyList(),
    val subsIds: List<String> = emptyList(),

)
