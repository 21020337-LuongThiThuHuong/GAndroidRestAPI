package com.example.gandroidrestapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel : ViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchPokemonData() {
        val call = RetrofitInstance.pokemonApi.getPokemonList(limit = 20, offset = 0)
        call.enqueue(
            object : Callback<PokemonResponse> {
                override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>,
                ) {
                    if (response.isSuccessful) {
                        _pokemonList.value = response.body()?.results ?: emptyList()
                    } else {
                        _error.value = "Error: ${response.code()}"
                    }
                }

                override fun onFailure(
                    call: Call<PokemonResponse>,
                    t: Throwable,
                ) {
                    _error.value = t.message
                }
            },
        )
    }
}
