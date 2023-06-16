package com.techhunters.easyschool.core.ext

import com.techhunters.easyschool.features.teacher.tasks.domain.Task

fun Task?.hasDueDate(): Boolean {
    return this?.dueDate.orEmpty().isNotBlank()
}

fun Task?.hasDueTime(): Boolean {
    return this?.dueTime.orEmpty().isNotBlank()
}