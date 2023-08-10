package com.techhunters.easyschool.features.auth.domain.usecase

import com.techhunters.easyschool.features.auth.domain.models.UserStatus
import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository

class SetUserStatus(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(userStatus: UserStatus) =
        accountRepository.setUserStatus(userStatus)
}