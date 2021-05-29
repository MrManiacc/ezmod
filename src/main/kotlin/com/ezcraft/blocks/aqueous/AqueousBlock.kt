package com.ezcraft.blocks.aqueous

import com.ezcraft.registry.*
import net.minecraft.block.*
import net.minecraft.block.material.*
import net.minecraft.entity.player.*
import net.minecraft.item.*
import net.minecraft.loot.*
import net.minecraft.state.*
import net.minecraft.tileentity.*
import net.minecraft.util.*
import net.minecraft.util.math.*
import net.minecraft.util.math.shapes.*
import net.minecraft.world.*
import net.minecraftforge.common.*
import java.util.stream.*

/**The Aqueous block allows for custom recipes using liquid's and energy. **/
class AqueousBlock(stage: Stage) : Block(
    Properties.of(Material.STONE)
        .sound(stage.sound)
        .strength(5f, 10f)
        .harvestTool(ToolType.PICKAXE)
        .randomTicks()
        .dynamicShape()
) {


    init {
        registerDefaultState(defaultBlockState().setValue(STAGE, stage))
    }

    /***Creates our initial state for the block, adding our stage.*/
    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>): Unit = with(builder) {
        add(STAGE)
    }

    override fun getRenderShape(p_149645_1_: BlockState): BlockRenderType = BlockRenderType.MODEL

    override fun getShape(
        state: BlockState,
        world: IBlockReader,
        blockPos: BlockPos,
        context: ISelectionContext
    ): VoxelShape {
        if (state.hasProperty(STAGE))
            return state.getValue(STAGE).shape
        return super.getShape(state, world, blockPos, context)
    }

    override fun getCollisionShape(
        state: BlockState,
        world: IBlockReader,
        blockPos: BlockPos,
        context: ISelectionContext
    ): VoxelShape {
        if (state.hasProperty(STAGE))
            return state.getValue(STAGE).shape
        return super.getShape(state, world, blockPos, context)
    }

    override fun hasTileEntity(state: BlockState): Boolean = true

    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity? = ModTiles.AQUEOUS_TILE.create()

    companion object {
        /**Used to track the current stage of the block**/
        val STAGE: EnumProperty<Stage> = EnumProperty.create("stage", Stage::class.java)
    }

    override fun use(
        state: BlockState,
        world: World,
        pos: BlockPos,
        playerEntity: PlayerEntity,
        hand: Hand,
        rayTraceResult: BlockRayTraceResult
    ): ActionResultType {
        if (world.isClientSide) return ActionResultType.SUCCESS //Cause swing on client
        if (playerEntity.isCrouching && playerEntity.getItemInHand(hand).item == ModItems.HAMMER) {
            val newState: BlockState =
                state.setValue(STAGE, Stage.values()[(state.getValue(STAGE).ordinal + 1) % Stage.values().size])
            world.setBlock(pos, newState, 3)
            world.updateNeighborsAt(pos, this)
            world.updateNeighborsAt(pos.below(), this)
            world.setBlocksDirty(pos, state, newState)
            return ActionResultType.SUCCESS
        }
        return ActionResultType.CONSUME
    }

    /**TODO: create drops for  the block state**/
    override fun getDrops(p_220076_1_: BlockState, p_220076_2_: LootContext.Builder): MutableList<ItemStack> {
        return super.getDrops(p_220076_1_, p_220076_2_)
    }

    /**How many parts are made**/
    enum class Stage(
        private val nameIn: String,
        val shape: VoxelShape,
        val sound: SoundType = SoundType.GILDED_BLACKSTONE,
        val light: Int = 0
    ) :
        IStringSerializable {
        Tank(
            "tank", Stream.of(
                box(4.25, 0.0, 4.25, 11.75, 0.25, 11.75),
                box(4.25, 0.0, 4.25, 11.75, 0.25, 11.75),
                box(4.25, 0.0, 11.75, 11.75, 9.0, 12.0),
                box(4.25, 0.0, 4.0, 11.75, 9.0, 4.25),
                box(4.0, 0.0, 4.25, 4.25, 9.0, 11.75),
                box(11.75, 0.0, 4.25, 12.0, 9.0, 11.75)
            ).reduce { v1, v2 ->
                VoxelShapes.join(v1, v2, IBooleanFunction.OR)
            }.get(),
            SoundType.GLASS,
            4
        ),
        Normal(
            "normal", Stream.of(
                box(2.25, 0.0, 2.25, 4.25, 13.0, 4.25),
                box(11.75, 0.0, 2.25, 13.75, 13.0, 4.25),
                box(11.75, 0.0, 11.75, 13.75, 13.0, 13.75),
                box(2.25, 0.0, 11.75, 4.25, 13.0, 13.75),
                box(4.25, 4.0, 4.25, 11.75, 4.25, 11.75),
                box(4.25, 4.0, 11.75, 11.75, 13.0, 12.0),
                box(4.25, 4.0, 4.0, 11.75, 13.0, 4.25),
                box(4.0, 4.0, 4.25, 4.25, 13.0, 11.75),
                box(11.75, 4.0, 4.25, 12.0, 13.0, 11.75),
                box(2.25, 13.5, 2.25, 2.5, 15.25, 13.5),
                box(2.5, 13.5, 13.25, 13.5, 15.25, 13.5),
                box(2.5, 13.5, 2.25, 13.5, 15.25, 2.5),
                box(13.5, 13.5, 2.25, 13.75, 15.25, 13.5),
                box(1.5, 13.0, 1.5, 6.0, 14.0, 14.25),
                box(10.0, 13.0, 1.5, 14.5, 14.0, 14.25),
                box(6.0, 13.0, 1.5, 10.0, 14.0, 6.0),
                box(6.0, 13.0, 10.0, 10.0, 14.0, 14.25)
            ).reduce { v1, v2 ->
                VoxelShapes.join(v1, v2, IBooleanFunction.OR)
            }.get()
        ),
        Input(
            "input",
            Stream.of(
                box(2.25, 0.0, 2.25, 4.25, 13.0, 4.25),
                box(11.75, 0.0, 2.25, 13.75, 13.0, 4.25),
                box(11.75, 0.0, 11.75, 13.75, 13.0, 13.75),
                box(2.25, 0.0, 11.75, 4.25, 13.0, 13.75),
                box(4.25, 4.0, 4.25, 11.75, 4.25, 11.75),
                box(4.25, 4.0, 11.75, 11.75, 13.0, 12.0),
                box(4.25, 4.0, 4.0, 11.75, 13.0, 4.25),
                box(4.0, 4.0, 4.25, 4.25, 13.0, 11.75),
                box(11.75, 4.0, 4.25, 12.0, 13.0, 11.75),
                box(2.25, 13.5, 2.25, 2.5, 15.25, 13.5),
                box(2.5, 13.5, 13.25, 13.5, 15.25, 13.5),
                box(2.5, 13.5, 2.25, 13.5, 15.25, 2.5),
                box(13.5, 13.5, 2.25, 13.75, 15.25, 13.5),
                box(1.5, 13.0, 1.5, 6.0, 14.0, 14.25),
                box(10.0, 13.0, 1.5, 14.5, 14.0, 14.25),
                box(6.0, 13.0, 1.5, 10.0, 14.0, 6.0),
                box(6.0, 13.0, 10.0, 10.0, 14.0, 14.25)
            ).reduce
            { v1, v2 ->
                VoxelShapes.join(v1, v2, IBooleanFunction.OR)
            }.get(),

            ),
        Output(
            "output", Stream.of(
                box(2.25, 0.0, 2.25, 4.25, 13.0, 4.25),
                box(11.75, 0.0, 2.25, 13.75, 13.0, 4.25),
                box(11.75, 0.0, 11.75, 13.75, 13.0, 13.75),
                box(2.25, 0.0, 11.75, 4.25, 13.0, 13.75),
                box(4.25, 4.0, 4.25, 11.75, 4.25, 11.75),
                box(4.25, 4.0, 11.75, 11.75, 13.0, 12.0),
                box(4.25, 4.0, 4.0, 11.75, 13.0, 4.25),
                box(4.0, 4.0, 4.25, 4.25, 13.0, 11.75),
                box(11.75, 4.0, 4.25, 12.0, 13.0, 11.75),
                box(2.25, 13.5, 2.25, 2.5, 15.25, 13.5),
                box(2.5, 13.5, 13.25, 13.5, 15.25, 13.5),
                box(2.5, 13.5, 2.25, 13.5, 15.25, 2.5),
                box(13.5, 13.5, 2.25, 13.75, 15.25, 13.5),
                box(1.5, 13.0, 1.5, 6.0, 14.0, 14.25),
                box(10.0, 13.0, 1.5, 14.5, 14.0, 14.25),
                box(6.0, 13.0, 1.5, 10.0, 14.0, 6.0),
                box(6.0, 13.0, 10.0, 10.0, 14.0, 14.25)
            ).reduce
            { v1, v2 ->
                VoxelShapes.join(v1, v2, IBooleanFunction.OR)
            }.get()
        );

        val hasPump: Boolean get() = this == Normal || this == Input
        val hasInput: Boolean get() = this == Input

        override fun getSerializedName(): String = nameIn
    }
}
