package edu.ucne.registrosistema.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen{

    @Serializable
    data object SistemaList : Screen()

    @Serializable
    data class Sistema(val sistemaId: Int?) : Screen()

}