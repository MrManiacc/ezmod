package com.ezcraft.items

import com.ezcraft.blocks.aqueous.*
import com.ezcraft.registry.*
import net.minecraft.block.*
import net.minecraft.item.*
import net.minecraft.util.*

class HammerItem : Item(Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).durability(200)) {
    override fun useOn(context: ItemUseContext): ActionResultType {
        val world = context.level
        val playerEntity = context.player ?: return super.useOn(context)
        val hand = playerEntity.mainHandItem
        val pos = context.clickedPos
        val state = world.getBlockState(pos)
        if (playerEntity.isCrouching && hand.item == ModItems.HAMMER && state.block == ModBlocks.AQUEOUS_BLOCK.block) {
            val newState: BlockState = state.setValue(
                AqueousBlock.STAGE,
                AqueousBlock.Stage.values()[(state.getValue(AqueousBlock.STAGE).ordinal + 1) % AqueousBlock.Stage.values().size]
            )
            world.setBlock(pos, newState, 3)
            world.updateNeighborsAt(pos, ModBlocks.AQUEOUS_BLOCK.block)
            world.updateNeighborsAt(pos.below(), ModBlocks.AQUEOUS_BLOCK.block)
            world.setBlocksDirty(pos, state, newState)
            world.playSound(context.player, pos, SoundEvents.ANVIL_DESTROY, SoundCategory.BLOCKS,0.5f,
                            world.random.nextFloat() * 0.33f + 0.6f)
            return ActionResultType.SUCCESS
        }
        return ActionResultType.FAIL
    }
}