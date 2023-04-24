package com.teampwr.przepisomat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teampwr.przepisomat.databinding.ActivityMainBinding
import com.teampwr.przepisomat.model.Recipe

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recycler_view)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val recipes = listOf(
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 1", ""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 2",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 3",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 4",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 5",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 6",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 7",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 8",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 9",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 10",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 11",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 12",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 13",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 14",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 15",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 16",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 17",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 18",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 19",""),
            Recipe(R.drawable.baseline_fastfood_24, "Recipe 20","")
        )



        adapter = RecipeAdapter(recipes)

        adapter.setOnItemClickListener(object : RecipeAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                val intent = Intent(this@MainActivity, RecipeDetailsActivity::class.java)
                intent.putExtra("recipeName", recipe.name)
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = true



    }
}