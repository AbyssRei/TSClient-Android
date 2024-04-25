package site.sayaz.ts3client.db

import androidx.room.Database
import androidx.room.RoomDatabase
import site.sayaz.ts3client.client.IdentityData
import site.sayaz.ts3client.client.IdentityDataDao
import site.sayaz.ts3client.client.ServerData
import site.sayaz.ts3client.client.ServerDataDao

@Database(entities = [ServerData::class, IdentityData::class], version = 1)
abstract class AppDB : RoomDatabase(){
    abstract fun loginDataDao(): ServerDataDao
    abstract fun identityDataDao(): IdentityDataDao
}