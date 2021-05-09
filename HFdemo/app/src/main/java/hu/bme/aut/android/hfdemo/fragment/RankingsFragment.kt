package hu.bme.aut.android.hfdemo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.hfdemo.R
import hu.bme.aut.android.hfdemo.adapter.RankingAdapter
import hu.bme.aut.android.hfdemo.data.Points
import hu.bme.aut.android.hfdemo.databinding.FragmentRankingsBinding

class RankingsFragment : Fragment() {
    private lateinit var binding: FragmentRankingsBinding
    private lateinit var rankingAdapter: RankingAdapter

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRankingsBinding.inflate(layoutInflater, container, false)
        rankingAdapter = this.context?.let { RankingAdapter(it) }!!
        binding.root.findViewById<RecyclerView>(R.id.ranking_list).adapter = rankingAdapter
        getPoints()
        return binding.root
    }

    private fun getPoints() {
        RankingAdapter.rankingList = emptyList()
        val pointArray = mutableListOf<Points>()
        val db = Firebase.firestore
        db.collection("points")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val my_points = document.toObject<Points>()
                    pointArray.add(my_points)
                }
                pointArray.sortByDescending { it.points }
                for (element in pointArray){
                    rankingAdapter.addRanking(element)
                }
            }
            .addOnFailureListener { exception ->
                //hiba esetén
            }
    }

}
