package com.techhunters.easyschool.features.auth.domain.models


data class Teacher(
    override val id: String="",
    override val name: String="",
    override val imageUrl: String="",
    override val status: String="",
    override val position: String="",
    override val role: String="",
    val phoneNum: String=""
) : User(id, imageUrl, name, status, position, role)
