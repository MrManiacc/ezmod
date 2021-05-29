package com.ezcraft.blocks.aqueous

import com.ezcraft.registry.*
import net.minecraft.block.*
import net.minecraft.item.*
import net.minecraft.util.*

class AqueousItem(val stage: AqueousBlock.Stage) :
    BlockItem(ModBlocks.AQUEOUS_BLOCK.block, Properties().fireResistant().stacksTo(1).tab(ItemGroup.TAB_MISC)) {

//    override fun placeBlock(context: BlockItemUseContext, state: BlockState): Boolean {
//        return super.placeBlock(context, state)
//    }

    override fun place(context: BlockItemUseContext): ActionResultType {
        val result = super.place(context)
        val world = context.level
        val pos = context.clickedPos
        if (world.isClientSide) return result
        val state = world.getBlockState(context.clickedPos)
        if (state.block is AqueousBlock) {
            val newState: BlockState = state.setValue(AqueousBlock.STAGE, this.stage)
            world.setBlock(pos, newState, 3)
            world.updateNeighborsAt(pos, state.block)
            world.updateNeighborsAt(pos.below(), state.block)
            world.setBlocksDirty(pos, state, newState)
            return ActionResultType.SUCCESS
        }
        return result
    }


}