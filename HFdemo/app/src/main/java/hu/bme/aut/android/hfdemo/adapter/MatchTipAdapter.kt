package hu.bme.aut.android.hfdemo.adapter

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
import hu.bme.aut.android.hfdemo.data.Match
import hu.bme.aut.android.hfdemo.databinding.MatchOverviewBinding


open class MatchTipAdapter(private val context: Context) :
    ListAdapter<Match, MatchTipAdapter.MatchTipViewHolder>(itemCallback) {

    var itemClickListener: MatchItemClickListener? = null

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Match>() {
            override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
                return oldItem.matchId == newItem.matchId
            }

            override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
                return oldItem == newItem
            }
        }

        var matchList = emptyList<Match>()
    }

    private var lastPosition = -1

    inner class MatchTipViewHolder(val binding: MatchOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvHomeTeam: TextView = binding.tvHomeTeam
        val tvAwayTeam: TextView = binding.tvAwayTeam
        val tvHomeGoals: TextView = binding.tvHomeGoals
        val tvAwayGoals: TextView = binding.tvAwayGoals
        val imgHome: ImageView = binding.imgHome
        val imgAway: ImageView = binding.imgAway
        val tvMatchDateHour: TextView = binding.tvMatchDateHour
        val tvMyTip: TextView = binding.tvMyTip

        var match: Match? = null

        init {
            itemView.setOnClickListener {
                match?.let { match -> itemClickListener?.onItemClick(match) }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MatchTipViewHolder(
            MatchOverviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MatchTipViewHolder, position: Int) {
        val tmpMatch = matchList[position]
        holder.match = tmpMatch
        holder.tvHomeTeam.text = tmpMatch.homeTeam
        holder.tvAwayTeam.text = tmpMatch.awayTeam
        holder.tvHomeGoals.text = tmpMatch.homeGoals?.toString()
        holder.tvAwayGoals.text = tmpMatch.awayGoals?.toString()
        holder.tvMatchDateHour.text = tmpMatch.matchDate
        holder.tvMyTip.text = tmpMatch.myTip

        if (tmpMatch.homeImageURL.isNullOrBlank()) {
            holder.imgHome.visibility = View.GONE
        } else {
            Glide.with(context).load(tmpMatch.homeImageURL).into(holder.imgHome)
            holder.imgHome.visibility = View.VISIBLE
        }

        if (tmpMatch.awayImageURL.isNullOrBlank()) {
            holder.imgAway.visibility = View.GONE
        } else {
            Glide.with(context).load(tmpMatch.awayImageURL).into(holder.imgAway)
            holder.imgAway.visibility = View.VISIBLE
        }

        setAnimation(holder.itemView, position)
    }

    fun addMatch(match: Match?) {
        match ?: return

        matchList += (match)
        submitList((matchList))
    }

    protected fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    interface MatchItemClickListener {
        fun onItemClick(match: Match)
        //fun onItemLongClick(position: Int, view: View): Boolean
    }
}