package com.teampwr.przepisomat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.teampwr.przepisomat.databinding.ActivityLoginBinding
import com.teampwr.przepisomat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
         //   var intent = Intent(this, LoginActivity::class.java)
          //  startActivity(intent)
            finish()
        }


    }
}