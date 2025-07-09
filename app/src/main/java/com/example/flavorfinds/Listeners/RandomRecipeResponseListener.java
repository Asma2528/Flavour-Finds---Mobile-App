package com.example.flavorfinds.Listeners;

import com.example.flavorfinds.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {

    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
