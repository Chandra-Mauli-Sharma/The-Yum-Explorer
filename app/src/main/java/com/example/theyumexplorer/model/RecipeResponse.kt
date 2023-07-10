package com.example.theyumexplorer.model

import com.google.gson.annotations.SerializedName


data class RecipeListResponse(
    val from: Long,
    val to: Long,
    val count: Long,

    @SerializedName("_links")
    val links: RecipeListLinks,

    val hits: List<Hit>
)


data class Hit(
    val recipe: Recipe,

    @SerializedName("_links")
    val links: HitLinks
)


data class HitLinks(
    val self: Next
)


data class Next(
    val href: String,
    val title: Title
)


enum class Title(val value: String) {
    @SerializedName("Next page")
    NextPage("Next page"),

    @SerializedName("Self")
    Self("Self");
}


data class Recipe(
    val uri: String = "",
    val label: String = "",
    val image: String = "",
    val images: Images = Images(),
    val source: String = "",
    val url: String = "",
    val shareAs: String = "",
    val yield: Long = 0,
    val dietLabels: List<DietLabel> = listOf(),
    val healthLabels: List<String> = listOf(),
    val cautions: List<Caution> = listOf(),
    val ingredientLines: List<String> = listOf(),
    val ingredients: List<Ingredient> = listOf(),
    val calories: Double = 0.0,

    @SerializedName("totalCO2Emissions")
    val totalco2Emissions: Double = 0.0,

    val co2EmissionsClass: Co2EmissionsClass = Co2EmissionsClass.F,
    val totalWeight: Double = 0.0,
    val totalTime: Double = 0.0,
    val cuisineType: List<String> = listOf(),
    val mealType: List<MealType> = listOf(),
    val dishType: List<String> = listOf(),
    val totalNutrients: Map<String, Total> = mapOf(),
    val totalDaily: Map<String, Total>? = mapOf(),
    val digest: List<Digest> = listOf()
)


enum class Caution(val value: String) {
    @SerializedName("FODMAP")
    Fodmap("FODMAP"),

    @SerializedName("Gluten")
    Gluten("Gluten"),

    @SerializedName("Sulfites")
    Sulfites("Sulfites"),

    @SerializedName("Wheat")
    Wheat("Wheat");
}


enum class Co2EmissionsClass(val value: String) {
    @SerializedName("F")
    F("F"),

    @SerializedName("G")
    G("G");
}


enum class DietLabel(val value: String) {
    @SerializedName("High-Fiber")
    HighFiber("High-Fiber"),

    @SerializedName("High-Protein")
    HighProtein("High-Protein"),

    @SerializedName("Low-Carb")
    LowCarb("Low-Carb");
}


data class Digest(
    val label: Label = Label.SugarAlcohols,
    val tag: String = "",
    val schemaOrgTag: SchemaOrgTag? = null,
    val total: Double = 0.0,

    @SerializedName("hasRDI")
    val hasrdi: Boolean = false,

    val daily: Double = 0.0,
    val unit: Unit = Unit.Empty,
    val sub: List<Digest>? = null
)


enum class Label(val value: String) {
    @SerializedName("Calcium")
    Calcium("Calcium"),

    @SerializedName("Carbohydrates (net)")
    CarbohydratesNet("Carbohydrates (net)"),

    @SerializedName("Carbs")
    Carbs("Carbs"),

    @SerializedName("Carbs (net)")
    CarbsNet("Carbs (net)"),

    @SerializedName("Cholesterol")
    Cholesterol("Cholesterol"),

    @SerializedName("Energy")
    Energy("Energy"),

    @SerializedName("Fat")
    Fat("Fat"),

    @SerializedName("Fiber")
    Fiber("Fiber"),

    @SerializedName("Folate equivalent (total)")
    FolateEquivalentTotal("Folate equivalent (total)"),

    @SerializedName("Folate (food)")
    FolateFood("Folate (food)"),

    @SerializedName("Folic acid")
    FolicAcid("Folic acid"),

    @SerializedName("Iron")
    Iron("Iron"),

    @SerializedName("Magnesium")
    Magnesium("Magnesium"),

