package com.ezcraft.data.client

import com.ezcraft.*
import com.ezcraft.blocks.aqueous.*
import com.ezcraft.registry.*
import net.minecraft.data.*
import net.minecraftforge.client.model.generators.*
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.common.data.*

class ModBlockStateProvider(generator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    BlockStateProvider(
        generator,
        EzMod.ID,
        existingFileHelper
    ) {

    override fun registerStatesAndModels() {
//        getVariantBuilder(ModBlocks.AQUEOUS_LEGS_TANK_COVER.block).forAllStates {
//            val stage = it.getValue(AqueousBlock.STAGE) ?: AqueousBlock.Stage.LegsTankCover
//            ConfiguredModel.builder().modelFile(
//                aqueousBuilder(stage.serializedName)
//            ).build()
//        }
    }

    private fun aqueousBuilder(name: String): ModelBuilder<BlockModelBuilder> =
        models().getBuilder("blocks/aqueous/$name")

}