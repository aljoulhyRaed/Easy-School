package com.techhunters.easyschool.features.auth.domain.repository


import com.techhunters.easyschool.features.auth.domain.models.User
import com.techhunters.easyschool.features.auth.domain.models.UserStatus
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getCurrentUserId(): String
    fun hasUser(): Boolean
    suspend fun loginWithEmailAndPassword(email: String, password: String)

    fun getUser(): Flow<User>
    fun getRole():String
    suspend fun getCurrentUser(): User?
    suspend fun getUserProfileData(userId: String): User?
    suspend fun sendRecoveryEmail(email: String)

    suspend fun setUserStatus(userStatus: UserStatus)

    suspend fun signOut()

    // with phone
    /*fun authenticateWithPhoneNum(phoneNum: String)
    fun onCodeSent(
        verificationId: String,
        token:
        PhoneAuthProvider.ForceResendingToken
    )
     fun verifyOtp(code: String)
     fun onVerificationCompleted(credential: PhoneAuthCredential)
     fun onVerificationFailed(exception: Exception)
     fun getUserPhone(): String
*/
}