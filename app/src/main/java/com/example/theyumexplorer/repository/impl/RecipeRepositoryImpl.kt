package com.example.theyumexplorer.repository.impl

import com.example.theyumexplorer.api.RecipeApiService
import com.example.theyumexplorer.model.Recipe
import com.example.theyumexplorer.model.RecipeListResponse
import com.example.theyumexplorer.repository.RecipeRepository
import com.example.theyumexplorer.util.TheYumCollections
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: RecipeApiService,
    private val db: FirebaseFirestore,
) :
    RecipeRepository {
    override suspend fun getRecipeList(q: String): Response<RecipeListResponse> {
        return apiService.getRecipe(q)
    }

    override suspend fun cacheRecipe(recipeList: List<Recipe>) {
        recipeList.forEach {
            db.collection(TheYumCollections.RECIPE.name).document().set(it)
        }
    }

    override suspend fun getCacheRecipeList(q: String): List<Recipe> {
        return db.collection("Recipes").get()
            .await().toObjects(Recipe::class.java).filter { it.label.contains(q) }
    }

    override suspend fun getRecipeFeed(): List<Recipe> {
        return db.collection("Recipes").get()
            .await().toObjects(Recipe::class.java)
    }

}