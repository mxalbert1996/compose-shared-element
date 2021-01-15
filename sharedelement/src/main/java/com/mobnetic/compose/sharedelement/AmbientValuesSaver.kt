package com.mobnetic.compose.sharedelement

import androidx.compose.foundation.AmbientIndication
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableAmbient
import androidx.compose.runtime.Providers
import androidx.compose.ui.selection.AmbientTextSelectionColors

@Suppress("UNCHECKED_CAST")
private val ambientList = listOf(
    AmbientContentColor,
    AmbientContentAlpha,
    AmbientIndication,
    AmbientTextSelectionColors,
    AmbientTextStyle
) as List<ProvidableAmbient<Any>>

@Composable
internal fun saveAmbientValues(block: @Composable () -> Unit): @Composable () -> Unit {
    val ambientValues = ambientList.map { it provides it.current }.toTypedArray()
    return (@Composable {
        Providers(*ambientValues, content = block)
    })
}
