package hu.bme.aut.android.hfdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.hfdemo.adapter.MatchTipAdapter
import hu.bme.aut.android.hfdemo.data.Match
import hu.bme.aut.android.hfdemo.data.Tip
import hu.bme.aut.android.hfdemo.databinding.ActivityTipBinding


class TipActivity : BaseActivity(), MatchTipAdapter.MatchItemClickListener {
    private lateinit var binding: ActivityTipBinding
    private lateinit var matchTipAdapter: MatchTipAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        matchTipAdapter = MatchTipAdapter(this)
        matchTipAdapter.itemClickListener = this
        //simpleItemRecyclerViewAdapter.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.match_tip_list).adapter = matchTipAdapter

        getData()
        binding.tvdata1.text = userEmail
        binding.btn1.setOnClickListener {
            sendClick()
            matchTipAdapter.addMatch(
                Match(
                    1,
                    "Real Betis",
                    "Atletico Madrid",
                    3,
                    1,
                    "https://media.api-sports.io/football/teams/543.png",
                    "https://media.api-sports.io/football/teams/530.png",
                    "21:00",
                    "Home"
                )
            )
        }
    }

    private fun sendClick() {
        //if (!validateForm()) {
        //    return
        //}
        uploadTip()

    }

    //private fun validateForm() = binding.etTitle.validateNonEmpty() && binding.etBody.validateNonEmpty()

    private fun uploadTip() {
        val newTip = Tip(uid, userName, 290, "away")

        val db = Firebase.firestore

        db.collection("tips")
            .document("Barca")
            .set(newTip)
            .addOnSuccessListener {
                toast("Tip created")
                //finish()
            }
            .addOnFailureListener { e -> toast(e.toString()) }
    }

    private fun getData() {
        val db = Firebase.firestore
        val docRef = db.collection("tips").document("U7AeQhWcoaMr537PTvcr")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    override fun onItemClick(match: Match) {
        val tempMatch = arrayOf<String>(match.matchId.toString(), match.homeTeam, match.awayTeam, match.homeGoals.toString(), match.awayGoals.toString(), match.homeImageURL.toString(), match.awayImageURL.toString(), match.matchDate.toString(), match.myTip.toString())
        val intent = Intent(this, DetailTipActivity::class.java)
        intent.putExtra("MATCH", tempMatch)
        startActivity(intent)
    }
}