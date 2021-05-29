package com.ezcraft.utils

import com.mojang.blaze3d.matrix.*
import com.mojang.blaze3d.vertex.*
import net.minecraft.block.*
import net.minecraft.client.*
import net.minecraft.client.renderer.*
import net.minecraft.client.renderer.model.*
import net.minecraft.inventory.container.*
import net.minecraft.item.*
import net.minecraft.tileentity.*
import net.minecraft.util.math.vector.*
import net.minecraft.util.text.*
import net.minecraftforge.client.model.data.*
import net.minecraftforge.fluids.capability.templates.*
import kotlin.properties.*
import kotlin.reflect.*

/**Used for all kinds of rendering**/
class RenderContext<T : TileEntity>(val type: KClass<T>) {
    var tile by Delegates.notNull<T>()
        private set

    var partialTicks by Delegates.notNull<Float>()
        private set

    var matrix by Delegates.notNull<MatrixStack>()
        private set

    var buffer by Delegates.notNull<IRenderTypeBuffer>()
        private set

    var combinedLight by Delegates.notNull<Int>()
        private set

    var combinedOverall by Delegates.notNull<Int>()
        private set

    /**Adds a block at the given psotion**/
    private fun add(
        renderer: IVertexBuilder,
        stack: MatrixStack,
        x: Float,
        y: Float,
        z: Float,
        u: Float,
        v: Float,
        r: Float,
        g: Float,
        b: Float,
        a: Float,
        light: Int
    ) = renderer.vertex(stack.last().pose(), x, y, z)
        .color(r, g, b, a)
        .uv(u, v)
        .uv2(light)
        .normal(1.0f, 0f, 0f)
        .endVertex()

    /**Renders fluid **/
    fun drawFluid(
        tank: FluidTank,
        min: FloatArray,
        max: FloatArray,
        lightLevel: Int,
        opacity: Float
    ) {
        val block = LightTexture.block(lightLevel)
        val sky = LightTexture.sky(lightLevel)
        val normalized = (block + sky) / 29.0f
        val lightCoords = 240
        //        val lightCoords: Float = (lightLevel)

        if (!tile.isRemoved) {

            val fluid = tank.fluid
            val renderFluid = tank.fluid.fluid ?: return

            val attribs = renderFluid.attributes
            val fluidStill = attribs.getStillTexture(fluid)

            val sprite = Minecraft.getInstance().getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(fluidStill)
            val builder = buffer.getBuffer(RenderType.translucent())
            val fluidScale = tank.fluidAmount.toFloat() / (tank.capacity)
            val rotation = Vector3f.YP.rotationDegrees(0f);
            val color = renderFluid.attributes.color;

            val r = (color shr 16 and 0xFF) / 255.0F;
            val g = (color shr 8 and 0xFF) / 255.0F;
            val b = (color and 0xFF) / 255.0F;
            val minX: Float = (min[0] / 15.0f)
            val minY: Float = (min[1] / 15.0f)
            val minZ: Float = (min[2] / 15.0f)

            val maxX: Float = (max[0] / 15.0f)
            val maxY: Float = ((((fluidScale * (max[1] / 15.0f)))) + minY) - 0.015f
            val maxZ: Float = (max[2] / 15.0f)
            matrix.pushPose();
            matrix.translate(.5, 0.5, .5);
            matrix.mulPose(rotation);

            matrix.translate(-.5, (-0.5), -.5);

            // Top Face
            add(builder, matrix, minX, maxY, maxZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, maxY, maxZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, maxY, minZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, maxY, minZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)

            // Bottom Facmatrix
            add(builder, matrix, maxX, minY, maxZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, maxZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, minZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, minY, minZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)

            // Front FacematrixUTH]
            add(builder, matrix, maxX, maxY, maxZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, maxY, maxZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, maxZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, minY, maxZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)

            add(builder, matrix, maxX, minY, minZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, minZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, maxY, minZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, maxY, minZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)

            // Back Facesf
            add(builder, matrix, maxX, maxY, minZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, maxY, minZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, minZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, minY, minZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)

            add(builder, matrix, maxX, minY, maxZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, maxZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, maxY, maxZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, maxY, maxZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)

            //            // east Facematrix
            add(builder, matrix, maxX, maxY, minZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, maxY, maxZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, minY, maxZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, minY, minZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)

            add(builder, matrix, maxX, maxY, maxZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, maxY, minZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, minY, minZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, maxX, minY, maxZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)

            //            // west Faces
            add(builder, matrix, minX, maxY, maxZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, maxY, minZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, minZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, maxZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)

            add(builder, matrix, minX, maxY, minZ, sprite.u0, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, maxY, maxZ, sprite.u1, sprite.v0, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, maxZ, sprite.u1, sprite.v1, r, g, b, opacity, lightCoords)
            add(builder, matrix, minX, minY, minZ, sprite.u0, sprite.v1, r, g, b, opacity, lightCoords)

            matrix.popPose()
        }
    }

