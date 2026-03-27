package br.com.tanalista.core.data

import br.com.tanalista.core.domain.model.BarcodeScan
import br.com.tanalista.core.domain.repository.BarcodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class InMemoryBarcodeRepository : BarcodeRepository {

    private val history = MutableStateFlow<List<BarcodeScan>>(emptyList())

    override suspend fun save(scan: BarcodeScan) {
        history.update { current -> listOf(scan) + current }
    }

    override fun observeHistory(): Flow<List<BarcodeScan>> = history

    override suspend fun syncPending() = Unit // placeholder
}