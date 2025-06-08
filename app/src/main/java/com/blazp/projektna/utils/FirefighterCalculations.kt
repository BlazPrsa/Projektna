package com.blazp.projektna.utils

import com.blazp.projektna.data.nozzleFlowTable

fun getNozzleFlow(pressure: Int): Int {
    return nozzleFlowTable[pressure] ?: 0
}

fun getPressureDropForB(numberOfBHoses: Int, waterFlow: Int): Int {
    // Tabela 182
    val hoseLength = 20 * numberOfBHoses

    return 0
}

fun getPressureDropForC(numberOfCHoses: Int, waterFlow: Int): Int {
    // Tabela 183
    val hoseLength = 15 * numberOfCHoses
    return 0
}