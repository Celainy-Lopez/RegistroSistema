package edu.ucne.registrosistema.presentation.sistemas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrosistema.data.local.entities.SistemaEntity
import edu.ucne.registrosistema.data.repository.SistemasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemasRepository: SistemasRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SistemaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSistemas()
    }


    fun onEvent(event: SistemaEvent) {
        when (event) {
            is SistemaEvent.SistemaChange -> onSistemaIdChange(event.sistemaId)
            is SistemaEvent.DescripcionChange -> onDescripcionChange(event.descripcion)

            SistemaEvent.Save -> viewModelScope.launch { saveSistema() }
            SistemaEvent.Delete -> deleteSistema()
            SistemaEvent.New -> nuevo()
        }
    }


    suspend fun saveSistema(): Boolean {
        return if (_uiState.value.descripcion.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Campos vacios")
            }
            false
        } else {
            sistemasRepository.save(_uiState.value.toEntity())
            true
        }
    }

    private fun nuevo() {
        _uiState.update {
            it.copy(
                sistemaId = null,
                descripcion = "",
                errorMessage = null
            )
        }
    }

    fun findSistema(sistemaId: Int) {
        viewModelScope.launch {
            if (sistemaId > 0) {
                val sistema = sistemasRepository.find(sistemaId)
                _uiState.update {
                    it.copy(
                        sistemaId = sistema?.sistemaId,
                        descripcion = sistema?.descripcion ?: ""
                    )
                }
            }
        }
    }

    fun deleteSistema() {
        viewModelScope.launch {
            sistemasRepository.delete(_uiState.value.toEntity())
        }
    }

    val sistemas: StateFlow<List<SistemaEntity>> = sistemasRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = emptyList()
        )

    private fun getSistemas() {
        viewModelScope.launch {
            sistemasRepository.getAll().collect { sistemas ->
                _uiState.update {
                    it.copy(
                        sistemas = sistemas
                    )
                }
            }
        }
    }


    private fun onSistemaIdChange(sistemaId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    sistemaId = sistemaId
                )
            }
        }
    }

    private fun onDescripcionChange(descripcion: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    descripcion = descripcion
                )
            }
        }
    }


    fun SistemaUiState.toEntity() = SistemaEntity(
        sistemaId = sistemaId,
        descripcion = descripcion ?: ""
    )
}