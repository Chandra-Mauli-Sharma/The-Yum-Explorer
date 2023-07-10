package com.example.theyumexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theyumexplorer.model.Recipe
import com.example.theyumexplorer.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    init {
        feedRecipe()
    }

    fun onQueryChanged(query: String) {
        _query.update {
            query
        }
    }

    private val _recipeListResponse = MutableStateFlow<List<Recipe>?>(null)
    val recipeListResponse = _recipeListResponse.asStateFlow()
    fun getRecipeList() {
        viewModelScope.launch {
            async { repository.getCacheRecipeList(_query.value) }.await().let {
                if (it.isEmpty()) {
                    val response = async { repository.getRecipeList(_query.value) }.await()
                    _recipeListResponse.emit(response.body()?.hits?.map { it.recipe })
                    cacheRecipe()
                } else {
                    _recipeListResponse.emit(it)
                }
            }
        }
    }

    private fun feedRecipe() {
        viewModelScope.launch {
            val recipeList = async { repository.getRecipeFeed() }.await()
            _recipeListResponse.emit(recipeList)
        }
    }

    private fun cacheRecipe() {
        viewModelScope.launch {
            recipeListResponse.value?.map { it }?.let { repository.cacheRecipe(it) }
        }
    }

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe = _recipe.asStateFlow()

    fun onRecipeChanged(recipe: Recipe) {
        _recipe.update {
            recipe
        }
    }
}