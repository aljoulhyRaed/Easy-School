package com.techhunters.easyschool.features.auth.domain


import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>
   // val signUpState: MutableStateFlow<ResponseUiState>

    suspend fun authenticateWithEmailAndPassword(email: String, password: String)

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

    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()

}