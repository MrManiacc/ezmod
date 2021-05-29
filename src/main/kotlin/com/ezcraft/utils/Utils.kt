package com.ezcraft.utils

import net.minecraft.util.text.*
import net.minecraftforge.fluids.capability.templates.*

/**Creates a container translation key**/
val FluidTank.fluidName: String
    get() = if (this.fluid.displayName != null) this.fluid.displayName.string else
        TranslationTextComponent(this.fluid.translationKey).string
