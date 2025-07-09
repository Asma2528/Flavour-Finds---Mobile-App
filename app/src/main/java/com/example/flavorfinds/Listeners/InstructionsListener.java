package com.example.flavorfinds.Listeners;

import com.example.flavorfinds.Models.InstructionsResponse;
import com.example.flavorfinds.Models.RandomRecipeApiResponse;

import java.util.List;

// Interface to handle the results of fetching instructions
public interface InstructionsListener {

    // Method to handle a successful API response
    void didFetch(List<InstructionsResponse> response, String message);

    // Method to handle an error in fetching data
    void didError(String message);
}

