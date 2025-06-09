package com.blazp.projektna.utils
import kotlin.math.ceil
import com.blazp.projektna.data.nozzleFlowTable
import com.blazp.projektna.data.BPressureLossTable
import com.blazp.projektna.data.CPressureLossTable


fun getNozzleFlow(pressure: Int): Int {
    return nozzleFlowTable[pressure] ?: 0
}

fun getPressureDropForB(numberOfBHoses: Int, waterFlow: Int): Double {
    val segmentLength = 30
    val roundedFlow = ceil(waterFlow / 200.0).toInt() * 200
    val pressureDropPerHose = BPressureLossTable[Pair(segmentLength, roundedFlow)] ?: 0.0
    return pressureDropPerHose * numberOfBHoses
}

fun getPressureDropForC(numberOfCHoses: Int, waterFlow: Int): Double {
    val segmentLength = 15
    val roundedFlow = ceil(waterFlow / 50.0).toInt() * 50  // Round up to the nearest 50 L/min
    val pressureDropPerHose = CPressureLossTable[Pair(segmentLength, roundedFlow)] ?: 0.0
    return pressureDropPerHose * numberOfCHoses
}