    @SerializedName("Monounsaturated")
    Monounsaturated("Monounsaturated"),

    @SerializedName("Niacin (B3)")
    Niacinb3("Niacin (B3)"),

    @SerializedName("Phosphorus")
    Phosphorus("Phosphorus"),

    @SerializedName("Polyunsaturated")
    Polyunsaturated("Polyunsaturated"),

    @SerializedName("Potassium")
    Potassium("Potassium"),

    @SerializedName("Protein")
    Protein("Protein"),

    @SerializedName("Riboflavin (B2)")
    Riboflavinb2("Riboflavin (B2)"),

    @SerializedName("Saturated")
    Saturated("Saturated"),

    @SerializedName("Sodium")
    Sodium("Sodium"),

    @SerializedName("Sugar alcohols")
    SugarAlcohols("Sugar alcohols"),

    @SerializedName("Sugars")
    Sugars("Sugars"),

    @SerializedName("Sugars, added")
    SugarsAdded("Sugars, added"),

    @SerializedName("Thiamin (B1)")
    Thiaminb1("Thiamin (B1)"),

    @SerializedName("Trans")
    Trans("Trans"),

    @SerializedName("Vitamin A")
    Vitamina("Vitamin A"),

    @SerializedName("Vitamin B12")
    Vitaminb12("Vitamin B12"),

    @SerializedName("Vitamin B6")
    Vitaminb6("Vitamin B6"),

    @SerializedName("Vitamin C")
    Vitaminc("Vitamin C"),

    @SerializedName("Vitamin D")
    Vitamind("Vitamin D"),

    @SerializedName("Vitamin E")
    Vitamine("Vitamin E"),

    @SerializedName("Vitamin K")
    Vitamink("Vitamin K"),

    @SerializedName("Water")
    Water("Water"),

    @SerializedName("Zinc")
    Zinc("Zinc");
}


enum class SchemaOrgTag(val value: String) {
    @SerializedName("carbohydrateContent")
    CarbohydrateContent("carbohydrateContent"),

    @SerializedName("cholesterolContent")
    CholesterolContent("cholesterolContent"),

    @SerializedName("fatContent")
    FatContent("fatContent"),

    @SerializedName("fiberContent")
    FiberContent("fiberContent"),

    @SerializedName("proteinContent")
    ProteinContent("proteinContent"),

    @SerializedName("saturatedFatContent")
    SaturatedFatContent("saturatedFatContent"),

    @SerializedName("sodiumContent")
    SodiumContent("sodiumContent"),

    @SerializedName("sugarContent")
    SugarContent("sugarContent"),

    @SerializedName("transFatContent")
    TransFatContent("transFatContent");
}


enum class Unit(val value: String) {
    @SerializedName("%")
    Empty("%"),

    @SerializedName("g")
    G("g"),

    @SerializedName("kcal")
    Kcal("kcal"),

    @SerializedName("mg")
    Mg("mg"),

    @SerializedName("µg")
    Μg("µg");
}


data class Images(
    @SerializedName("THUMBNAIL")
    val thumbnail: Large = Large(),

    @SerializedName("SMALL")
    val small: Large = Large(),

    @SerializedName("REGULAR")
    val regular: Large = Large(),

    @SerializedName("LARGE")
    val large: Large? = null
)


data class Large(
    val url: String = "",
    val width: Long = 0,
    val height: Long = 0
)


data class Ingredient(
    val text: String = "",
    val quantity: Double = 0.0,
    val measure: String? = null,
    val food: String = "",
    val weight: Double = 0.0,
    val foodCategory: String = "",

    @SerializedName("foodId")
    val foodid: String = "",

    val image: String? = null
)


enum class MealType(val value: String) {
    @SerializedName("brunch")
    Brunch("brunch"),

    @SerializedName("lunch/dinner")
    LunchDinner("lunch/dinner");
}


data class Total(
    val label: Label = Label.SugarAlcohols,
    val quantity: Double = 0.0,
    val unit: Unit = Unit.Empty
)


data class RecipeListLinks(
    val next: Next
)
