package com.teampwr.przepisomat

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val recipeName = intent.getStringExtra("recipeName")
        val recipeNameTextView: TextView = findViewById(R.id.recipe_name)
        recipeNameTextView.text = recipeName
    }
}