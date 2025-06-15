package edu.ucne.registrosistema.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrosistema.data.local.database.SistemaDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideSistemaDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            SistemaDb::class.java,
            "Sistema.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSistemaDao(sistemaDb: SistemaDb) = sistemaDb.SistemaDao()

}