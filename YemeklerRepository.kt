package com.example.yemeksiparis.data.repository

import com.example.yemeksiparis.data.model.*
import com.example.yemeksiparis.data.remote.YemeklerDao
import retrofit2.Response

class YemeklerRepository(private val yemeklerDao: YemeklerDao) {
    suspend fun tumYemekleriGetir(): Response<YemeklerCevap> {
        return yemeklerDao.tumYemekleriGetir()
    }

    suspend fun sepeteYemekEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ): Response<CRUDCevap> {
        return yemeklerDao.sepeteYemekEkle(
            yemek_adi,
            yemek_resim_adi,
            yemek_fiyat,
            yemek_siparis_adet,
            kullanici_adi
        )
    }

    suspend fun sepettekiYemekleriGetir(kullanici_adi: String): Response<SepetYemeklerCevap> {
        return yemeklerDao.sepettekiYemekleriGetir(kullanici_adi)
    }

    suspend fun sepettenYemekSil(sepet_yemek_id: Int, kullanici_adi: String): Response<CRUDCevap> {
        return yemeklerDao.sepettenYemekSil(sepet_yemek_id, kullanici_adi)
    }
} 
