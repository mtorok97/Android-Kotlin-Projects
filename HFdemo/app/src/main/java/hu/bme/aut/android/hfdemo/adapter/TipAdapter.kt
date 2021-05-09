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
import hu.bme.aut.android.hfdemo.data.Tip
import hu.bme.aut.android.hfdemo.databinding.TipOverviewBinding


class TipAdapter(private val context: Context) :
    ListAdapter<Tip, TipAdapter.TipViewHolder>(itemCallback) {
    var itemClickListener: TipItemClickListener? = null

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Tip>() {
            override fun areItemsTheSame(oldItem: Tip, newItem: Tip): Boolean {
                return oldItem.matchID == newItem.matchID
            }

            override fun areContentsTheSame(oldItem: Tip, newItem: Tip): Boolean {
                return oldItem == newItem
            }
        }

        var tipList = emptyList<Tip>()
    }

    private var lastPosition = -1

    inner class TipViewHolder(binding: TipOverviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvAuthor: TextView = binding.tvAuthor
        val tvHomeTeam: TextView = binding.tvHomeTeam
        val tvAwayTeam: TextView = binding.tvAwayTeam
        val tvTip: TextView = binding.tvTip
        val tvPointCorrect: ImageView = binding.imgCorrect
        val tvPointWrong: ImageView = binding.imgWrong

        var tip: Tip? = null

        init {
            itemView.setOnClickListener {
                tip?.let { tip -> itemClickListener?.onItemClick(tip) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TipViewHolder(
            TipOverviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        val tmpTip = tipList[position]
        holder.tip = tmpTip
        holder.tvHomeTeam.text = tmpTip.homeTeam
        holder.tvAwayTeam.text = tmpTip.awayTeam
        holder.tvAuthor.text = "Username: ${tmpTip.author}"
        holder.tvTip.text = "TIP: ${tmpTip.tip}"
        if (tmpTip.points == 1) {
            holder.tvPointCorrect.visibility = View.VISIBLE
            holder.tvPointWrong.visibility = View.GONE
        } else {
            holder.tvPointCorrect.visibility = View.GONE
            holder.tvPointWrong.visibility = View.VISIBLE
        }

        setAnimation(holder.itemView, position)
    }

    fun addTip(tip: Tip?) {
        tip ?: return

        tipList += (tip)
        submitList(tipList)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    interface TipItemClickListener {
        fun onItemClick(tip: Tip)
        //fun onItemLongClick(position: Int, view: View): Boolean
    }
}
