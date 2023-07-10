package com.example.theyumexplorer.repository

import com.example.theyumexplorer.model.Recipe
import com.example.theyumexplorer.model.RecipeListResponse
import retrofit2.Response

interface RecipeRepository {
    suspend fun getRecipeList(q: String): Response<RecipeListResponse>
    suspend fun cacheRecipe(recipeList: List<Recipe>)
    suspend fun getCacheRecipeList(q: String): List<Recipe>

    suspend fun getRecipeFeed(): List<Recipe>
}