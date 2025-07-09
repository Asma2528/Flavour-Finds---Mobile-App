package com.example.flavorfinds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flavorfinds.Adapters.IngredientsAdapter;
import com.example.flavorfinds.Adapters.InstructionsAdapter;
import com.example.flavorfinds.Adapters.SimilarRecipeAdapter;
import com.example.flavorfinds.Listeners.InstructionsListener;
import com.example.flavorfinds.Listeners.RecipeClickListener;
import com.example.flavorfinds.Listeners.RecipeDetailsListener;
import com.example.flavorfinds.Listeners.SimilarRecipesListener;
import com.example.flavorfinds.Models.InstructionsResponse;
import com.example.flavorfinds.Models.RecipeDetailsResponse;
import com.example.flavorfinds.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    // Views and variables
    int id;
    TextView textView_meal_name, textView_meal_source;
    RecyclerView recycler_meal_ingredients, recycler_meal_similar, recycler_meal_instructions;
    ImageView imageView_meal_image;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display mode
        setContentView(R.layout.activity_recipe_details);

        // Apply window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        findViews();

        // Retrieve recipe ID from intent
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        Log.d("RECIPE_ID", "Recipe ID: " + id);

        // Initialize the request manager
        manager = new RequestManager(this);

        // Fetch recipe details, similar recipes, and instructions
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipeListener, id);
        manager.getInstructions(instructionsListener, id);

        // Initialize and show progress dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details");
        dialog.show();
    }

    private void findViews() {
        // Find and initialize UI elements
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions);
    }

    // Listener for recipe details response
    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss(); // Dismiss progress dialog

            if (response != null) {
                // Update UI with recipe details
                textView_meal_name.setText(response.title);
                textView_meal_source.setText(response.sourceName != null ? response.sourceName : "Unknown");
                Picasso.get().load(response.image).into(imageView_meal_image);

                // Set up RecyclerView for ingredients
                recycler_meal_ingredients.setHasFixedSize(true);
                recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
                recycler_meal_ingredients.setAdapter(ingredientsAdapter);

                // Log ingredients for debugging
                Log.d("API_RESPONSE", "Ingredients: " + response.extendedIngredients.toString());
            } else {
                Toast.makeText(RecipeDetailsActivity.this, "Failed to load details.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void didError(String message) {
            dialog.dismiss();
            Log.e("API_ERROR", "Error: " + message);
            Toast.makeText(RecipeDetailsActivity.this, "API Error: " + message, Toast.LENGTH_SHORT).show();
        }
    };

    // Listener for similar recipes response
    private final SimilarRecipesListener similarRecipeListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            // Set up RecyclerView for similar recipes
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            if (response != null && !response.isEmpty()) {
                similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this, response, recipeClickListener);
                recycler_meal_similar.setAdapter(similarRecipeAdapter);
            } else {
                Log.d("SIMILAR_RECIPES", "No similar recipes found.");
            }
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    // Listener for recipe click events in similar recipes
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class).putExtra("id", id));
        }
    };

    // Listener for instructions response
    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            // Set up RecyclerView for instructions
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(RecipeDetailsActivity.this, response);
            recycler_meal_instructions.setAdapter(instructionsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}
