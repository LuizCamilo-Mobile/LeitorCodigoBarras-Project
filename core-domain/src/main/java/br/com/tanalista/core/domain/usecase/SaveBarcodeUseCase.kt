package br.com.tanalista.core.domain.usecase

import br.com.tanalista.core.domain.model.BarcodeScan
import br.com.tanalista.core.domain.repository.BarcodeRepository

class SaveBarcodeUseCase(
    private val repository: BarcodeRepository
) {
    suspend operator fun invoke(scan: BarcodeScan) {
        repository.save(scan)
    }
}