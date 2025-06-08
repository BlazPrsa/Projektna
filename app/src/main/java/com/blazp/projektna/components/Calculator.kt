package com.blazp.projektna.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    fun calculateTotalWaterConsumption() {
        nozzleFlow = getNozzleFlow(pressureValue.toIntOrNull() ?: 0)
        totalWaterConsumption = nozzleFlow * selectedNozzleCount.toInt()
        timeToEmpty = capacity / totalWaterConsumption
    }

    fun calculateTotalPressureDrop() {
        val bHosePressureDrop = getPressureDropForB(numberOfBHoses, totalWaterConsumption)
        val cHosePressureDrop = getPressureDropForC(numberOfCHoses, nozzleFlow)
        val armaturePressureDrop = numberOfArmatures * 0.25
        val heightDifferencePressureDrop = heightDifference * 0.1

        pressureNeeded = bHosePressureDrop + cHosePressureDrop +
                armaturePressureDrop + heightDifferencePressureDrop +
                (pressureValue.toIntOrNull() ?: 0)
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
                label = { Text("Tlak na ročniku") },
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
                        modifier = Modifier
                            .fillMaxWidth()
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
    }
}