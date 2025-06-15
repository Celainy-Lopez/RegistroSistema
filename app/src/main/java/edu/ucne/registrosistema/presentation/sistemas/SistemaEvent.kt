package edu.ucne.registrosistema.presentation.sistemas

interface SistemaEvent {

    data class SistemaChange(val sistemaId: Int): SistemaEvent
    data class  DescripcionChange(val descripcion: String): SistemaEvent

    data object Save: SistemaEvent
    data object Delete: SistemaEvent
    data object New: SistemaEvent
}