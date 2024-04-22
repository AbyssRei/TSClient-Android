package site.sayaz.ts3client.db

import androidx.room.Database
import androidx.room.RoomDatabase
import site.sayaz.ts3client.client.IdentityData
import site.sayaz.ts3client.client.IdentityDataDao
import site.sayaz.ts3client.client.LoginData
import site.sayaz.ts3client.client.LoginDataDao

@Database(entities = [LoginData::class, IdentityData::class], version = 1)
abstract class AppDB : RoomDatabase(){
    abstract fun loginDataDao(): LoginDataDao
    abstract fun identityDataDao(): IdentityDataDao
}