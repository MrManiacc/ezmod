package com.ezcraft.registry

import com.ezcraft.*
import com.ezcraft.blocks.aqueous.*
import net.minecraft.tileentity.*
import net.minecraftforge.fml.client.registry.*
import net.minecraftforge.fml.event.lifecycle.*
import net.minecraftforge.registries.*
import thedarkcolour.kotlinforforge.eventbus.*
import thedarkcolour.kotlinforforge.forge.*

/***
 * Stores all of our blocks
 */
object ModTiles {
    private val REGISTRY = KDeferredRegister(ForgeRegistries.TILE_ENTITIES, EzMod.ID)

    /*********************************************************************/
    val AQUEOUS_TILE: TileEntityType<AqueousTile> by REGISTRY.registerObject("aqueous") {
        TileEntityType.Builder.of(
            { AqueousTile() }, ModBlocks.AQUEOUS_BLOCK.block
        ).build(null)
    }
    /*********************************************************************/

    fun register(eventBus: KotlinEventBus) {
        REGISTRY.register(eventBus)
        eventBus.addListener(::onClientSetup)
    }

    private fun onClientSetup(e: FMLClientSetupEvent) {
        ClientRegistry.bindTileEntityRenderer(AQUEOUS_TILE) {
            AqueousChamberRenderer(it)
        }
    }

}