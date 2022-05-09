package com.arifwidayana.simpledatastore.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreService(private val context: Context) {

    fun getCount(): Flow<Int> {
        return context.dataStore.data.map { pref ->
            pref[COUNTER_KEY] ?: 0
        }
    }

    suspend fun setCount(countValue: Int) {
        context.dataStore.edit { pref ->
            pref[COUNTER_KEY] = countValue
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("counter")
        private val COUNTER_KEY = intPreferencesKey("counter_key")
    }
}