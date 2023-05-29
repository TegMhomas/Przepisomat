package com.teampwr.przepisomat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.teampwr.przepisomat.databinding.ActivityMainBinding
import com.teampwr.przepisomat.model.Recipe
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withTimeout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val db = FirebaseFirestore.getInstance()
    private val przepisyRef = db.collection("przepisy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recycler_view)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val recipes = ArrayList<Recipe>()


        przepisyRef.get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val zdjecie = document.getLong("zdjecie")
                    val nazwa = document.getString("nazwa")
                    val kategoria = document.getString("kategoria")
                    val opis = document.getString("opis")
                    val iloscLajkow = document.getLong("ilosc_lajkow")
                    System.out.println("KEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKWWWWWWWWWWWWWWWWWWW " + nazwa)
                    //recipes.add(Recipe(R.drawable.baseline_fastfood_24, nazwa, opis, iloscLajkow, kategoria))


                }
            }
            .addOnFailureListener { exception ->

            }


        while (recipes.isEmpty())
        {
            System.out.println("PUSTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + recipes.size)
        }


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