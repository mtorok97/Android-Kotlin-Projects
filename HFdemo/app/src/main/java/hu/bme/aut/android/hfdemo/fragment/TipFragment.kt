package hu.bme.aut.android.hfdemo.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import hu.bme.aut.android.hfdemo.StartActivity
import hu.bme.aut.android.hfdemo.TipActivity
import hu.bme.aut.android.hfdemo.databinding.FragmentTipBinding
import hu.bme.aut.android.hfdemo.extensions.validateNonEmpty


class TipFragment : Fragment() {

    private lateinit var binding: FragmentTipBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTipBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener { registerClick(); loginClick() }
        binding.btnLogin.setOnClickListener { loginClick() }
        return binding.root
    }

    private fun validateForm() = binding.etEmail.validateNonEmpty() && binding.etPassword.validateNonEmpty()

    private fun registerClick() {
        if (!validateForm()) {
            return
        }

        (this.activity as StartActivity).showProgressDialog()

        firebaseAuth
            .createUserWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            .addOnSuccessListener { result ->

                val firebaseUser = result.user
                val profileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(firebaseUser?.email?.substringBefore('@'))
                    .build()
                firebaseUser?.updateProfile(profileChangeRequest)

                (this.activity as StartActivity).toast("Registration successful")
            }
            .addOnFailureListener { exception ->
                (this.activity as StartActivity).hideProgressDialog()

                (this.activity as StartActivity).toast(exception.message)
            }
    }

    private fun loginClick() {
        if (!validateForm()) {
            return
        }

        (this.activity as StartActivity).showProgressDialog()

        firebaseAuth
            .signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            .addOnSuccessListener {
                (this.activity as StartActivity).hideProgressDialog()

                startActivity(Intent(activity, TipActivity::class.java))
            }
            .addOnFailureListener { exception ->
                (this.activity as StartActivity).hideProgressDialog()

                (this.activity as StartActivity).toast(exception.localizedMessage)
            }
    }
}