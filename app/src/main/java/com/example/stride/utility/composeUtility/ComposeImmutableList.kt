package com.example.stride.utility.composeUtility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import java.util.Collections

@Immutable
data class ComposeImmutableList<E> private constructor(
    private val baseList: List<E>
) : List<E> by baseList {
    companion object {
        fun <E> from(baseList: List<E>): ComposeImmutableList<E> {
            return ComposeImmutableList(Collections.unmodifiableList(ArrayList(baseList)))
        }
    }
}

fun <E> Iterable<E>.toComposeImmutableList(): ComposeImmutableList<E> {
    val list = if (this is List<E>) this else toList()
    return ComposeImmutableList.from(list)
}

fun <E> composeImmutableListOf(vararg items: E): ComposeImmutableList<E> =
    items.toList().toComposeImmutableList()

@Composable
inline fun <E> rememberComposeImmutableList(
    crossinline baseList: @DisallowComposableCalls () -> Iterable<E>
): State<ComposeImmutableList<E>> {
    return remember { derivedStateOf { baseList().toComposeImmutableList() } }
}
