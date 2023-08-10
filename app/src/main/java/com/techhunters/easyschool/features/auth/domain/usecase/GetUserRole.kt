package com.techhunters.easyschool.features.auth.domain.usecase

import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository

class GetUserRole(private val accountRepository: AccountRepository) {
    operator fun invoke() = accountRepository.getRole()
}