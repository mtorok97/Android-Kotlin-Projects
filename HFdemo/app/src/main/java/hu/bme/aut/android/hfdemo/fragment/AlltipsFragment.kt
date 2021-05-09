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
import hu.bme.aut.android.hfdemo.adapter.TipAdapter
import hu.bme.aut.android.hfdemo.data.Tip
import hu.bme.aut.android.hfdemo.databinding.FragmentAlltipsBinding

class AlltipsFragment : Fragment() {
    private lateinit var binding: FragmentAlltipsBinding
    private lateinit var tipAdapter: TipAdapter

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlltipsBinding.inflate(layoutInflater, container, false)
        tipAdapter = this.context?.let { TipAdapter(it) }!!
        binding.root.findViewById<RecyclerView>(R.id.tip_all_list).adapter = tipAdapter
        getPoints()
        return binding.root
    }

    private fun getPoints() {
        TipAdapter.tipList = emptyList()
        val db = Firebase.firestore
        db.collection("spanish_tips")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val my_tip = document.toObject<Tip>()
                    tipAdapter.addTip(my_tip)
                }
            }
            .addOnFailureListener { exception ->
                //hiba esetén
            }
    }

}
