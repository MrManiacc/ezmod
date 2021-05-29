package com.ezcraft.registry

import com.ezcraft.*
import com.ezcraft.blocks.aqueous.*
import com.ezcraft.items.*
import net.minecraftforge.registries.*
import thedarkcolour.kotlinforforge.eventbus.*
import thedarkcolour.kotlinforforge.forge.*

/***
 * Stores all of our blocks
 */
object ModItems {
    private val REGISTRY = KDeferredRegister(ForgeRegistries.ITEMS, EzMod.ID)
    fun register(eventBus: KotlinEventBus) = REGISTRY.register(eventBus)

    val AQUEOUS by REGISTRY.registerObject("aqueous") {
        AqueousItem(AqueousBlock.Stage.Normal)
    }

    val HAMMER by REGISTRY.registerObject("hammer") {
        HammerItem()
    }



}