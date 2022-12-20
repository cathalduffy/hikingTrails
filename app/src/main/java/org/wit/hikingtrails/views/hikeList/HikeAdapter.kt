package org.wit.hikingtrails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.hikingtrails.databinding.CardHikeBinding
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel
import timber.log.Timber.i

interface HikeListener {
    fun onHikeClick(hike: HikeModel)
}

class HikeAdapter constructor(private var hikes: List<HikeModel>,
                              private val listener: HikeListener) :
    RecyclerView.Adapter<HikeAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardHikeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hike = hikes[holder.adapterPosition]
        holder.bind(hike, listener)
    }

    override fun getItemCount(): Int = hikes.size

    class MainHolder(private val binding : CardHikeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hike: HikeModel, listener: HikeListener) {
            binding.hikeName.text = hike.name
            binding.description.text = hike.description

            binding.favourite.isChecked = hike.favourite
            binding.favourite.setOnClickListener {
                hike.favourite = binding.favourite.isChecked
                listener.onHikeClick(hike)
            }



            if (hike.image != ""){
                Picasso.get()
                    .load(hike.image)
                    .resize(200, 200)
                    .into(binding.imageIcon)
            }
            binding.root.setOnClickListener { listener.onHikeClick(hike) }


        }
    }
}