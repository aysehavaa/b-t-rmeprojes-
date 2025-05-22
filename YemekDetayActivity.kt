package com.example.yemeksiparis.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.yemeksiparis.data.model.Yemek
import com.example.yemeksiparis.databinding.ActivityYemekDetayBinding
import com.example.yemeksiparis.ui.viewmodel.AnaSayfaViewModel

class YemekDetayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYemekDetayBinding
    private lateinit var viewModel: AnaSayfaViewModel
    private var adet = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYemekDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[AnaSayfaViewModel::class.java]
        setupYemekDetaylari()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupYemekDetaylari() {
        val yemekId = intent.getIntExtra("yemek_id", 0)
        val yemekAdi = intent.getStringExtra("yemek_adi") ?: ""
        val yemekResimAdi = intent.getStringExtra("yemek_resim_adi") ?: ""
        val yemekFiyat = intent.getIntExtra("yemek_fiyat", 0)

        binding.textViewYemekAdi.text = yemekAdi
        binding.textViewYemekFiyat.text = "${yemekFiyat} ₺"
        binding.textViewAdet.text = adet.toString()

        Glide.with(this)
            .load("http://kasimadalan.pe.hu/yemekler/resimler/$yemekResimAdi")
            .into(binding.imageViewYemek)
    }

    private fun setupClickListeners() {
        binding.buttonArttir.setOnClickListener {
            adet++
            binding.textViewAdet.text = adet.toString()
        }

        binding.buttonAzalt.setOnClickListener {
            if (adet > 1) {
                adet--
                binding.textViewAdet.text = adet.toString()
            }
        }

        binding.buttonSepeteEkle.setOnClickListener {
            val yemek = Yemek(
                yemek_id = intent.getIntExtra("yemek_id", 0),
                yemek_adi = intent.getStringExtra("yemek_adi") ?: "",
                yemek_resim_adi = intent.getStringExtra("yemek_resim_adi") ?: "",
                yemek_fiyat = intent.getIntExtra("yemek_fiyat", 0)
            )
            viewModel.sepeteYemekEkle(yemek, adet)
        }
    }

    private fun observeViewModel() {
        viewModel.hataMesaji.observe(this) { hata ->
            Toast.makeText(this, hata, Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 
