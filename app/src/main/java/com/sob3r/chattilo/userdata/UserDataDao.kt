package com.sob3r.chattilo.userdata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDataDao {
    @Query("SELECT * FROM userdata")
    suspend fun getAll(): List<UserData>

    @Query("SELECT user_token FROM userdata")
    suspend fun getTokenFromDb(): String

    @Insert(entity = UserData::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg userData: UserData)

    @Query("UPDATE UserData SET user_token = :newToken WHERE uid = 1")
    suspend fun updateToken(newToken: String)

    @Delete(entity = UserData::class)
    suspend fun delete(userData: UserData)
}