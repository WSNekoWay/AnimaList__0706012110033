package Adapter

import Database.GlobalVar
import Interface.cardListener
import Model.Animal
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toDrawable
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.animallist.R
import com.example.animallist.databinding.CardAnimalBinding
import com.google.android.material.snackbar.Snackbar

class ListDataRVAdapter (val ListAnimal: ArrayList<Animal>, val cardListener: cardListener):RecyclerView.Adapter<ListDataRVAdapter.viewHolder>() {
    class viewHolder (val itemView: View, val cardListeners: cardListener): RecyclerView.ViewHolder(itemView){
        val binding = CardAnimalBinding.bind(itemView)
        fun setData(data: Animal){
            binding.textNama.text = data.nama
            binding.textHewan.text = data.jenis
            binding.textUsia.text = data.usia

            if (data.imageUri!!.isNotEmpty()) {
                binding.imageView.setImageURI(Uri.parse(data.imageUri))
            }else{
                binding.imageView.setImageResource(R.drawable.squid)
            }

            binding.editButton.setOnClickListener {
                cardListeners.onCardClick("edit",position)
            }

            binding.deleteButton.setOnClickListener {
                cardListeners.onCardClick("delete",position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_animal, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(ListAnimal[position])
    }

    override fun getItemCount(): Int {
        return ListAnimal.size
    }
}