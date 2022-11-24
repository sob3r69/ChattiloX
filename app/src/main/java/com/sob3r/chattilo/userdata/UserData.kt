package com.sob3r.chattilo.userdata

import androidx.room.*

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "user_token") val userToken: String?
)