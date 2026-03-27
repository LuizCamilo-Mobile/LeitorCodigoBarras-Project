package br.com.tanalista

import br.com.tanalista.core.data.InMemoryBarcodeRepository
import br.com.tanalista.core.domain.repository.BarcodeRepository
import br.com.tanalista.core.domain.usecase.ObserveHistoryUseCase
import br.com.tanalista.core.domain.usecase.SaveBarcodeUseCase
import br.com.tanalista.core.domain.usecase.SyncPendingUseCase

class AppContainer {
    private val repository: BarcodeRepository = InMemoryBarcodeRepository()

    val saveBarcodeUseCase = SaveBarcodeUseCase(repository)
    val observeHistoryUseCase = ObserveHistoryUseCase(repository)
    val syncPendingUseCase = SyncPendingUseCase(repository)
}