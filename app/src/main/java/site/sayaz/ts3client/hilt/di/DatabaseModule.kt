package site.sayaz.ts3client.hilt.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import site.sayaz.ts3client.db.AppDB
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDB {
        return Room.databaseBuilder(
            appContext,
            AppDB::class.java,
            "TS3db"
        ).build()
    }

    @Provides
    fun provideLoginDataDao(database:AppDB) = database.loginDataDao()

    @Provides
    fun provideIdentityDataDao(database:AppDB) = database.identityDataDao()


}