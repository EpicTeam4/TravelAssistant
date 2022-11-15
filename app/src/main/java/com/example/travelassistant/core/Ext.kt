package com.example.travelassistant.core

/**
 * Or default - устанавливает значение по умолчанию
 *
 * @author Marianne Sabanchieva
 */

fun Int?.orDefault(value: Int = -1) = this ?: value