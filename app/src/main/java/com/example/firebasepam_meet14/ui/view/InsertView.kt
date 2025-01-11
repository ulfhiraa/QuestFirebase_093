package com.example.firebasepam_meet14.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasepam_meet14.ui.viewmodel.FormState
import com.example.firebasepam_meet14.ui.viewmodel.HomeUiState
import com.example.firebasepam_meet14.ui.viewmodel.InsertUiState
import com.example.firebasepam_meet14.ui.viewmodel.InsertViewModel
import com.example.firebasepam_meet14.ui.viewmodel.MahasiswaEvent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.Normalizer.Form

// fungsi  untuk menampilkan form input mahasiswa dengan validasi dan navigasi setelah berhasil
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun insertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState // state utama untuk loading, success, error
    val uiEvent = viewModel.uiEvent // state untuk form dan validasi
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // observasi perubahan state untuk snackbar dan navigasi
    LaunchedEffect(uiState) {
        when (uiState) {
            is FormState.Success -> {
                println("InsertMhsView: uiState is FormState.Success, navigate to home " + uiState.message)

                coroutineScope.launch() {
                    snackbarHostState.showSnackbar(uiState.message)
                } // tampilkan snackbar
                delay(700)
                onNavigate()// navigasi langsung
                viewModel.resetSnackBarMessage() // reset snackbar state
            }

            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }

            else -> Unit
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Tambah Mahasiswa") },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding()
                .padding(16.dp)
        ) {
            InsertBodyMhs(
                uiState = uiEvent,
                homeUiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    if (viewModel.validateFields()) {
                        viewModel.insertMhs()
                        //onNavigate()
                    }
                }
            )
        }
    }
}

//  untuk form input mahasiswa dengan validasi dan tombol untuk menambahkan data mahasiswa, termasuk status loading saat proses berlangsung.
@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: InsertUiState,
    onClick: () -> Unit,
    homeUiState: FormState
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMahasiswa(
            mahasiswaEvent = uiState.insertUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = homeUiState !is FormState.Loading
        ) {
            if (homeUiState is FormState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 8.dp)
                )
                Text("Loading...")
            } else {
                Text("Add")
            }
        }
    }
}


