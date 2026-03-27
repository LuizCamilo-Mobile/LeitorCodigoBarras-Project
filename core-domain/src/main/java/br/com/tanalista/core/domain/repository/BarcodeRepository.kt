package br.com.tanalista.core.domain.repository

import br.com.tanalista.core.domain.model.BarcodeScan
import kotlinx.coroutines.flow.Flow

interface BarcodeRepository {
    suspend fun save(scan: BarcodeScan)
    fun observeHistory(): Flow<List<BarcodeScan>>
    suspend fun syncPending() // placeholder para o futuro (Firebase)
}