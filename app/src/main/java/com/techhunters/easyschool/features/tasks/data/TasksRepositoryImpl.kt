package com.techhunters.easyschool.features.tasks.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.techhunters.easyschool.core.service.trace
import com.techhunters.easyschool.features.auth.domain.models.User
import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository
import com.techhunters.easyschool.features.tasks.domain.model.Task
import com.techhunters.easyschool.features.tasks.domain.repository.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val  accountRepository: AccountRepository,
): TasksRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTasks(gradeId: String, subId: String): Flow<List<Task>> {
        val tasks: Flow<List<Task>> =
            tasksCollection(subId, gradeId).snapshots().mapLatest { tasks ->
                tasks.toObjects()
            }
        return tasks
    }

    override suspend fun getTasksUser(): User? {
        return accountRepository.getCurrentUser()
    }

    override suspend fun getTask(gradeId: String, subId: String, taskId: String): Task? =
        tasksCollection(subId, gradeId).document(taskId).get().await().toObject()

    override suspend fun saveTask(gradeId: String, subId: String, task: Task): String =
        trace(SAVE_TASK_TRACE) { tasksCollection(subId, gradeId).add(task).await().id }

    override suspend fun updateTask(gradeId: String, subId: String, task: Task): Unit =
        trace(UPDATE_TASK_TRACE) {
            tasksCollection(subId, gradeId).document(task.id).set(task).await()
        }

    override suspend fun deleteTask(gradeId: String, subId: String, taskId: String) {
        tasksCollection(subId, gradeId).document(taskId).delete().await()
    }

    private fun tasksCollection(subId: String, gradeId: String): CollectionReference =
        fireStore.collection(SUBJECTS_COLLECTION).document(subId).collection(HOMEWORK_COLLECTION)
            .document(gradeId).collection(
                TASK_COLLECTION
            )

    companion object {
        private const val HOMEWORK_COLLECTION = "homeworks"
        private const val SUBJECTS_COLLECTION = "subjects"
        private const val TASK_COLLECTION = "tasks"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}