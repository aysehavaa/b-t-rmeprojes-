package com.example.yemeksiparis.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yemeksiparis.databinding.ActivitySepetBinding
import com.example.yemeksiparis.ui.adapter.SepetAdapter
import com.example.yemeksiparis.ui.viewmodel.AnaSayfaViewModel

class SepetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySepetBinding
    private lateinit var viewModel: AnaSayfaViewModel
    private lateinit var adapter: SepetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySepetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[AnaSayfaViewModel::class.java]
        setupRecyclerView()
        observeViewModel()

        viewModel.sepetiGuncelle()
    }

    private fun setupRecyclerView() {
        adapter = SepetAdapter(emptyList()) { sepetYemek ->
            viewModel.sepettenYemekSil(sepetYemek.sepet_yemek_id)
        }

        binding.recyclerViewSepet.apply {
            layoutManager = LinearLayoutManager(this@SepetActivity)
            adapter = this@SepetActivity.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.sepetYemeklerListesi.observe(this) { sepetYemekler ->
            if (sepetYemekler.isEmpty()) {
                binding.textViewSepetBos.visibility = View.VISIBLE
                binding.recyclerViewSepet.visibility = View.GONE
            } else {
                binding.textViewSepetBos.visibility = View.GONE
                binding.recyclerViewSepet.visibility = View.VISIBLE
                adapter = SepetAdapter(sepetYemekler) { sepetYemek ->
                    viewModel.sepettenYemekSil(sepetYemek.sepet_yemek_id)
                }
                binding.recyclerViewSepet.adapter = adapter
            }
        }

        viewModel.hataMesaji.observe(this) { hata ->
            Toast.makeText(this, hata, Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 
