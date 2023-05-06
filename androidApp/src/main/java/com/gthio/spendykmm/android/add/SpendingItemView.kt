package com.gthio.spendykmm.android.add

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gthio.spendykmm.domain.Spending

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpendingItemView(
    modifier: Modifier = Modifier,
    spending: Spending,
) {
    val noteBuilder: @Composable (() -> Unit)? = spending.notes.isEmpty().let { empty ->
        if (empty) return@let null
        
        return@let { Text(text = spending.notes) }
    }

    ListItem(
        modifier = modifier,
        overlineText = { Text(text = spending.id.uuidString) },
        text = { Text(text = spending.amount.toString()) },
        secondaryText = noteBuilder,
    )
}