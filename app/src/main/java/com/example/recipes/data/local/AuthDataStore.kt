package com.example.recipes.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import coil3.Image
import com.example.recipes.domain.model.User
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
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val USER_NM = stringPreferencesKey("user_nm")
        private val FIRST_NAME = stringPreferencesKey("first_name")
        private val LAST_NAME = stringPreferencesKey("last_name")
        private val EMAIL = stringPreferencesKey("email")
        private val IMAGE = stringPreferencesKey("image")
    }


    // access token
    val accessToken: Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN]
        }

//    suspend fun saveToken(token: String) {
//        context.dataStore.edit { prefs ->
//            prefs[ACCESS_TOKEN] = token
//        }
//    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN)
        }
    }

    // user

    val user: Flow<User?> =
        context.dataStore.data.map { prefs ->

            val userNm = prefs[USER_NM] ?: ""
            val firstNm = prefs[FIRST_NAME] ?: ""
            val lastNm = prefs[LAST_NAME] ?: ""
            val email = prefs[EMAIL] ?: ""
            val image = prefs[IMAGE] ?: ""

            User(userNm,firstNm,lastNm,email,image)
        }


    suspend fun saveUser(
        userNm : String,
        firstName : String,
        lastName : String,
        email : String,
        image : String,
        token : String,
    ){
        context.dataStore.edit { prefs ->
            prefs[USER_NM] = userNm
            prefs[FIRST_NAME] = firstName
            prefs[LAST_NAME] = lastName
            prefs[EMAIL] = email
            prefs[IMAGE] = image
            prefs[ACCESS_TOKEN] = token
        }
    }
}
