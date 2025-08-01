package com.blazp.projektna.data

val nozzleFlowTable = mapOf(
    1 to 94,
    2 to 135,
    3 to 165,
    4 to 190,
    5 to 215,
    6 to 235,
    7 to 250,
    8 to 270,
    9 to 285,
    10 to 300,
    11 to 315,
    12 to 330,
    13 to 345,
    14 to 355,
    15 to 370,
    16 to 380,


)

val BPressureLossTable: Map<Pair<Int, Int>, Double> = mapOf(
    Pair(30, 200) to 0.03,
    Pair(30, 400) to 0.11,
    Pair(30, 600) to 0.24,
    Pair(30, 800) to 0.41

)

val CPressureLossTable: Map<Pair<Int, Int>, Double> = mapOf(
    Pair(15, 100) to 0.03,
    Pair(15, 150) to 0.06,
    Pair(15, 200) to 0.09,
    Pair(15, 250) to 0.14,
    Pair(15, 300) to 0.19,
    Pair(15, 350) to 0.25,
    Pair(15, 400) to 0.34,
    Pair(15, 450) to 0.41,
    Pair(15, 500) to 0.52
)