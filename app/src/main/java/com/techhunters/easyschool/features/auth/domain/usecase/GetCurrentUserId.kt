package com.techhunters.easyschool.features.auth.domain.usecase

import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository

class GetCurrentUserId(private val accountRepository: AccountRepository ) {
    operator fun invoke() = accountRepository.getCurrentUserId()
}