package com.example.theyumexplorer.api

import com.example.theyumexplorer.BuildConfig
import com.example.theyumexplorer.model.RecipeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("/api/recipes/v2")
    suspend fun getRecipe(
        @Query("q") q: String,
        @Query("type") type: String = "public",
        @Query("app_id") app_id: String = BuildConfig.app_id,
        @Query("app_key") app_key: String = BuildConfig.app_key,
    ): Response<RecipeListResponse>
}