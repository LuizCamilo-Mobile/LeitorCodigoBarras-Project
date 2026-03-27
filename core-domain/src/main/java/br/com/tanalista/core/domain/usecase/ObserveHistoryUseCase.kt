package br.com.tanalista.core.domain.usecase

import br.com.tanalista.core.domain.model.BarcodeScan
import br.com.tanalista.core.domain.repository.BarcodeRepository
import kotlinx.coroutines.flow.Flow

class ObserveHistoryUseCase(
    private val repository: BarcodeRepository
) {
    operator fun invoke(): Flow<List<BarcodeScan>> = repository.observeHistory()
}