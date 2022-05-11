package com.harper.capital.storage

import android.content.Context
import android.provider.ContactsContract
import com.harper.capital.domain.model.Contact
import com.harper.core.ext.orElse

class ContactsProvider(private val context: Context) {

    fun provide(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val contentResolver = context.contentResolver
        contentResolver.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null)
            .use { cursor ->
                while (cursor != null && cursor.moveToNext()) {
                    val id = cursor.getColumnIndex(ContactsContract.Contacts._ID).takeIf { it != -1 }
                        ?.let { cursor.getString(it) }
                    val name = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY).takeIf { it != -1 }
                        ?.let { cursor.getString(it) }
                    if (id == null || name == null) {
                        continue
                    }

                    val avatar = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI).takeIf { it != -1 }
                        ?.let { cursor.getString(it) }
                    var phone: String? = null

                    val hasPhoneNumber = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER).takeIf { it != -1 }
                        ?.let { cursor.getInt(it) }
                        .orElse(-1)
                    if (hasPhoneNumber != -1) {
                        contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                        ).use { phoneCursor ->
                            while (phoneCursor != null && phoneCursor.moveToNext()) {
                                phone = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER).takeIf { it != -1 }
                                    ?.let { cursor.getString(it) }
                            }
                        }
                    }
                    contacts.add(Contact(name, phone, avatar))
                }
            }
        return contacts
    }

    companion object {
        private val PROJECTION: Array<out String> = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
        )
    }
}
