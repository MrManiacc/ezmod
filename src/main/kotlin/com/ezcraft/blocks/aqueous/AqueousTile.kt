package com.ezcraft.blocks.aqueous

import com.ezcraft.registry.*
import net.minecraft.block.*
import net.minecraft.nbt.*
import net.minecraft.tileentity.*
import net.minecraft.util.*
import net.minecraft.world.server.*
import net.minecraftforge.common.capabilities.*
import net.minecraftforge.common.util.*
import net.minecraftforge.fluids.capability.*
import net.minecraftforge.fluids.capability.templates.*

/**Stores extract data about our aqueous chamber, provides container screens if needed, provided renderers, providers item handler
 * input from below**/
class AqueousTile : TileEntity(ModTiles.AQUEOUS_TILE), ITickableTileEntity {

    /**The number of times to call per tick, if 20 then it's once per second, 40 = 2x second, etc.**/
    private var tickRate: Int = 20

    /**The current ticked value**/
    private var tick: Int = 0

    /**Can hold 256 buckets**/
    internal var tankHandler: FluidTank = object : FluidTank(32_000) {
        /**Forces a sync from server to client any time a slot changes.**/
        override fun onContentsChanged() = setChanged()
    }

    /**Allows access to our tank via a lazy optional**/
    val tank: LazyOptional<IFluidHandler> = LazyOptional.of { tankHandler }

    override fun tick() {
        if (level is ServerWorld) {
            if (tick++ > tickRate) {

                tick = 0
            }
        }
    }

    override fun save(nbt: CompoundNBT): CompoundNBT {
        return super.save(nbt)
    }

    override fun load(state: BlockState, nbt: CompoundNBT) {
        super.load(state, nbt)
    }

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        val stage = blockState.getValue(AqueousBlock.STAGE)
        if (side == Direction.DOWN && stage.hasInput && (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY))
            return tank.cast()
        return super.getCapability(cap, side)
    }


}
