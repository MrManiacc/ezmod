package com.ezcraft.data

import com.ezcraft.registry.*
import com.mojang.datafixers.util.Pair
import net.minecraft.data.*
import net.minecraft.data.loot.*
import net.minecraft.loot.*
import net.minecraft.util.*
import java.util.function.*

class ModLootTableProviders(dataGenerator: DataGenerator) : LootTableProvider(dataGenerator) {

    override fun getTables(): MutableList<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> =
        mutableListOf(Pair(Supplier { Blocks() }, LootParameterSets.BLOCK))

    override fun validate(map: MutableMap<ResourceLocation, LootTable>, validationtracker: ValidationTracker) {
        map.forEach { (t, u) -> LootTableManager.validate(validationtracker, t, u) }
    }

    /**Stores all of our **/
    class Blocks : BlockLootTables() {
        override fun addTables() {
            dropSelf(ModBlocks.AQUEOUS_BLOCK.block)
        }
    }
}