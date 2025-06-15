package edu.ucne.registrosistema.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrosistema.presentation.sistemas.SistemaListScreen
import edu.ucne.registrosistema.presentation.sistemas.SistemaScreen

@Composable
fun HomeNavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.SistemaList
    ) {
        //pantalla lista de prioridades
        composable <Screen.SistemaList> {

            SistemaListScreen(
                goToSistema = { id ->
                    navHostController.navigate(Screen.Sistema(id ?: 0))
                },
                createSistema = {
                    navHostController.navigate(Screen.Sistema(0))
                }
            )
        }

        //pantalla formulario de prioridades
        composable <Screen.Sistema> { backStack ->
            val sistemaId = backStack.toRoute<Screen.Sistema>().sistemaId
            SistemaScreen(
                sistemaId = sistemaId,
                goBack = { navHostController.popBackStack() }
            )
        }
    }
}