package edu.ucne.registrosistema.data.repository

import edu.ucne.registrosistema.data.local.dao.SistemaDao
import edu.ucne.registrosistema.data.local.entities.SistemaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SistemasRepository @Inject constructor(
    private val dao: SistemaDao
) {
    suspend fun save(sistema: SistemaEntity) = dao.save(sistema)

    suspend fun find(id: Int): SistemaEntity? = dao.find(id)

    suspend fun delete(sistema: SistemaEntity) = dao.delete(sistema)

    fun getAll(): Flow<List<SistemaEntity>> = dao.getAll()
}