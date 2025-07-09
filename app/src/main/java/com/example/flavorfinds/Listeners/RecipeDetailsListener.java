package com.example.flavorfinds.Listeners;

import com.example.flavorfinds.Models.RecipeDetailsResponse;
import com.example.flavorfinds.RecipeDetailsActivity;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
