package com.example.recipes.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "auth_prefs"
)

/* DATA STORE
*
* 1) Preferences DataStore :-
*       I) key, value pairs, without schema
*      II) ASYNC via coroutines and Flow
*     III) Easy & Quick data migration
*      IV) No type safety
*
*
* 2) Proto DataStore :-
*       I) Typed object - protocol Buffer
*           PROTOCOL BUFFER :
*               1) Language and platform neutral
*               2) Serialize structure data
*               3) Faster & smaller than xml
*               4) Easy to read
*
*      II) Efficient Error handling
*     III) Async via Coroutine and Flow
*      IV) Quick Data Migration
*
* */
class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val ACCESS_TOKEN =
            stringPreferencesKey("access_token")
    }

    val accessToken: Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN]
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN)
        }
    }
}
