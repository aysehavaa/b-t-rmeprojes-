package com.example.yemeksiparis.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yemeksiparis.databinding.ActivityMainBinding
import com.example.yemeksiparis.ui.adapter.YemeklerAdapter
import com.example.yemeksiparis.ui.viewmodel.AnaSayfaViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AnaSayfaViewModel
    private lateinit var adapter: YemeklerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[AnaSayfaViewModel::class.java]
        setupRecyclerView()
        observeViewModel()
        setupClickListeners()

        viewModel.yemekleriYukle()
    }

    private fun setupRecyclerView() {
        adapter = YemeklerAdapter(emptyList()) { yemek ->
            val intent = Intent(this, YemekDetayActivity::class.java).apply {
                putExtra("yemek_id", yemek.yemek_id)
                putExtra("yemek_adi", yemek.yemek_adi)
                putExtra("yemek_resim_adi", yemek.yemek_resim_adi)
                putExtra("yemek_fiyat", yemek.yemek_fiyat)
            }
            startActivity(intent)
        }

        binding.recyclerViewYemekler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.yemeklerListesi.observe(this) { yemekler ->
            adapter = YemeklerAdapter(yemekler) { yemek ->
                val intent = Intent(this, YemekDetayActivity::class.java).apply {
                    putExtra("yemek_id", yemek.yemek_id)
                    putExtra("yemek_adi", yemek.yemek_adi)
                    putExtra("yemek_resim_adi", yemek.yemek_resim_adi)
                    putExtra("yemek_fiyat", yemek.yemek_fiyat)
                }
                startActivity(intent)
            }
            binding.recyclerViewYemekler.adapter = adapter
        }

        viewModel.hataMesaji.observe(this) { hata ->
            Toast.makeText(this, hata, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupClickListeners() {
        binding.fabSepet.setOnClickListener {
            startActivity(Intent(this, SepetActivity::class.java))
        }
    }
} 
