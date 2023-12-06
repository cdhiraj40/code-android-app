package com.getcode.view.main.invites

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.viewModelScope
import com.codeinc.gen.invite.v2.InviteService
import com.getcode.App
import com.getcode.R
import com.getcode.db.Database
import com.getcode.manager.SessionManager
import com.getcode.manager.TopBarManager
import com.getcode.network.repository.ContactsRepository
import com.getcode.network.repository.IdentityRepository
import com.getcode.network.repository.InviteRepository
import com.getcode.network.repository.replaceParam
import com.getcode.util.IntentUtils
import com.getcode.utils.PhoneUtils
import com.getcode.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx3.asFlowable
import javax.inject.Inject


data class ContactModel(
    val id: String = "",
    val name: String,
    val phoneNumber: String = "",
    val phoneNumberFormatted: String = "",
    val initials: String = "",
    val isInvited: Boolean = false,
    val isRegistered: Boolean = false,
)

data class InvitesSheetUiModel(
    val isContactsPermissionGranted: Boolean? = null,
    val isPermissionRequested: Boolean = false,
    val inviteCount: Int = 0,
    val contacts: List<ContactModel> = listOf(),
    val contactsFiltered: List<ContactModel> = listOf(),
    val contactFilterString: String = "",
    val contactsLoading: Boolean = true,
)

