package edu.ucne.registrosistema.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrosistema.data.local.dao.SistemaDao
import edu.ucne.registrosistema.data.local.entities.SistemaEntity

@Database(
    entities = [
        SistemaEntity:: class
    ],
    version = 1,
    exportSchema = false
)

abstract class SistemaDb: RoomDatabase() {
    abstract fun SistemaDao(): SistemaDao
}