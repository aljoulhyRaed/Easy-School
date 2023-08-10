package com.techhunters.easyschool.features.auth.domain.usecase

import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository

class LoginWithEmailAndPassword(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(email: String, password: String) =
        accountRepository.loginWithEmailAndPassword(email, password)
}