@HiltViewModel
class InvitesSheetViewModel @Inject constructor(
    private val inviteRepository: InviteRepository,
    private val contactsRepository: ContactsRepository,
    private val identityRepository: IdentityRepository
) :
    BaseViewModel() {
    val uiFlow = MutableStateFlow(InvitesSheetUiModel())

    fun init() {
        Database.isInit
            .flatMap { inviteRepository.getInviteCount() }
            .subscribeOn(Schedulers.computation())
            .subscribe {
                uiFlow.update { v ->
                    v.copy(inviteCount = it.toInt())
                }
            }
    }

    private fun initContacts() {
        CoroutineScope(Dispatchers.IO).launch {
            val keyPair = SessionManager.getKeyPair() ?: return@launch

            val contacts = getLocalContacts()
            if (contacts.isEmpty()) {
                updateContacts(contacts)
                return@launch
            }

            identityRepository.getUserLocal().asFlowable()
                .flatMap { res ->
                    contactsRepository.uploadContacts(
                        keyPair,
                        res.dataContainerId.toByteArray(),
                        contacts.map { c -> c.phoneNumber })
                        .map { res }
                }
                .flatMap {
                    contactsRepository.getContacts(
                        keyPair,
                        it.dataContainerId.toByteArray()
                    )
                }
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    { remoteContacts -> displayContacts(contacts, remoteContacts) },
                    { displayContacts(contacts) }
                )
        }
    }

    private fun displayContacts(
        contacts_: List<ContactModel>,
        remoteContacts: List<ContactsRepository.GetContactsResponse> = listOf()
    ) {
        val remoteContactsMap =
            remoteContacts.associateBy { PhoneUtils.makeE164(it.phoneNumber) }
        val contacts = contacts_.map { contact ->
            val phoneInternational = PhoneUtils.makeE164(contact.phoneNumber)
            contact.copy(
                isInvited = remoteContactsMap[phoneInternational]?.isInvited == true,
                isRegistered = remoteContactsMap[phoneInternational]?.isRegistered == true,
            )
        }.let { list ->
            list.sortedWith(
                compareBy<ContactModel> { !it.isRegistered }.thenBy { !it.isInvited }
                    .thenBy { it.name }
            )
        }
        updateContacts(contacts)
    }

    private fun updateContacts(contacts: List<ContactModel>) {
        uiFlow.update {
            it.copy(
                contacts = contacts,
                contactsFiltered = contacts,
                contactsLoading = false
            )
        }
    }

    fun inviteContactCustomInput(phoneValue: String) {
        val phoneE164 = PhoneUtils.makeE164(phoneValue, java.util.Locale.getDefault())

        if (phoneE164.length < 8) {
            TopBarManager.showMessage(
                App.getInstance().getString(
                    R.string.error_title_invalidInvitePhone),
                App.getInstance().getString(
                    R.string.error_description_invalidInvitePhone)
            )
        } else {
            inviteContact(phoneE164)
        }
    }

    fun inviteContact(phoneValue: String) {
        viewModelScope.launch {
        inviteRepository.whitelist(phoneValue)
            .subscribe {
                if (it == InviteService.InvitePhoneNumberResponse.Result.INVITE_COUNT_EXCEEDED) {
                    TopBarManager.showMessage(
                        App.getInstance().getString(
                            R.string.error_title_noInvitesLeft),
                        App.getInstance().getString(
                            R.string.error_description_noInvitesLeft)
                    )
                } else {
                    IntentUtils.launchSmsIntent(
                        phoneValue,
                        getString(R.string.subtitle_inviteText).replaceParam("getcode.com/download")
                    )
                    val contacts = uiFlow.value.contacts.toMutableList()
                    val index = contacts.indexOfFirst { i -> i.phoneNumber == phoneValue }
                    if (index >= 0) {
                        contacts[index] = contacts[index].copy(isInvited = true)
                        updateContacts(contacts)
                    }
                }
            }
        }
    }

    fun onContactsPermissionChanged(isGranted: Boolean) {
        if (isGranted && uiFlow.value.isContactsPermissionGranted == isGranted) return
        uiFlow.update {
            it.copy(isContactsPermissionGranted = isGranted)
        }

        if (isGranted) {
            initContacts()
        } else if (uiFlow.value.isPermissionRequested) {
            TopBarManager.showMessage(
                TopBarManager.TopBarMessage(
                    title = "Failed to access contacts",
                    message = "Please allow Code access to Contacts in Settings.",
                    type = TopBarManager.TopBarMessageType.ERROR,
                    secondaryText = App.getInstance().getString(R.string.action_openSettings),
                    secondaryAction = { IntentUtils.launchAppSettings() }
                )
            )
        }
    }

    fun onContactsPermissionRequested() {
        uiFlow.update {
            it.copy(isPermissionRequested = true)
        }
    }

    fun updateContactFilterString(contactFilterString: String) {
        val contacts = (uiFlow.value.contacts)
        val contactsFiltered =
            if (contactFilterString.isBlank()) {
                contacts
            } else {
                (uiFlow.value.contacts)
                    .filter {
                        val filterString = contactFilterString.toLowerCase(Locale.current)
                        it.name.lowercase().contains(filterString.lowercase()) ||
                                it.phoneNumber.contains(filterString)
                    }
            }

        uiFlow.update {
            it.copy(
                contactFilterString = contactFilterString,
                contactsFiltered = contactsFiltered
            )
        }
    }

    private fun getLocalContacts(): List<ContactModel> {
        val map = linkedMapOf<String, ContactModel>()

        val cr: ContentResolver = App.getInstance().contentResolver
        val cur: Cursor? = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
        if ((cur?.count ?: 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                val idIndex = cur.getColumnIndex(ContactsContract.Contacts._ID)
                val nameIndex = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val hasPhoneIndex = cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)

                val id: String = cur.getString(idIndex)
                val name: String = cur.getString(nameIndex) ?: continue

                val initials = when {
                    name.length >= 3 && name.contains(" ") -> {
                        name
                            .split(" ")
                            .take(2)
                            .joinToString("") { it.take(1) }
                    }
                    name.isNotEmpty() -> name.take(1)
                    else -> ""
                }

                if (cur.getInt(hasPhoneIndex) > 0) {
                    val pCur: Cursor? = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    pCur ?: return listOf()

                    while (pCur.moveToNext()) {
                        val phone =
                            pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER
                            ).let {
                                pCur.getString(it)
                            } ?: pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            ).let {
                                pCur.getString(it)
                            }.let {
                                if (!it.startsWith("+")) "+$it" else it
                            }

                        val phoneNumber: String = phone
                            .replace("(", "")
                            .replace(")", "")
                            .replace("-", "")
                            .replace(" ", "")

                        val phoneNumberFormatted = com.getcode.util.PhoneUtils.formatNumber(
                            phoneNumber
                        )

                        if (phoneNumber.matches(Regex("^\\+[1-9]\\d{7,14}$"))) {
                            map[phoneNumber] = ContactModel(
                                id = id,
                                name = name,
                                phoneNumber = phoneNumber,
                                phoneNumberFormatted = phoneNumberFormatted,
                                initials = initials,
                                isInvited = false
                            )
                        }
                    }
                    pCur.close()
                }
            }
        }
        cur?.close()

        return map.values.toList()
    }
}
