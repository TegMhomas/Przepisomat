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
    private val db = FirebaseFirestore.getInstance()
    private val przepisyRef = db.collection("przepisy")
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

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        recyclerView = binding.recyclerView
        layoutManager = LinearLayoutManager(context)
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
                        recipes.add(Recipe(R.drawable.baseline_fastfood_24, nazwa, opis, iloscLajkow, kategoria))


                    }
                }
                .addOnFailureListener { exception ->

                }
        adapter = RecipeAdapter(recipes)

        adapter.setOnItemClickListener(object : RecipeAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                val intent = Intent(context, RecipeDetailsActivity::class.java)
                intent.putExtra("recipeName", recipe.name)
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = true

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}