    fun renderItem(
        stack: ItemStack,
        translation: DoubleArray,
        rotation: Quaternion,
        lightLevel: Int,
        scale: Float
    ) {
        matrix.pushPose()
        matrix.translate(translation[0], translation[1], translation[2])
        matrix.mulPose(rotation)
        matrix.scale(scale, scale, scale)

        val model = Minecraft.getInstance().itemRenderer.getModel(stack, null, null)
        Minecraft.getInstance().itemRenderer.render(
            stack,
            ItemCameraTransforms.TransformType.GROUND,
            true,
            matrix,
            buffer,
            lightLevel,
            combinedLight,
            model
        )
        matrix.popPose()
    }

    /**Renders a label in the world**/
    fun renderLabel(
        corner: DoubleArray,
        text: ITextComponent,
        color: Int
    ) {
        matrix.pushPose()
        val fontRenderer = Minecraft.getInstance().font
        val scale = 0.015f
        val f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25f)
        val opacity = (f1 * 255.0f).toInt() shl 24
        val offset = (-fontRenderer.width(text) / 2).toFloat()
        val mat4f = matrix.last().pose()
        matrix.translate(corner[0], corner[1] + 0.4f, corner[2])
        matrix.scale(scale, scale, scale)
        matrix.mulPose(Minecraft.getInstance().entityRenderDispatcher.cameraOrientation())
        matrix.mulPose(Vector3f.ZP.rotationDegrees(180f))
        fontRenderer.drawInBatch(text, offset, 0.0f, color, false, mat4f, buffer, false, opacity, 240)
        matrix.popPose()
    }

    internal fun set(
        tileIn: TileEntity,
        partialTicks: Float,
        stack: MatrixStack,
        buffer: IRenderTypeBuffer,
        combinedLight: Int,
        combinedOverall: Int
    ) {
        if (type.isInstance(tileIn)) this.tile = tileIn as T
        this.partialTicks = partialTicks
        this.matrix = stack
        this.buffer = buffer
        this.combinedLight = combinedLight
        this.combinedOverall = combinedOverall
    }

    companion object {
        /***Caches the renderer contexts**/
        private val renderCache = HashMap<KClass<out TileEntity>, RenderContext<*>>()

        /**Gets the render cache for the given tile entity type or creates a new one**/
        private fun <T : TileEntity> cacheOf(type: KClass<T>): RenderContext<T> =
            if (renderCache.containsKey(type))
                renderCache[type] as RenderContext<T>
            else {
                val cache = RenderContext(type)
                renderCache[type] = cache
                cache
            }

        /**Creates a new context and update it's based upon the **/
        fun <T : TileEntity> begin(
            tile: T,
            partialTicks: Float,
            stack: MatrixStack,
            buffer: IRenderTypeBuffer,
            combinedLight: Int,
            combinedOverall: Int
        ): RenderContext<T> {
            val context = cacheOf(tile::class)
            context.set(tile, partialTicks, stack, buffer, combinedLight, combinedOverall)
            return context as RenderContext<T>
        }
    }

}