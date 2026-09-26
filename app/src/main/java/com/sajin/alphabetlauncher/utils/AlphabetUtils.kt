package com.sajin.alphabetlauncher.utils

fun indexFromY(
    y: Float,
    height: Float
): Int {

    val top = 70f
    val bottom = height - 70f

    val usableHeight =
        (bottom - top).coerceAtLeast(1f)

    val position =
        ((y - top) / usableHeight)
            .coerceIn(0f, 0.9999f)

    return (position * 26)
        .toInt()
}

fun letterFromIndex(index: Int): Char {

    return ('A'.code + index.coerceIn(0, 25))
        .toChar()
}