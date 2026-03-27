package br.com.tanalista.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tanalista.core.domain.model.BarcodeScan
import br.com.tanalista.core.domain.model.BarcodeValue
import br.com.tanalista.core.domain.usecase.ObserveHistoryUseCase
import br.com.tanalista.core.domain.usecase.SaveBarcodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant

class MainViewModel(
    private val saveBarcodeUseCase: SaveBarcodeUseCase,
    private val observeHistoryUseCase: ObserveHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        observeHistory()
    }

    private fun observeHistory() {
        viewModelScope.launch {
            observeHistoryUseCase()
                .catch { error ->
                    _uiState.update {
                        it.copy(errorMessage = error.message ?: "Erro ao carregar histórico")
                    }
                }
                .collect { history ->
                    _uiState.update {
                        it.copy(history = history)
                    }
                }
        }
    }

    fun mockScan(barcodeRaw: String) {
        viewModelScope.launch {
            runCatching {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }

                val scan = BarcodeScan(
                    value = BarcodeValue(barcodeRaw),
                    scannedAt = Instant.now()
                )

                saveBarcodeUseCase(scan)

                _uiState.update {
                    it.copy(
                        lastScannedValue = scan.value.normalized(),
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Erro ao salvar leitura"
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}