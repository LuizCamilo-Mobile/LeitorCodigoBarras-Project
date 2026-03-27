package br.com.tanalista.core.domain.usecase

import br.com.tanalista.core.domain.repository.BarcodeRepository

class SyncPendingUseCase(
    private val repository: BarcodeRepository
) {
    suspend operator fun invoke() {
        repository.syncPending()
    }
}