package com.example.yemeksiparis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yemeksiparis.data.model.SepetYemek
import com.example.yemeksiparis.databinding.ItemSepetBinding

class SepetAdapter(
    private val sepetYemeklerListesi: List<SepetYemek>,
    private val onDeleteClick: (SepetYemek) -> Unit
) : RecyclerView.Adapter<SepetAdapter.SepetViewHolder>() {

    class SepetViewHolder(val binding: ItemSepetBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetViewHolder {
        val binding = ItemSepetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SepetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SepetViewHolder, position: Int) {
        val sepetYemek = sepetYemeklerListesi[position]
        holder.binding.apply {
            textViewYemekAdi.text = sepetYemek.yemek_adi
            textViewYemekFiyat.text = "${sepetYemek.yemek_fiyat} ₺"
            textViewAdet.text = "Adet: ${sepetYemek.yemek_siparis_adet}"
            
            Glide.with(imageViewYemek)
                .load("http://kasimadalan.pe.hu/yemekler/resimler/${sepetYemek.yemek_resim_adi}")
                .into(imageViewYemek)

            buttonSil.setOnClickListener {
                onDeleteClick(sepetYemek)
            }
        }
    }

    override fun getItemCount() = sepetYemeklerListesi.size
} 
