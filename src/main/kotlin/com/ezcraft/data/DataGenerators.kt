package com.ezcraft.data

import com.ezcraft.data.client.*
import net.minecraftforge.fml.event.lifecycle.*
import thedarkcolour.kotlinforforge.eventbus.*

object DataGenerators {
    fun register(bus: KotlinEventBus) = bus.addListener(::gatherData)

    private fun gatherData(event: GatherDataEvent) {
        val gen = event.generator
        val fileHelper = event.existingFileHelper
        gen.addProvider(ModItemModelProvider(gen, fileHelper))
//        gen.addProvider(ModBlockStateProvider(gen, fileHelper))
        gen.addProvider(ModLootTableProviders(gen))
        gen.addProvider(ModRecipeProviders(gen))
    }

}