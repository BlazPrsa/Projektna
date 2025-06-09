package com.blazp.projektna.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ToggleDetailsComponent(
    bHosePressureDrop: Double,
    cHosePressureDrop: Double,
    nozzleFlow: Int,
    totalWaterConsumption: Int
) {
    var showDetails by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { showDetails = !showDetails },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(if (showDetails) "Skrij dodatne informacije" else "Dodatne informacije")
    }

    if (showDetails) {
        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Text(
                text = "Padci tlaka:",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "• B-cevi: ${"%.2f".format(bHosePressureDrop)} bar",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "• C-cevi: ${"%.2f".format(cHosePressureDrop)} bar",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Pretoki na ročnikih:",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "Trenutni pretok ročnika: $nozzleFlow l/min",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Skupni pretok ročnikov: $totalWaterConsumption l/min",
                style = MaterialTheme.typography.body1
            )
        }
    }
}
