package com.ezcraft.blocks.aqueous

import com.ezcraft.registry.*
import com.ezcraft.utils.*
import com.mojang.blaze3d.matrix.*
import net.minecraft.client.*
import net.minecraft.client.renderer.*
import net.minecraft.client.renderer.tileentity.*
import net.minecraft.util.math.*
import net.minecraft.util.text.*
import java.text.*
import java.util.*
import kotlin.math.*

class AqueousChamberRenderer(it: TileEntityRendererDispatcher?) : TileEntityRenderer<AqueousTile>(it) {
    override fun render(
        tile: AqueousTile,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        combinedLight: Int,
        combinedOverallLight: Int
    ) {

        with(RenderContext.begin(tile, partialTicks, matrixStack, buffer, combinedLight, combinedOverallLight)) {
            if (shouldRenderInfo(this))
                renderInfo(this)
            //            if (shouldRenderLiquids())
        }
    }

    /**Renders the info if shift crouching and looking at it**/
    private fun shouldRenderInfo(ctx: RenderContext<AqueousTile>): Boolean {
        ctx.tile.level?.let { world ->
            val player = Minecraft.getInstance().player ?: return false
            Minecraft.getInstance().hitResult?.location?.let {
                var bx = it.x
                var by = it.y
                var bz = it.z
                val px = player.x
                val py = player.y
                val pz = player.z
                if (bx == floor(bx) && bx <= px)
                    bx -= 1
                if (by == floor(by) && by <= py + 1)
                    by -= 1
                if (bz == floor(bz) && bz < pz)
                    bz -= 1
                val pos = BlockPos(bx, by, bz)
                if (ctx.tile.blockPos == pos)
                    return !player.isCrouching && player.mainHandItem.item == ModItems.HAMMER
            }
        }
        return false
    }

    /**Renders the info if shift crouching and looking at it**/
    private fun renderInfo(ctx: RenderContext<AqueousTile>) {
        val tank = ctx.tile.tankHandler
        val capacity = NumberFormat.getNumberInstance(Locale.US).format(tank.capacity)
        val stored = NumberFormat.getNumberInstance(Locale.US).format(tank.fluidAmount)
        ctx.renderLabel(
            doubleArrayOf(0.5, 1.3, 0.5),
            StringTextComponent("Fluid: ${tank.fluidName}"),
            0xffffff
        )
        ctx.renderLabel(

            doubleArrayOf(0.5, 1.15, 0.5),
            StringTextComponent("Capacity: ${capacity}mb"),
            0xe0144b
        )
        ctx.renderLabel(
            doubleArrayOf(0.5, 1.0, 0.5),
            StringTextComponent("Stored: ${stored}mb"),
            0xe0144b
        )
    }

}
