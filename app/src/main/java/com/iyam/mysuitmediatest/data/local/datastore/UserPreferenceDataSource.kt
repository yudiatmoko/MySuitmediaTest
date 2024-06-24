package com.iyam.mysuitmediatest.data.local.datastore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.iyam.mysuitmediatest.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    fun getUserNameFlow(): Flow<String>
    suspend fun setUserName(name: String)
}

class UserPreferenceDataSourceImpl(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper
) : UserPreferenceDataSource {

    override suspend fun setUserName(name: String) {
        preferenceDataStoreHelper.putPreference(
            USER_NAME_PREF,
            name
        )
    }

    override fun getUserNameFlow(): Flow<String> {
        return preferenceDataStoreHelper.getPreference(
            USER_NAME_PREF,
            ""
        )
    }

    companion object {
        val USER_NAME_PREF = stringPreferencesKey("USER_NAME_PREF")
    }
}
