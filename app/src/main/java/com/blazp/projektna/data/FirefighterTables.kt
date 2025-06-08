package com.blazp.projektna.data

val nozzleFlowTable = mapOf(
    1 to 94,
    // Tabela 172
)

val BPressureLossTable: Map<Pair<Int, Int>, Double> = mapOf(
    Pair(15, 200) to 0.01
)

val CPressureLossTable: Map<Pair<Int, Int>, Double> = mapOf(
    Pair(15, 100) to 0.03
)