package com.example.travelassistant.core

/**
 * Or default - устанавливает значение по умолчанию
 *
 * @author Marianne Sabanchieva
 */

fun Int?.orDefault(value: Int = -1) = this ?: value
fun Long?.orDefault(value: Long = -1) = this ?: value