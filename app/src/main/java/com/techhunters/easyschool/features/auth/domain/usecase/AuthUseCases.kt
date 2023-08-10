package com.techhunters.easyschool.features.auth.domain.usecase

data class AuthUseCases(
    val getCurrentUserId: GetCurrentUserId,
    val getCurrentUser: GetCurrentUser,
    val getUserProfileData: GetUserProfileData,
    val hasUser: HasUser,
    val loginWithEmailAndPassword: LoginWithEmailAndPassword,
    val sendRecoveryEmail: SendRecoveryEmail,
    val setUserStatus: SetUserStatus,
    val getUserRole: GetUserRole,
    val signOut: SignOut
)