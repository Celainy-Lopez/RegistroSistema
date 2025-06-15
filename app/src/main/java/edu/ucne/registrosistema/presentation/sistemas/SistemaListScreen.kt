package edu.ucne.registrosistema.presentation.sistemas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrosistema.data.local.entities.SistemaEntity

@Composable
fun SistemaListScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    goToSistema: (Int) -> Unit,
    createSistema: () -> Unit,
    deleteSistema: ((SistemaEntity) -> Unit)? = null
){
    val  uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaListBodyScreen(
        uiState = uiState,
        goToSistema = goToSistema,
        createSistema = createSistema,
        deleteSistema = { sistema ->
            viewModel.onEvent(SistemaEvent.SistemaChange(sistema.sistemaId?:0))
            viewModel.onEvent(SistemaEvent.Delete)

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SistemaListBodyScreen(
    uiState: SistemaUiState,
    goToSistema: (Int) -> Unit,
    createSistema: () -> Unit,
    deleteSistema: (SistemaEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de sistemas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = createSistema) {
                Icon(Icons.Filled.Add, "Agregar nueva")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.weight(1f), text= "ID")
                Text(modifier = Modifier.weight(1f), text = "Descripcion")
                Text(modifier = Modifier.weight(1f), text = "Acciones")

            }

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.sistemas) {sistema ->
                    SistemaRow(
                        it = sistema,
                        goToSistema = {goToSistema(sistema.sistemaId?: 0)},
                        deleteSistema = deleteSistema
                    )
                }
            }
        }
    }
}

@Composable
private fun SistemaRow(
    it: SistemaEntity,
    goToSistema: () -> Unit,
    deleteSistema: (SistemaEntity) ->Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = cardElevation(defaultElevation = 6.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(modifier = Modifier.weight(1f), text = it.sistemaId.toString())
            Text(modifier = Modifier.weight(1f), text = it.descripcion)

            Row(modifier = Modifier.weight(1f)){
                IconButton(onClick = goToSistema) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { deleteSistema(it) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
        HorizontalDivider()
    }

}