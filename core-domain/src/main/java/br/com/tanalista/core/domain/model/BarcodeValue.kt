package br.com.tanalista.core.domain.model

@JvmInline
value class BarcodeValue(val raw: String) {
    init {
        val v = raw.trim()
        require(v.isNotEmpty()) { "Barcode cannot be empty" }
    }

    fun normalized(): String = raw.trim()
}