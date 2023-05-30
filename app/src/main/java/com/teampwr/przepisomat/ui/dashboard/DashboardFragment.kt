package com.teampwr.przepisomat.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance("https://przepisomat-39ba5-default-rtdb.europe-west1.firebasedatabase.app")
    }
    private val recipesRef: DatabaseReference by lazy {
        database.getReference("recipes")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        searchEditText = binding.searchEditText
        searchButton = binding.searchButton

        recyclerView = binding.recyclerView
        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        val recipesList: MutableList<Recipe> = mutableListOf()

        adapter = RecipeAdapter(recipesList)
        adapter.setOnItemClickListener(object : RecipeAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                val intent = Intent(requireContext(), RecipeDetailsActivity::class.java)
                intent.putExtra("recipeName", recipe.name)
                intent.putExtra("recipeCategory", recipe.category)
                intent.putExtra("recipeDescription", recipe.description)
                intent.putExtra("recipeImage", recipe.image)
                intent.putExtra("likes", recipe.likes)
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = true

        searchButton.setOnClickListener {
            val searchQuery = searchEditText.text.toString().trim()
            searchRecipes(searchQuery)
        }

        recipesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                recipesList.clear()
                for (snapshot in dataSnapshot.children) {
                    val recipe = snapshot.getValue(Recipe::class.java)
                    recipe?.let {
                        recipesList.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("DashboardFragment", "Error fetching recipes: ${databaseError.message}")
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchRecipes(query: String) {
        val lowercaseQuery = query.toLowerCase(Locale.getDefault())
        val filteredList = mutableListOf<Recipe>()

        for (recipe in adapter.getRecipes()) {
            if (recipe.name?.toLowerCase(Locale.getDefault())?.contains(lowercaseQuery) == true) {
                filteredList.add(recipe)
            }
        }

        adapter.filterRecipes(filteredList)
    }
}
