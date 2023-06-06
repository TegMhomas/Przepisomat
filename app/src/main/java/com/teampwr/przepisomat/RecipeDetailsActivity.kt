package com.teampwr.przepisomat

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class RecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val recipeName = intent.getStringExtra("recipeName")
        val recipeCategory = intent.getStringExtra("recipeCategory")
//        val likes = intent.getStringExtra("likes")
        val recipeDescription = intent.getStringExtra("recipeDescription")
        val recipeImage = intent.getStringExtra("recipeImage")
        val en_name = intent.getStringExtra("en_name")
        val en_description = intent.getStringExtra("en_description")
        val en_category = intent.getStringExtra("en_category")
        val movie_url = intent.getStringExtra("movie_url")
        val recipeNameTextView: TextView = findViewById(R.id.recipe_name)
        val recipeDescriptionTextView: TextView = findViewById(R.id.recipe_description)
        val recipeImageView: ImageView = findViewById(R.id.recipe_image)

        recipeNameTextView.text = recipeName
        recipeDescriptionTextView.text = recipeDescription
        //TextView.text = recipeDescription
        Picasso.get().load(recipeImage).into(recipeImageView)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {

        }
    }
}