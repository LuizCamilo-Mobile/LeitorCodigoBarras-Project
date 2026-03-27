package br.com.tanalista.core.domain.model

import java.time.Instant

data class BarcodeScan(
    val value: BarcodeValue,
    val scannedAt: Instant
)