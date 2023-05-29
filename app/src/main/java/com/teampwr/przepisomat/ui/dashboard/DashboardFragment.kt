package com.teampwr.przepisomat.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.teampwr.przepisomat.R
import com.teampwr.przepisomat.RecipeAdapter
import com.teampwr.przepisomat.RecipeDetailsActivity
import com.teampwr.przepisomat.RecipesActivity
import com.teampwr.przepisomat.databinding.FragmentDashboardBinding
import com.teampwr.przepisomat.model.Recipe

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var layoutManager: LinearLayoutManager
    val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://przepisomat-39ba5-default-rtdb.europe-west1.firebasedatabase.app")
    val recipesRef: DatabaseReference = database.getReference("recipes")
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        recyclerView = binding.recyclerView
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
       // val recipe = Recipe(0,"Nazwa przepisu", "Opis przepisu", 2, "test" )
       // val recipeRef: DatabaseReference = recipesRef.push()
        //recipeRef.setValue(recipe)

        val recipes = ArrayList<Recipe>()

        val recipesList: MutableList<Recipe> = mutableListOf()

        recipesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                recipesList.clear()
                for (snapshot in dataSnapshot.children) {
                    val recipe = snapshot.getValue(Recipe::class.java)
                    recipe?.let {
                        recipesList.add(it)
                    }
                    println("ROZMIAR: " + recipesList.size)
                }
                adapter = RecipeAdapter(recipesList)

                adapter.setOnItemClickListener(object : RecipeAdapter.OnItemClickListener {
                    override fun onItemClick(recipe: Recipe) {
                        val intent = Intent(context, RecipeDetailsActivity::class.java)
                        intent.putExtra("recipeName", recipe.name)
                        startActivity(intent)
                    }
                })
                recyclerView.adapter = adapter
                recyclerView.isNestedScrollingEnabled = true
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("ERROR: " + databaseError)
            }
        })


        println("ROZMIAR: " + recipesList.size)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}