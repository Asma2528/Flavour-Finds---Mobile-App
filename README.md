# 🍲 FlavorFinds - Recipe Discovery & Exploration App

**FlavorFinds** is an Android mobile application built to help users explore a wide range of recipes based on their preferences using the Spoonacular API.

## 🚀 Features
- User Registration & Login
- Random Recipe Suggestions
- Filter by Tags, Ingredients, and Cuisine
- Search Functionality
- View Step-by-Step Instructions
- Explore Similar Recipes
- Clean & Minimal UI with RecyclerView display


---

## 🔧 Tech Stack

- **Language**: Java  
- **IDE**: Android Studio  
- **API**: [Spoonacular API](https://spoonacular.com/food-api)  
- **Networking**: Retrofit + GSON  
- **Image Loading**: Picasso  
- **Tools**: Postman, Json2Csharp  

---

## 📦 Project Modules

### ▶️ MainActivity
Main screen with toolbar, search bar, tag filter, and recipe list (RecyclerView).

### ⚙️ RequestManager
Manages all Retrofit API calls to fetch:
- Random recipes
- Detailed recipe info
- Similar recipes
- Instructions

### 🔁 Recipe Listeners
- `RandomRecipeResponseListener`
- `RecipeDetailsListener`
- `SimilarRecipesListener`
- `InstructionsListener`

### 🔍 Search Module
SearchView-based filtering by ingredients, tags, or cuisine.

### 👤 Authentication
- `RegisterActivity`
- `LoginActivity`

### 🧱 Adapter (RecipeAdapter)
Populates RecyclerView with recipe cards.
---


## 🔗 API Integration

All recipe data is fetched from [Spoonacular API](https://spoonacular.com/food-api).  
Handled using **Retrofit**, parsed with **GSON**, and displayed via **RecyclerView**.

---

## 🛠 How to Run

1. Clone the repository
```bash
  git clone https://github.com/Asma2528/Flavour-Finds-Mobile-App

2. Open in Android Studio

2. Sync Gradle files

3. Run the app on emulator or real device

Useful Tools
Retrofit: https://github.com/square/retrofit

GSON Converter: https://github.com/square/retrofit/tree/master/retrofit-converters/gson

Picasso (Image Loading): https://github.com/square/picasso

Postman: https://www.postman.com/

Json2Csharp (Pojo generation): https://json2csharp.com/json-to-pojo





