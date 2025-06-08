package com.blazp.projektna.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownSwitcher() {
    val options = listOf("GVC1", "GVC2", "GVC3")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(modifier = Modifier.padding(16.dp)) {
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
    }
}

@Composable fun GVC1Content() = Text("You selected GVC1")
@Composable fun GVC2Content() = Text("You selected GVC2")
@Composable fun GVC3Content() = Text("You selected GVC3")

@Preview(showBackground = true)
@Composable
fun DropdownSwitcherPreview() {
    DropdownSwitcher()
}