package com.gthio.spendykmm.android.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gthio.spendykmm.android.add.SpendingItemView
import com.gthio.spendykmm.domain.KmmUUID
import com.gthio.spendykmm.presenter.list.SpendingListEffect
import com.gthio.spendykmm.presenter.list.SpendingListState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpendingListRoute(
    toAdd: () -> Unit,
    toEdit: (KmmUUID) -> Unit,
    viewModel: SpendingListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isDialogVisible by viewModel.isDeleteConfirmationVisible.collectAsStateWithLifecycle()
    val sideEffect by viewModel.sideEffects.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(key1 = sideEffect) {
        sideEffect?.let { effect ->
            when (effect) {
                SpendingListEffect.NavigateToAdd -> toAdd()
                is SpendingListEffect.NavigateToEdit -> toEdit(effect.id)
            }
        }
    }

    if (isDialogVisible) {
        AlertDialog(
            onDismissRequest = { viewModel.onDeleteConfirmation(false) },
            buttons = {
                TextButton(onClick = { viewModel.onDeleteConfirmation(true) }) {
                    Text(text = "Delete")
                }
                TextButton(onClick = { viewModel.onDeleteConfirmation(false) }) {
                    Text(text = "Cancel")
                }
            },
            text = { Text(text = "Are you sure you want to delete the selected spending?") },
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Spending List") },
                actions = {
                    IconButton(onClick = viewModel::onAddTapped) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Spending")
                    }
                }
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()
                else -> SpendingListScreen(state = uiState)
            }
        }
    }
}

@Composable
fun SpendingListScreen(state: SpendingListState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(items = state.spendings) { spending ->
            SpendingItemView(
                modifier = Modifier.fillMaxWidth(),
                spending = spending,
            )
        }
    }
}