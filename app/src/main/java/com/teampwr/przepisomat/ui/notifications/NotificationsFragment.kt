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
import com.teampwr.przepisomat.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var languagePreferences: SharedPreferences
    private lateinit var languageAdapter: ArrayAdapter<String>
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
        val languages = arrayOf("Polski", "English") // Lista dostępnych języków
        languageAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = languageAdapter

        languagePreferences = requireContext().getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE)
        val savedLanguage = languagePreferences.getString("language", null)
        if (savedLanguage != null) {

            val spinnerPosition = languageAdapter.getPosition(savedLanguage)
            binding.languageSpinner.setSelection(spinnerPosition)
        }

        binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent.getItemAtPosition(position).toString()
                saveLanguage(selectedLanguage)
                setAppLanguage(selectedLanguage)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

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
        requireActivity().recreate()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}