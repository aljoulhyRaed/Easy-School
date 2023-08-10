package com.techhunters.easyschool.features.grades.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.onesignal.OneSignal
import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository
import com.techhunters.easyschool.features.grades.domain.model.Grade
import com.techhunters.easyschool.features.grades.domain.repository.GradesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GradesRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: AccountRepository
) : GradesRepository {

    private val user = auth.getUser()

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getTeacherGrades(): Flow<List<Grade>> {
        return user.flatMapLatest { user ->
            gradesCollection().whereArrayContains("teachersIds", user.id).snapshots()
                .map { grades ->
                    grades.forEach { grade ->
                        val segName = grade.get("segmentName") as String
                        if (segName.isNotEmpty())
                            OneSignal.sendTag("class", segName)
                    }
                    grades.toObjects()
                }
        }
    }



    override fun getGrades(): Flow<List<Grade>> {
        lateinit var grades: Flow<List<Grade>>
        when ("teacher") {
             "teacher"-> grades = getTeacherGrades()
             "manager" -> grades = getAllGrades()
        }
        return grades
    }

    private fun getAllGrades(): Flow<List<Grade>> {
        return gradesCollection().snapshots().map { grades ->
            grades.toObjects()
        }
    }

    override suspend fun getStudentGrade(gradeId: String): Grade? =
        gradesCollection().document(gradeId).get().await().toObject()

    private fun gradesCollection(): CollectionReference =
        fireStore.collection(GRADES_COLLECTION)

    companion object {
        private const val GRADES_COLLECTION = "class"
    }
}