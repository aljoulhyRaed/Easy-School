package com.techhunters.easyschool.features.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.techhunters.easyschool.features.auth.domain.models.Manager
import com.techhunters.easyschool.features.auth.domain.models.Student
import com.techhunters.easyschool.features.auth.domain.models.Teacher
import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository
import com.techhunters.easyschool.features.auth.domain.models.User
import com.techhunters.easyschool.features.auth.domain.models.UserStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
    // private val context: EasySchoolActivity
) : AccountRepository {
    override fun getCurrentUserId(): String = auth.currentUser?.uid.orEmpty()
    private var userRole :String = ""
     override fun getRole():String {
         print("====================$userRole==================")
        return userRole
    }

    override fun getUser(): Flow<User> {
        return callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }
    }

    override fun hasUser(): Boolean = auth.currentUser != null


    override suspend fun loginWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun getCurrentUser(): User? {
        val user = currentCollection().document(getCurrentUserId()).get().await()
        return when (user.get("role")) {
            "teacher" -> {
                //UserRole.setUserRole("teacher")
                userRole = "teacher"
                user.toObject<Teacher>()
            }
            "student" -> {
                //UserRole.setUserRole("student")
                userRole = "student"
                user.toObject<Student>()
            }
            "manager" -> {
               // UserRole.setUserRole("manager")
                userRole = "manager"
                user.toObject<Manager>()
            }
            else -> User()
        }
    }

    override suspend fun getUserProfileData(userId: String): User? {
        val user = currentCollection().document(userId).get().await()
        return when (user.get("role")) {
            "teacher" -> user.toObject<Teacher>()
            "student" -> user.toObject<Student>()
            "manager" -> user.toObject<Manager>()
            else -> User()
        }
    }


    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun setUserStatus(userStatus: UserStatus) {
        currentCollection().document(getCurrentUserId())
            .update("status", userStatus.toString())
            .await()
    }

    override suspend fun signOut() = auth.signOut()


    private fun currentCollection(): CollectionReference =
        fireStore.collection(USER_COLLECTION)

    companion object {
        private const val USER_COLLECTION = "users"
    }

    /* private val authCallbacks = object :
         PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
         override fun onVerificationCompleted(credential: PhoneAuthCredential) {
             Log.i(
                 TAG,
                 "onVerificationCompleted: Verification completed. ${context.getString(R.string.verification_complete)}"
             )
             signUpState.value =
                 ResponseUiState.Loading(message = context.getString(R.string.verification_complete))
             // Use obtained credential to sign in user
             signInWithPhoneAuthCredential(credential)
         }

         override fun onVerificationFailed(exception: FirebaseException) {
             when (exception) {
                 is FirebaseAuthInvalidCredentialsException -> {
                     signUpState.value =
                         ResponseUiState.Error(
                             exception =
                             Exception(context.getString(R.string.verification_failed_try_again))
                         )
                 }

                 is FirebaseTooManyRequestsException -> {
                     signUpState.value =
                         ResponseUiState.Error(
                             exception =
                             Exception(context.getString(R.string.quota_exceeded))
                         )

                 }

                 else -> {
                     signUpState.value = ResponseUiState.Error(exception)

                 }
             }
         }
     }

     private val authBuilder: PhoneAuthOptions.Builder = PhoneAuthOptions.newBuilder(auth)
         .setCallbacks(authCallbacks)
         .setActivity(context)
         .setTimeout(120L, TimeUnit.SECONDS)

     private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
         auth.signInWithCredential(credential)
             .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                     Log.i(TAG, "signInWithAuthCredential:The sign in succeeded ")
                     signUpState.value =
                         ResponseUiState.Success(message = context.getString(R.string.phone_auth_success))
                 } else {


                     if (task.exception is FirebaseAuthInvalidCredentialsException) {
                         Log.e(TAG, context.getString(R.string.invalid_code))
                         signUpState.value =
                             ResponseUiState.Error(exception = Exception(context.getString(R.string.invalid_code)))

                         return@addOnCompleteListener
                     } else {
                         signUpState.value = ResponseUiState.Error(task.exception)
                         Log.e(TAG, "signInWithAuthCredential: Error ${task.exception?.message}")

                     }
                 }

             }

     }


     override fun authenticateWithPhoneNum(phoneNum: String) {
         auth.setLanguageCode("ar")
         val options = authBuilder
             .setPhoneNumber(phoneNum)
             .build()
         PhoneAuthProvider.verifyPhoneNumber(options)


     }

     override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {

     }

     override fun verifyOtp(code: String) {

     }

     override fun onVerificationCompleted(credential: PhoneAuthCredential) {

     }

     override fun onVerificationFailed(exception: Exception) {

     }

     override fun getUserPhone(): String {

     }
 */


    /*override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }
*/
    /* override suspend fun linkAccount(email: String, password: String): Unit =
         trace(LINK_ACCOUNT_TRACE) {
             val credential = EmailAuthProvider.getCredential(email, password)
             auth.currentUser!!.linkWithCredential(credential).await()
         }
     override suspend fun deleteAccount() {
         auth.currentUser!!.delete().await()
     }*/
}