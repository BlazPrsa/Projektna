package com.blazp.projektna.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blazp.projektna.utils.getNozzleFlow
import com.blazp.projektna.utils.getPressureDropForB
import com.blazp.projektna.utils.getPressureDropForC
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalculatorComponent(selectedValue: String) {
    val (maxFlow, capacity) = when (selectedValue) {
        "GVC1" -> 1600 to 2500
        "GVC2" -> 2400 to 5000
        "GVC3" -> 1600 to 7000
        else -> 0 to 0
    }

    var pressureValue by remember { mutableStateOf("") }
    val nozzleOptions = listOf("1", "2", "3")
    var selectedNozzleCount by remember { mutableStateOf(nozzleOptions[0]) }
    var nozzleExpanded by remember { mutableStateOf(false) }

    var totalWaterConsumption by remember { mutableIntStateOf(0) }
    var nozzleFlow by remember { mutableIntStateOf(0) }
    var timeToEmpty by remember { mutableIntStateOf(0) }

    var numberOfBHoses by remember { mutableIntStateOf(0) }
    var numberOfCHoses by remember { mutableIntStateOf(0) }
    var numberOfArmatures by remember { mutableIntStateOf(0) }
    var heightDifference by remember { mutableIntStateOf(0) }
    var pressureNeeded by remember { mutableDoubleStateOf(0.0) }
    var bHosePressureDrop by remember { mutableDoubleStateOf(0.0) }
    var cHosePressureDrop by remember { mutableDoubleStateOf(0.0) }

    fun calculateTotalWaterConsumption() {
        nozzleFlow = getNozzleFlow(pressureValue.toIntOrNull() ?: 0)
        totalWaterConsumption = nozzleFlow * selectedNozzleCount.toInt()

        timeToEmpty = if (totalWaterConsumption != 0) {
            capacity / totalWaterConsumption
        } else {
            0
        }
    }

    fun calculateTotalPressureDrop() {
        bHosePressureDrop = getPressureDropForB(numberOfBHoses, totalWaterConsumption).toDouble()
        cHosePressureDrop = getPressureDropForC(numberOfCHoses, nozzleFlow).toDouble()
        val armaturePressureDrop = numberOfArmatures * 0.25
        val heightDifferencePressureDrop = heightDifference * 0.1

        pressureNeeded = bHosePressureDrop + cHosePressureDrop +
                armaturePressureDrop + heightDifferencePressureDrop +
                (pressureValue.toIntOrNull() ?: 0)
    }

    LaunchedEffect(pressureValue, selectedNozzleCount, capacity) {
        calculateTotalWaterConsumption()
    }

    LaunchedEffect(
        totalWaterConsumption,
        numberOfBHoses,
        numberOfCHoses,
        nozzleFlow,
        numberOfArmatures,
        heightDifference,
        pressureValue
    ) {
        calculateTotalPressureDrop()
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Izbrano vozilo: $selectedValue", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Maksimalni pretok: $maxFlow l/min")
        Text("Kapaciteta vode: $capacity l")

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = pressureValue,
                onValueChange = {
                    if (it.all { char -> char.isDigit() } && (it.toIntOrNull() ?: 0) <= 16)
                        pressureValue = it
                },
                label = { Text("Tlak na ročniku (bar)") },
                modifier = Modifier.weight(1f)
            )

            Box(modifier = Modifier.weight(1f)) {
                ExposedDropdownMenuBox(
                    expanded = nozzleExpanded,
                    onExpandedChange = { nozzleExpanded = !nozzleExpanded },
                ) {
                    OutlinedTextField(
                        value = selectedNozzleCount,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Št. ročnikov") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = nozzleExpanded,
                        onDismissRequest = { nozzleExpanded = false }
                    ) {
                        nozzleOptions.forEach { option ->
                            DropdownMenuItem(onClick = {
                                selectedNozzleCount = option
                                nozzleExpanded = false
                            }) {
                                Text(option)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(
                value = numberOfBHoses.toString(),
                onValueChange = { numberOfBHoses = it.toIntOrNull() ?: 0 },
                label = { Text("Št. B-cevi (30m)") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = numberOfCHoses.toString(),
                onValueChange = { numberOfCHoses = it.toIntOrNull() ?: 0 },
                label = { Text("Št. C-cevi (15m)") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(
                value = numberOfArmatures.toString(),
                onValueChange = { numberOfArmatures = it.toIntOrNull() ?: 0 },
                label = { Text("Št. armatur") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = heightDifference.toString(),
                onValueChange = { heightDifference = it.toIntOrNull() ?: 0 },
                label = { Text("Višinska razlika (m)") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "Čas do izpraznitve cisterne brez napajanja: $timeToEmpty min",
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Potrebni tlak na črpalki: ${"%.2f".format(pressureNeeded)} bar",
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(8.dp))

        ToggleDetailsComponent(
            bHosePressureDrop = bHosePressureDrop,
            cHosePressureDrop = cHosePressureDrop,
            nozzleFlow = nozzleFlow,
            totalWaterConsumption = totalWaterConsumption
        )
    }
}