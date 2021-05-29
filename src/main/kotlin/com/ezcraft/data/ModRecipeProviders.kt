package com.ezcraft.data

import com.ezcraft.registry.*
import com.mojang.datafixers.util.Pair
import net.minecraft.data.*
import net.minecraft.data.loot.*
import net.minecraft.loot.*
import net.minecraft.util.*
import java.util.function.*

class ModRecipeProviders(dataGenerator: DataGenerator) : RecipeProvider(dataGenerator) {

    override fun buildShapelessRecipes(consumer: Consumer<IFinishedRecipe>) {
    }


}