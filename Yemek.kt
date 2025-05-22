package com.example.yemeksiparis.data.model

data class Yemek(
    val yemek_id: Int,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: Int
)

data class YemeklerCevap(
    val yemekler: List<Yemek>,
    val success: Int
) 
