package com.teampwr.przepisomat.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.teampwr.przepisomat.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var userEmailTextView: TextView
    private lateinit var changePasswordButton: Button
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val user = FirebaseAuth.getInstance().currentUser
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userEmailTextView = binding.userEmailTextView
        changePasswordButton = binding.changePasswordButton

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userEmail = currentUser.email
            userEmailTextView.text = userEmail

            changePasswordButton.setOnClickListener {
                val oldPasswordEditText: EditText = binding.oldPasswordEditText
                val newPasswordEditText: EditText = binding.newPasswordEditText
                val confirmPasswordEditText: EditText = binding.confirmPasswordEditText

                val oldPassword = oldPasswordEditText.text.toString()
                val newPassword = newPasswordEditText.text.toString()
                val confirmPassword = confirmPasswordEditText.text.toString()

                // Sprawdzenie, czy nowe hasła są identyczne
                if (newPassword == confirmPassword) {
                    // Utworzenie poświadczenia dla starego hasła
                    val user = FirebaseAuth.getInstance().currentUser

                    user?.let {
                        val credential = it.email?.let { email ->
                            EmailAuthProvider.getCredential(email, oldPassword)
                        }

                        credential?.let { credential ->
                            it.reauthenticate(credential)
                                .addOnCompleteListener { authResult ->
                                    if (authResult.isSuccessful) {
                                        // Zmiana hasła na nowe
                                        user.updatePassword(newPassword)
                                            .addOnCompleteListener { passwordUpdateTask ->
                                                if (passwordUpdateTask.isSuccessful) {
                                                    Toast.makeText(
                                                        context,
                                                        "Hasło zostało pomyślnie zmienione",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    // Wyczyść pola tekstowe
                                                    oldPasswordEditText.text.clear()
                                                    newPasswordEditText.text.clear()
                                                    confirmPasswordEditText.text.clear()
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Wystąpił błąd podczas zmiany hasła",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Stare hasło jest niepoprawne",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    }

                }

            }
            }
            return root
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}