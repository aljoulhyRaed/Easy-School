package com.techhunters.easyschool.features.auth.domain.usecase

import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository

class SendRecoveryEmail(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(email: String) = accountRepository.sendRecoveryEmail(email)
}