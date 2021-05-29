package com.ezcraft.registry

import com.ezcraft.*
import com.ezcraft.blocks.aqueous.*
import net.minecraft.client.renderer.*
import net.minecraftforge.fml.event.lifecycle.*
import net.minecraftforge.registries.*
import thedarkcolour.kotlinforforge.eventbus.*
import thedarkcolour.kotlinforforge.forge.*

/***
 * Stores all of our blocks
 */
object ModBlocks {
    private val REGISTRY = KDeferredRegister(ForgeRegistries.BLOCKS, EzMod.ID)

    /*********************************************************************/
    val AQUEOUS_BLOCK by REGISTRY.registerObject("aqueous") { AqueousBlock(AqueousBlock.Stage.Normal) }
    /*********************************************************************/



    fun register(eventBus: KotlinEventBus) {
        REGISTRY.register(eventBus)
        eventBus.addListener(::onClientSetup)
    }

    private fun onClientSetup(e: FMLClientSetupEvent) {
        val translucent: RenderType = RenderType.translucent()
        RenderTypeLookup.setRenderLayer(AQUEOUS_BLOCK.block, translucent)
    }
}


