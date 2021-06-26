package hu.bme.aut.android.hfdemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.hfdemo.databinding.PlayerOverviewBinding
import hu.bme.aut.android.hfdemo.model.Player

class PlayerAdapter(private val context: Context) : ListAdapter<Player, PlayerAdapter.PlayerViewHolder>(itemCallback) {

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Player>() {
            override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var lastPosition = -1

    inner class PlayerViewHolder(binding: PlayerOverviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvName
        val tvAge: TextView = binding.tvAge
        val tvCountry: TextView = binding.tvCountry
        val tvImgPlayer: ImageView = binding.imgPlayer

        var player: Player? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlayerViewHolder(
            PlayerOverviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val tmpPlayer = this.getItem(position)
        holder.player = tmpPlayer
        holder.tvName.text = tmpPlayer.name
        holder.tvAge.text = "Age: ${tmpPlayer.age.toString()}"
        holder.tvCountry.text = "Country: ${tmpPlayer.nationality}"

        if (tmpPlayer.photo.isNullOrBlank()) {
            holder.tvImgPlayer.visibility = View.GONE
        } else {
            Glide.with(context).load(tmpPlayer.photo).into(holder.tvImgPlayer)
            holder.tvImgPlayer.visibility = View.VISIBLE
        }

        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}