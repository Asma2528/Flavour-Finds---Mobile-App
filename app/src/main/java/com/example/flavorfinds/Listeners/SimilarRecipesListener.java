package com.example.flavorfinds.Listeners;

import com.example.flavorfinds.Models.RecipeDetailsResponse;
import com.example.flavorfinds.Models.SimilarRecipeResponse;

import java.util.List;

public interface SimilarRecipesListener {
    void didFetch(List<SimilarRecipeResponse> response, String message);
    void didError(String message);
}
