package edu.ucne.registrosistema.presentation.sistemas

import edu.ucne.registrosistema.data.local.entities.SistemaEntity

data class SistemaUiState (
    val sistemaId: Int? = null,
    val descripcion: String = "",
    val errorMessage: String? = null,
    val sistemas: List<SistemaEntity> = emptyList(),
)