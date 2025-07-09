package com.example.flavorfinds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flavorfinds.Adapters.RandomRecipeAdapter;
import com.example.flavorfinds.Listeners.RandomRecipeResponseListener;
import com.example.flavorfinds.Listeners.RecipeClickListener;
import com.example.flavorfinds.Models.RandomRecipeApiResponse;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    // Progress dialog to show loading status
    ProgressDialog dialog;
    // Manager to handle requests
    RequestManager manager;
    // Adapter for displaying random recipes
    RandomRecipeAdapter randomRecipeAdapter;
    // RecyclerView for displaying recipes
    RecyclerView recyclerView;
    // Spinner for selecting tags
    Spinner spinner;
    // List to hold selected tags for filtering recipes
    List<String> tags = new ArrayList<>();
    // Search view for searching recipes
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enables edge-to-edge display mode

        // Set the content view to the main layout
        setContentView(R.layout.activity_main);

        // Initialize and configure the progress dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading.... ");

        // Initialize and configure the spinner for tags
        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        // Initialize the request manager
        manager = new RequestManager(this);

        // Initialize and configure the search view
        searchView = findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear(); // Clear previous tags
                tags.add(query); // Add new query as tag
                manager.getRandomRecipes(randomRecipeResponseListener, tags); // Fetch recipes based on query
                dialog.show(); // Show progress dialog
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    // Listener for handling the response from fetching random recipes
    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss(); // Dismiss progress dialog
            // Initialize and configure RecyclerView
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show(); // Show error message
        }
    };

    // Listener for handling spinner item selections
    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear(); // Clear previous tags
            tags.add(adapterView.getSelectedItem().toString()); // Add selected tag
            manager.getRandomRecipes(randomRecipeResponseListener, tags); // Fetch recipes based on selected tag
            dialog.show(); // Show progress dialog
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            // No action needed when no item is selected
        }
    };

    // Listener for handling recipe item clicks
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(MainActivity.this, RecipeDetailsActivity.class).putExtra("id", id)); // Navigate to RecipeDetailsActivity with recipe id
        }
    };

    // Method to handle sign-out button click
    public void Sign_out(View view) {
        FirebaseAuth.getInstance().signOut(); // Sign out the user from Firebase
        startActivity(new Intent(this, WelcomeActivity.class)); // Navigate to WelcomeActivity
    }
}
