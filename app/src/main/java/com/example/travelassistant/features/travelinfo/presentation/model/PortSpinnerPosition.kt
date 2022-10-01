package com.example.travelassistant.features.travelinfo.presentation.model

/**
 * Port spinner position - модель, содержащая позицию выбранного порта в списке
 *
 * @author Marianne Sabanchieva
 */

data class PortSpinnerPosition(
    val pos: Int = 0
) {
    fun copyPortPosition(pos: Int = this.pos): PortSpinnerPosition = PortSpinnerPosition(pos = pos)
}
