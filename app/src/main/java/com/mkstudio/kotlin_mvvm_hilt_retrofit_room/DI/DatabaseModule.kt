package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DI

import android.content.Context
import androidx.room.Room
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.BookDao
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.BookDatabase
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.BookDatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): BookDatabase {
        return Room.databaseBuilder(
            appContext,
            BookDatabase::class.java,
            "book-database.db"
        ).allowMainThreadQueries().build()
    }
}

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
@Module
object TestDatabaseModule {
    @Provides
    @Singleton
    fun provideTestDatabase(@ApplicationContext appContext: Context): BookDatabase {
        return Room.inMemoryDatabaseBuilder(
            appContext,
            BookDatabase::class.java,
        ).allowMainThreadQueries().build()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object DatabaseAccessModule {
    @Singleton
    @Provides
    fun provideBookDao(database: BookDatabase): BookDao {
        return database.bookDAO()
    }

    @Singleton
    @Provides
    fun provideDatabaseServicec(bookDao: BookDao): BookDatabaseService {
        return BookDatabaseService(bookDao)
    }
}
