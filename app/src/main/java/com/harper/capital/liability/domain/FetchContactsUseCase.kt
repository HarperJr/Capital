package com.harper.capital.liability.domain

import com.harper.capital.domain.model.Contact
import com.harper.capital.storage.ContactsProvider
import java.lang.Exception
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FetchContactsUseCase(private val contactsProvider: ContactsProvider) {

    suspend operator fun invoke(): List<Contact> = suspendCancellableCoroutine {
        try {
            it.resume(contactsProvider.provide())
        } catch (ex: Exception) {
            if (ex is CancellationException) {
                it.resumeWithException(ex)
            } else {
                it.resume(emptyList())
            }
        }
    }
}
