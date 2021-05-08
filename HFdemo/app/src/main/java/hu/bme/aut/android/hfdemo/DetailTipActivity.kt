package hu.bme.aut.android.hfdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.hfdemo.data.Tip
import hu.bme.aut.android.hfdemo.databinding.ActivityDetailTipBinding
import java.util.*

class DetailTipActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailTipBinding
    private var args: Array<String>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()

        args = intent.getStringArrayExtra("MATCH")
        binding.tvDate.text = args?.get(7)
        Glide.with(this).load(args?.get(5)).into(binding.ivHome)
        Glide.with(this).load(args?.get(6)).into(binding.ivAway)
        binding.tvTeamHome.text = args?.get(1)
        binding.tvTeamAway.text = args?.get(2)
        binding.tvMytip.text = "My tip: "
        binding.btnHome.setOnClickListener {
            val tipEnabled = checkDate()
            if (tipEnabled) {
                uploadTip(args!!.get(0), "HOME", args!!.get(1), args!!.get(2))
                binding.tvMytip.text = "My tip: ${args?.get(1)}"
            } else {
                toastLong("Invalid tip! You can not tip during or after the match!")
            }
        }
        binding.btnX.setOnClickListener {
            val tipEnabled = checkDate()
            if (tipEnabled) {
                uploadTip(args!!.get(0), "DRAW", args!!.get(1), args!!.get(2))
                binding.tvMytip.text = "My tip: Draw"
            } else {
                toastLong("Invalid tip! You can not tip during and after the match!")
            }
        }
        binding.btnAway.setOnClickListener {
            val tipEnabled = checkDate()
            if (tipEnabled) {
                uploadTip(args!!.get(0), "AWAY", args!!.get(1), args!!.get(2))
                binding.tvMytip.text = "My tip: ${args?.get(2)}"
            } else {
                toastLong("Invalid tip! You can not tip during or after the match!")
            }
        }
    }

    private fun uploadTip(matchID: String, tip: String, homeT: String, awayT: String) {
        val newTip = Tip(uid, userName, matchID, homeT, awayT, tip)

        val db = Firebase.firestore

        db.collection("spanish_tips")
            .document("$userName-${getDate()}-$matchID")
            .set(newTip)
            .addOnSuccessListener {
                toastLong("Tip created")
                //finish()
            }
            .addOnFailureListener { e -> toast(e.toString()) }
    }

    private fun getData() {
        val db = Firebase.firestore
        val docRef = db.collection("spanish_tips").document("mate970921-2021-05-02-605397")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                    Log.d("TAG", "Jó lesz: ${document.toObject<Tip>()?.author}")
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    private fun checkDate(): Boolean {
        val currentDate = Calendar.getInstance()
        val currentHour = currentDate.get(Calendar.HOUR_OF_DAY)
        return currentHour < args?.get(7)?.take(2)?.toInt()!!
    }

    private fun getDate(): String {
        val currentDate = Calendar.getInstance()
        val currentYear = "${currentDate.get(Calendar.YEAR)}"
        //Month (Format matters because of API)
        val currentMonthInt = currentDate.get(Calendar.MONTH) + 1
        val currentMonth: String = if (currentMonthInt < 10) {
            "0$currentMonthInt"
        } else {
            "$currentMonthInt"
        }
        //Day (Format matters because of API)
        val currentDayInt = currentDate.get(Calendar.DAY_OF_MONTH)
        val currentDay: String = if (currentDayInt < 10) {
            "0$currentDayInt"
        } else {
            "$currentDayInt"
        }
        return "$currentYear-$currentMonth-$currentDay"
    }
}