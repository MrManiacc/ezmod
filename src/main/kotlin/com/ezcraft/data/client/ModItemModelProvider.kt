package com.ezcraft.data.client

import com.ezcraft.*
import net.minecraft.data.*
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.*

class ModItemModelProvider(generator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    ItemModelProvider(
        generator,
        EzMod.ID,
        existingFileHelper
    ) {

    override fun registerModels() {
        withExistingParent("aqueous", modLoc("block/aqueous/legs_tank_cover"))

    }
}