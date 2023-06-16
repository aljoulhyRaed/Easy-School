package com.techhunters.easyschool.features.teacher.tasks.domain

import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId val id: String = "",
    val title: String = "",
    val priority: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val description: String = "",
    val url: String = "",
    val flag: Boolean = false,
    val completed: Boolean = false
)
