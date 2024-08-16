package com.example.gandroidrestapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        fetchPokemonData()
    }

    private fun fetchPokemonData() {
        val call = RetrofitInstance.pokemonApi.getPokemonList(limit = 20, offset = 0)

        call.enqueue(
            object : Callback<PokemonResponse> {
                override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>,
                ) {
                    if (response.isSuccessful) {
                        val pokemonList = response.body()?.results ?: emptyList()
                        pokemonAdapter = PokemonAdapter(pokemonList)
                        recyclerView.adapter = pokemonAdapter
                    }
                }

                override fun onFailure(
                    call: Call<PokemonResponse>,
                    t: Throwable,
                ) {
                    // Handle failure
                }
            },
        )
    }
}
