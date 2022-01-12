package com.victor.noteapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.victor.noteapp.features.note.data.data_source.NoteDatabase
import com.victor.noteapp.features.note.data.repository.NoteRepositoryImpl
import com.victor.noteapp.features.note.domain.repository.NoteRepository
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application): RoomDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }
}