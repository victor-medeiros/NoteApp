package com.victor.noteapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.victor.noteapp.features.note.data.data_source.NoteDatabase
import com.victor.noteapp.features.note.data.repository.NoteRepositoryImpl
import com.victor.noteapp.features.note.domain.repository.NoteRepository
import com.victor.noteapp.features.note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application): NoteDatabase {
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

    @Singleton
    @Provides
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNote = GetNoteUseCase(noteRepository),
            deleteNote = DeleteNoteUseCase(noteRepository),
            addNote = AddNoteUseCase(noteRepository),
            getNotes = GetNotesUseCase(noteRepository)
        )
    }
}