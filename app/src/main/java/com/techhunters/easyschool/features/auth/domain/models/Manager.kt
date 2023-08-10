package com.techhunters.easyschool.features.auth.domain.models


data class Manager(
    override val name: String="",
    override val imageUrl: String="",
    override val status: String="",
    override val position: String="",
    override val role: String="",
    val phoneNum: String=""
) : User( imageUrl, name, status, position, role)
