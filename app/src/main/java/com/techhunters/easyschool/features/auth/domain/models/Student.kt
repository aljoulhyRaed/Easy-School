package com.techhunters.easyschool.features.auth.domain.models


data class Student(
    override val id: String="",
    override val name: String="",
    override val role: String="",
    val guardianPhoneNum: String="",
    val classId: String="",
    override val imageUrl: String="",
    override val status: String="",
    override val position: String="",
) : User(id, imageUrl, name, status, position, role)
