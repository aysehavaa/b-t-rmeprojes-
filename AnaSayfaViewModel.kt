package com.example.yemeksiparis.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemeksiparis.data.model.*
import com.example.yemeksiparis.data.repository.YemeklerRepository
import kotlinx.coroutines.launch

class AnaSayfaViewModel(private val repository: YemeklerRepository) : ViewModel() {
    private val _yemeklerListesi = MutableLiveData<List<Yemek>>()
    val yemeklerListesi: LiveData<List<Yemek>> = _yemeklerListesi

    private val _sepetYemeklerListesi = MutableLiveData<List<SepetYemek>>()
    val sepetYemeklerListesi: LiveData<List<SepetYemek>> = _sepetYemeklerListesi

    private val _hataMesaji = MutableLiveData<String>()
    val hataMesaji: LiveData<String> = _hataMesaji

    fun yemekleriYukle() {
        viewModelScope.launch {
            try {
                val response = repository.tumYemekleriGetir()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _yemeklerListesi.value = it.yemekler
                    }
                } else {
                    _hataMesaji.value = "Yemekler yüklenirken bir hata oluştu"
                }
            } catch (e: Exception) {
                _hataMesaji.value = "Bağlantı hatası: ${e.message}"
            }
        }
    }

    fun sepeteYemekEkle(yemek: Yemek, adet: Int) {
        viewModelScope.launch {
            try {
                val response = repository.sepeteYemekEkle(
                    yemek.yemek_adi,
                    yemek.yemek_resim_adi,
                    yemek.yemek_fiyat,
                    adet,
                    "ayse_kullanici"
                )
                if (response.isSuccessful) {
                    sepetiGuncelle()
                } else {
                    _hataMesaji.value = "Yemek sepete eklenirken bir hata oluştu"
                }
            } catch (e: Exception) {
                _hataMesaji.value = "Bağlantı hatası: ${e.message}"
            }
        }
    }

    fun sepetiGuncelle() {
        viewModelScope.launch {
            try {
                val response = repository.sepettekiYemekleriGetir("ayse_kullanici")
                if (response.isSuccessful) {
                    response.body()?.let {
                        _sepetYemeklerListesi.value = it.sepet_yemekler
                    }
                } else {
                    _hataMesaji.value = "Sepet güncellenirken bir hata oluştu"
                }
            } catch (e: Exception) {
                _hataMesaji.value = "Bağlantı hatası: ${e.message}"
            }
        }
    }

    fun sepettenYemekSil(sepet_yemek_id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.sepettenYemekSil(sepet_yemek_id, "ayse_kullanici")
                if (response.isSuccessful) {
                    sepetiGuncelle()
                } else {
                    _hataMesaji.value = "Yemek sepetten silinirken bir hata oluştu"
                }
            } catch (e: Exception) {
                _hataMesaji.value = "Bağlantı hatası: ${e.message}"
            }
        }
    }
} 
