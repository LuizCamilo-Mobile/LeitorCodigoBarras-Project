package br.com.tanalista.ui

import br.com.tanalista.core.domain.model.BarcodeScan

data class MainUiState(
    val lastScannedValue: String = "",
    val history: List<BarcodeScan> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)