package com.whatziya.todoapplication.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.whatziya.todoapplication.data.database.MainDatabase
import com.whatziya.todoapplication.data.database.dao.DeletedDao
import com.whatziya.todoapplication.data.database.dao.EditRemoteDao
import com.whatziya.todoapplication.data.database.dao.MainDao
import com.whatziya.todoapplication.preferences.PreferencesProvider
import com.whatziya.todoapplication.utils.Constants
import com.whatziya.todoapplication.utils.Constants.FileName.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return try {
            val masterKeyAlias =
                MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
            EncryptedSharedPreferences.create(
                context,
                Constants.FileName.SHARED_PREFS,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (ignore: Exception) {
            context.getSharedPreferences(Constants.FileName.SHARED_PREFS, Context.MODE_PRIVATE)
        }
    }

    @Singleton
    @Provides
    fun providePreferencesProvider(sharedPreferences: SharedPreferences) =
        PreferencesProvider(sharedPreferences)

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMainDao(roomDatabase: MainDatabase): MainDao {
        return roomDatabase.mainDao()
    }

    @Provides
    fun provideEditRemoteDao(roomDatabase: MainDatabase): EditRemoteDao {
        return roomDatabase.editRemoteDao()
    }

    @Provides
    fun provideDeletedDao(roomDatabase: MainDatabase): DeletedDao {
        return roomDatabase.deletedDao()
    }
}
