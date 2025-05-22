package com.example.yemeksiparis.data.remote

import com.example.yemeksiparis.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface YemeklerDao {
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun tumYemekleriGetir(): Response<YemeklerCevap>

    @POST("yemekler/sepeteYemekEkle.php")
    suspend fun sepeteYemekEkle(
        @Field("yemek_adi") yemek_adi: String,
        @Field("yemek_resim_adi") yemek_resim_adi: String,
        @Field("yemek_fiyat") yemek_fiyat: Int,
        @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
        @Field("kullanici_adi") kullanici_adi: String
    ): Response<CRUDCevap>

    @POST("yemekler/sepettekiYemekleriGetir.php")
    suspend fun sepettekiYemekleriGetir(
        @Field("kullanici_adi") kullanici_adi: String
    ): Response<SepetYemeklerCevap>

    @POST("yemekler/sepettenYemekSil.php")
    suspend fun sepettenYemekSil(
        @Field("sepet_yemek_id") sepet_yemek_id: Int,
        @Field("kullanici_adi") kullanici_adi: String
    ): Response<CRUDCevap>
} 
