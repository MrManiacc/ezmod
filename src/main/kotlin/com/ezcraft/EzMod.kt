package com.ezcraft

import com.ezcraft.blocks.aqueous.*
import com.ezcraft.data.*
import com.ezcraft.registry.*
import net.minecraft.client.renderer.*
import net.minecraft.client.renderer.model.*
import net.minecraft.item.*
import net.minecraftforge.fml.client.registry.*
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.*
import net.minecraftforge.fml.event.server.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.eventbus.*
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * Main mod class. Should be an `object` declaration annotated with `@Mod`.
 * The modid should be declared in this object and should match the modId entry
 * in mods.toml.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(EzMod.ID)
object EzMod {
    const val ID: String = "ezcraft"

    init {
        DataGenerators.register(MOD_BUS)
        ModBlocks.register(MOD_BUS)
        ModTiles.register(MOD_BUS)
        ModItems.register(MOD_BUS)
        Listeners.register(MOD_BUS, FORGE_BUS)
    }


}

/**
 * This will listener to the client/server specific events.
 */
internal object Listeners {

    /**
     * This will register the listeners
     */
    fun register(modBus: KotlinEventBus, forgeBus: KotlinEventBus) {
        DataGenerators.register(modBus)
        modBus.addListener(::onCommonSetup)
        modBus.addListener(::onClientSetup)
        modBus.addListener(::onLoadComplete)
        forgeBus.addListener(::onServerStopping)
    }

    /**
     * This will create the imgui instance
     */
    private fun onLoadComplete(event: FMLLoadCompleteEvent) {
    }

    /**
     * This is for initializing anything that's shared acorss the client
     * and server.
     */
    private fun onCommonSetup(event: FMLCommonSetupEvent) {

    }

    private fun onClientSetup(event: FMLClientSetupEvent) {

    }

    /**This will call the [GraphTileEntity.onUnload] for each tile in each world. This isn't the most efficient method,
     *  but it should only be called upon unloading of the world **/
    private fun onServerStopping(event: FMLServerStoppingEvent) {
    }

}

/**Various extensions pertaining to the mod env***/

// the logger for our mod
fun Any.logger(): Logger = LogManager.getLogger(this::class.java)
val Any.logger: Logger get() = logger()
fun Any.info(log: String) = logger.info(log)
fun Any.debug(log: String) = logger.debug(log)
fun Any.warn(log: String) = logger.warn(log)
fun Any.throwable(catching: Throwable) = logger.catching(catching)