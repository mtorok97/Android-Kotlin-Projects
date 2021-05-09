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
import hu.bme.aut.android.hfdemo.TipViewActivity
import hu.bme.aut.android.hfdemo.adapter.MatchAdapter
import hu.bme.aut.android.hfdemo.adapter.TipAdapter
import hu.bme.aut.android.hfdemo.data.Points
import hu.bme.aut.android.hfdemo.data.Tip
import hu.bme.aut.android.hfdemo.databinding.FragmentMytipsBinding

class MytipsFragment : Fragment() {
    private lateinit var binding: FragmentMytipsBinding
    private lateinit var tipAdapter: TipAdapter

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMytipsBinding.inflate(layoutInflater, container, false)
        tipAdapter = this.context?.let { TipAdapter(it) }!!
        binding.root.findViewById<RecyclerView>(R.id.tip_list).adapter = tipAdapter
        getPoints()
        return binding.root
    }

    private fun getPoints() {
        TipAdapter.tipList = emptyList()
        var points = 0
        val db = Firebase.firestore
        db.collection("spanish_tips")
            //.document("${(this.activity as TipViewActivity).userName}")
            .whereEqualTo("author", "${(this.activity as TipViewActivity).userName}")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val my_tip = document.toObject<Tip>()
                    tipAdapter.addTip(my_tip)
                    if (my_tip.points == 1){
                        points++
                    }
                    //Log.d(TAG, "${document.id} => ${document.data}")
                }
                uploadPoints(points)
            }
            .addOnFailureListener { exception ->
                //hiba esetén
            }
    }

    private fun uploadPoints(points: Int) {
        val newPoints = Points((this.activity as TipViewActivity).uid, (this.activity as TipViewActivity).userName, points)
        val db = Firebase.firestore
        db.collection("points")
            .document("${(this.activity as TipViewActivity).userName}")
            .set(newPoints)
            .addOnSuccessListener {
                //pontok sikeresen hozzáadva
            }
            .addOnFailureListener {
                //hiba esetén
            }
    }
}