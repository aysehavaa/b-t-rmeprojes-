package com.example.yemeksiparis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yemeksiparis.data.model.Yemek
import com.example.yemeksiparis.databinding.ItemYemekBinding

class YemeklerAdapter(
    private val yemeklerListesi: List<Yemek>,
    private val onItemClick: (Yemek) -> Unit
) : RecyclerView.Adapter<YemeklerAdapter.YemekViewHolder>() {

    class YemekViewHolder(val binding: ItemYemekBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekViewHolder {
        val binding = ItemYemekBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return YemekViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YemekViewHolder, position: Int) {
        val yemek = yemeklerListesi[position]
        holder.binding.apply {
            textViewYemekAdi.text = yemek.yemek_adi
            textViewYemekFiyat.text = "${yemek.yemek_fiyat} ₺"
            
            Glide.with(imageViewYemek)
                .load("http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}")
                .into(imageViewYemek)

            cardViewYemek.setOnClickListener {
                onItemClick(yemek)
            }
        }
    }

    override fun getItemCount() = yemeklerListesi.size
} 
