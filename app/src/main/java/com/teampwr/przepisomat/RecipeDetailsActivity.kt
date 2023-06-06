package com.teampwr.przepisomat

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class RecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        var start=0
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
        val videoView = findViewById<VideoView>(R.id.video_view)
        val mediaPlayer = MediaPlayer.create(this, R.raw.like)
        if(recipeName.equals("Pierogi"))
        {
            videoView.setVideoURI(Uri.parse(movie_url))
            videoView.setOnClickListener({
                if(start==0)
                {
                    videoView.start()
                    start=1
                }
                else
                {
                    videoView.pause()
                    start=0
                }

            })
        }

        if(LanguageManager.getSelectedLanguage(this).equals("en"))
        {
            recipeNameTextView.text = en_name
            recipeDescriptionTextView.text = en_description
        }
        else
        {
            recipeNameTextView.text = recipeName
            recipeDescriptionTextView.text = recipeDescription
        }
        //TextView.text = recipeDescription
        Picasso.get().load(recipeImage).into(recipeImageView)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            mediaPlayer.start()
        }
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.seekTo(0)
        }
    }
}