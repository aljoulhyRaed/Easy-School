package com.techhunters.easyschool.features.subjects.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.onesignal.OneSignal
import com.techhunters.easyschool.features.auth.domain.models.Student
import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository
import com.techhunters.easyschool.features.grades.domain.repository.GradesRepository
import com.techhunters.easyschool.features.subjects.domain.model.Subject
import com.techhunters.easyschool.features.subjects.domain.repository.SubjectsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SubjectsRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val accountRepository: AccountRepository,
    private val gradesRepository: GradesRepository
) : SubjectsRepository {

    private suspend fun getSubjects(subjectsIds: List<String>): List<Subject?> {

        val subjects = mutableListOf<Subject?>()
        subjectsIds.forEach { subId ->
            val subject = subjectsCollection().document(subId).get().await().toObject<Subject>()
            subjects.add(subject)
        }
        if(subjects.isEmpty())
            print("===============subjects are empty ==================")
        return subjects
    }

    override suspend fun determineSubjects(subjectsIds: List<String>): List<Subject?> {
        val user = accountRepository.getCurrentUser()
        val subs = if (user is Student) {
            print("===============user is student ==================")
            val grade = gradesRepository.getStudentGrade(user.classId)
            if(grade!=null){
                val segName = grade.segmentName
                OneSignal.sendTag("class",segName)
                val ids = grade.subsIds
                getSubjects(subjectsIds = ids)
            }else emptyList()

        } else {
            print("===============user is teacher ==================")
            getSubjects(subjectsIds)
        }
        return subs
    }

    private fun subjectsCollection(): CollectionReference =
        fireStore.collection(SUBJECTS_COLLECTION)

    companion object {
        private const val SUBJECTS_COLLECTION = "subjects"
    }

}