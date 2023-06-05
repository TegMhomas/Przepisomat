package com.teampwr.przepisomat.ui.notifications

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.teampwr.przepisomat.LanguageManager
import com.teampwr.przepisomat.LoginActivity
import com.teampwr.przepisomat.MainActivity
import com.teampwr.przepisomat.RecipesActivity
import com.teampwr.przepisomat.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var languagePreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        firebaseAuth = FirebaseAuth.getInstance()
        binding.button2.setOnClickListener({
            firebaseAuth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        })
        LanguageManager.applyLanguage(requireContext())
        languagePreferences = requireContext().getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE)

        binding.button3.setOnClickListener({
            LanguageManager.setSelectedLanguage(requireContext(), "en")
            LanguageManager.applyLanguage(requireContext())
            requireActivity().recreate()
        })

        binding.button4.setOnClickListener({
            LanguageManager.setSelectedLanguage(requireContext(), "default")
            LanguageManager.applyLanguage(requireContext())
            requireActivity().recreate()
        })

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val uid = user.uid
            val email = user.email
            val displayName = user.displayName

        }

        return root
    }
    private fun saveLanguage(language: String) {
        val editor = languagePreferences.edit()
        editor.putString("language", language)
        editor.apply()
    }

    private fun setAppLanguage(language: String) {
        LanguageManager.setSelectedLanguage(requireContext(), language)
        LanguageManager.applyLanguage(requireContext())

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}