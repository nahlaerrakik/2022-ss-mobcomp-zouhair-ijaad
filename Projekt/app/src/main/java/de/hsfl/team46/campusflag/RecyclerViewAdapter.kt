package de.hsfl.team46.campusflag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.hsfl.team46.campusflag.databinding.RecyclerPlayersBinding


class RecyclerViewAdapter(private val data : List<Player>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>()  {


    inner class MyViewHolder(val binding: RecyclerPlayersBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Player){
            binding.listItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listPlayersBinding = RecyclerPlayersBinding.inflate(inflater, parent, false)
        return MyViewHolder(listPlayersBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}