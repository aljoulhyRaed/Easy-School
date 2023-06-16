package com.techhunters.easyschool.features.auth.data

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.techhunters.easyschool.features.auth.domain.AccountService
import com.techhunters.easyschool.features.auth.domain.User
import com.techhunters.easyschool.core.service.trace
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
   // private val context: EasySchoolActivity
) : AccountService {

    //private val TAG = AccountService::class.java.simpleName
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }
    /*override var signUpState: MutableStateFlow<ResponseUiState> =
        MutableStateFlow(ResponseUiState.NotInitialized)
        private set*/

    override suspend fun authenticateWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
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

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun linkAccount(email: String, password: String): Unit =
        trace(LINK_ACCOUNT_TRACE) {
            val credential = EmailAuthProvider.getCredential(email, password)
            auth.currentUser!!.linkWithCredential(credential).await()
        }
    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()

        // Sign the user back in anonymously.
        createAnonymousAccount()
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }

}