package com.gulfappdeveloper.project3.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.services.DataStoreService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreConstants.PREFERENCE_NAME)

class DataStoreServiceImpl(context: Context) : DataStoreService {

    private object PreferenceKeys {
        val operationCountKey = intPreferencesKey(name = DataStoreConstants.OPERATION_COUNT_KEY)
        val baseUrlKey = stringPreferencesKey(name = DataStoreConstants.BASE_URL_KEY)
        val serialNoKey = intPreferencesKey(name = DataStoreConstants.SERIAL_NO_KEY)
        val ipAddressKey = stringPreferencesKey(name = DataStoreConstants.IP_ADDRESS_KEY)
        val portAddressKey = stringPreferencesKey(name = DataStoreConstants.PORT_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun updateOperationCount() {
        dataStore.edit { preference ->
            val count = preference[PreferenceKeys.operationCountKey] ?: 0
            preference[PreferenceKeys.operationCountKey] = count + 1
        }
    }

    override suspend fun saveBaseUrl(baseUrl: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.baseUrlKey] = baseUrl
        }
    }

    override suspend fun updateSerialNo() {
        dataStore.edit { preference ->
            val count = preference[PreferenceKeys.serialNoKey] ?: 0
            preference[PreferenceKeys.serialNoKey] = count + 1
        }
    }

    override suspend fun saveIpAddress(ipAddress:String) {
        dataStore.edit { preference->
            preference[PreferenceKeys.ipAddressKey] = ipAddress
        }
    }

    override suspend fun savePortAddress(portAddress:String) {
        dataStore.edit { preference->
            preference[PreferenceKeys.portAddressKey] = portAddress
        }
    }

    override fun readOperationCount(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val operationCount = preferences[PreferenceKeys.operationCountKey] ?: 0
                operationCount
            }
    }

    override fun readBaseUrl(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val baseUrl = preferences[PreferenceKeys.baseUrlKey] ?: HttpRoutes.BASE_URL
                baseUrl
            }
    }

    override fun readSerialNo(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val serialNoCount = preferences[PreferenceKeys.serialNoKey] ?: 0
                serialNoCount
            }
    }

    override fun readIpaddress(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val ipAddress = preferences[PreferenceKeys.ipAddressKey] ?: ""
               ipAddress
            }
    }

    override fun readPortAddress(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val portAddress = preferences[PreferenceKeys.portAddressKey] ?: ""
                portAddress
            }
    }
}