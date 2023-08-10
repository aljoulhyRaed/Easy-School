package com.techhunters.easyschool.features.auth.domain.usecase

import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository

class GetUserProfileData(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(userId: String) =
        accountRepository.getUserProfileData(userId)
}