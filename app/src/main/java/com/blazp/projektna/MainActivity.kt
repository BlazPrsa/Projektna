package com.blazp.projektna


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.blazp.projektna.components.CalculatorComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                TlakApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TlakApp() {
    val options = listOf("GVC1", "GVC2", "GVC3")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selection ->
                    DropdownMenuItem(onClick = {
                        selectedOption = selection
                        expanded = false
                    }) {
                        Text(text = selection)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TlakContent(option = selectedOption)
    }
}

@Composable
fun TlakContent(option: String) {

    CalculatorComponent(selectedValue = option)

    // 1st Row: Nastavi željeni tlak in št. napadov (več kak 3 ne)
    // Na podlagi tega izračunaj porabo 12mm ročnik (stran 172)
    // Shrani si pretok vode na C cevi in na B cevi
    // C cev pretok = št. barov na 12 mm ročniku po tabeli
    // B cev pretok = C cev pretok * št. napadov
    // Izračunaj čas do izpraznitve cisterne

    // 2nd Row: Vnesi število cevi v C cevovodu in B cevovodu
    // Na podlagi pretoka in dolžine cevi, izračunaj izgubo tlakov (182, 183)

    // 3rd Row: Vnesi število armatur (vsaka je 0,25 bara)
    // Vnesi višinsko razliko (1m je 0,1m)


}

@Preview(showBackground = true)
@Composable
fun PreviewTlakApp() {
    TlakApp()
}