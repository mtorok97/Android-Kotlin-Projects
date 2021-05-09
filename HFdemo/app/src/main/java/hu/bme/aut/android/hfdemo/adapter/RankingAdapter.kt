package hu.bme.aut.android.hfdemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.hfdemo.data.Points
import hu.bme.aut.android.hfdemo.databinding.RankingOverviewBinding

class RankingAdapter(private val context: Context) :
    ListAdapter<Points, RankingAdapter.RankingViewHolder>(itemCallback) {
    var itemClickListener: TipItemClickListener? = null

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Points>() {
            override fun areItemsTheSame(oldItem: Points, newItem: Points): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Points, newItem: Points): Boolean {
                return oldItem == newItem
            }
        }

        var rankingList = emptyList<Points>()
    }

    private var lastPosition = -1

    inner class RankingViewHolder(binding: RankingOverviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvAuthor: TextView = binding.tvAuthor
        val tvRank: TextView = binding.tvRankingNumber
        val tvPoint: TextView = binding.tvPoint

        var ranking: Points? = null

        init {
            itemView.setOnClickListener {
                ranking?.let { ranking -> itemClickListener?.onItemClick(ranking) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RankingViewHolder(
            RankingOverviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val tmpRanking = rankingList[position]
        holder.ranking = tmpRanking
        holder.tvAuthor.text = tmpRanking.author
        holder.tvPoint.text = "Points: ${tmpRanking.points.toString()}"
        holder.tvRank.text = "${position+1}."

        setAnimation(holder.itemView, position)
    }

    fun addRanking(ranking: Points?) {
        ranking ?: return

        rankingList += (ranking)
        submitList(rankingList)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    interface TipItemClickListener {
        fun onItemClick(ranking: Points)
        //fun onItemLongClick(position: Int, view: View): Boolean
    }
}