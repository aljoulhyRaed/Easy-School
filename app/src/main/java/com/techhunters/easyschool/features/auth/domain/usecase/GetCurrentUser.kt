package com.techhunters.easyschool.features.auth.domain.usecase

import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository

class GetCurrentUser(private val accountRepository: AccountRepository) {
    suspend operator fun invoke() = accountRepository.getCurrentUser()
}