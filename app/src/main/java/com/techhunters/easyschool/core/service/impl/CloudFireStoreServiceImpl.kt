package com.techhunters.easyschool.core.service.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.techhunters.easyschool.core.service.DataStorageService
import com.techhunters.easyschool.core.service.trace
import com.techhunters.easyschool.features.auth.domain.AccountService
import com.techhunters.easyschool.features.teacher.tasks.domain.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudFireStoreServiceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: AccountService
) : DataStorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val tasks: Flow<List<Task>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                currentCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
            }

    override suspend fun getTask(taskId: String): Task? =
        currentCollection(auth.currentUserId).document(taskId).get().await().toObject()

    override suspend fun save(task: Task): String =
        trace(SAVE_TASK_TRACE) { currentCollection(auth.currentUserId).add(task).await().id }

    override suspend fun update(task: Task): Unit =
        trace(UPDATE_TASK_TRACE) {
            currentCollection(auth.currentUserId).document(task.id).set(task).await()
        }

    override suspend fun delete(taskId: String) {
        currentCollection(auth.currentUserId).document(taskId).delete().await()
    }

    private fun currentCollection(uid: String): CollectionReference =
        fireStore.collection(USER_COLLECTION).document(uid).collection(TASK_COLLECTION)

    companion object {
        private const val USER_COLLECTION = "users"
        private const val TASK_COLLECTION = "tasks"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}