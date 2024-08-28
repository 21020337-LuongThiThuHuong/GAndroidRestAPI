package com.example.gandroidrestapi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter

    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        viewModel.pokemonList.observe(
            this,
            Observer { pokemonList ->
                pokemonAdapter = PokemonAdapter(pokemonList)
                recyclerView.adapter = pokemonAdapter
            },
        )

        viewModel.error.observe(
            this,
            Observer { errorMessage ->
                // Handle error (e.g., show a toast or a dialog)
            },
        )

        viewModel.fetchPokemonData()
    }
}
