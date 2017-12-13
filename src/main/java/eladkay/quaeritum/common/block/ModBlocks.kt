package eladkay.quaeritum.common.block

import eladkay.quaeritum.api.lib.LibMisc
import eladkay.quaeritum.api.rituals.PositionedBlockChalk
import eladkay.quaeritum.common.block.chalk.BlockChalk
import eladkay.quaeritum.common.block.chalk.BlockChalkTempest
import eladkay.quaeritum.common.block.flowers.BlockFiresoulFlower
import eladkay.quaeritum.common.block.flowers.BlockIronheartCrop
import eladkay.quaeritum.common.block.flowers.BlockIronheartFlower
import eladkay.quaeritum.common.block.machine.*
import eladkay.quaeritum.common.block.tile.TileEntityBlueprint
import eladkay.quaeritum.common.block.tile.TileEntityFoundationStone
import eladkay.quaeritum.common.lib.LibNames
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * @author WireSegal
 * *         Created at 5:00 PM on 4/16/16.
 */
object ModBlocks {
    val blueprint = BlockBlueprint(LibNames.BLUEPRINT)
    val chalk = BlockChalk()
    val flower = BlockFiresoulFlower()
    val crystal = CrystalSoul()
    val foundation = BlockFoundationStone()
    val tempest = BlockChalkTempest()

    val centrifuge = BlockCentrifuge()
    val compoundCrucible = BlockCompoundCrucible()
    val spiralDistillate = BlockSpiralDistillate()
    val desiccator = BlockDesiccator()
    val fluidHolder = BlockFluidHolder()
    val jet = BlockFluidJet()

    val ironCrop = BlockIronheartCrop()
    val ironheart = BlockIronheartFlower()

    init {
        GameRegistry.registerTileEntity(TileEntityBlueprint::class.java, ResourceLocation(LibMisc.MOD_ID, LibNames.BLUEPRINT).toString())
        GameRegistry.registerTileEntity(TileEntityFoundationStone::class.java, ResourceLocation(LibMisc.MOD_ID, LibNames.FOUNDATION).toString())

        PositionedBlockChalk.chalk = chalk
        PositionedBlockChalk.tempest = tempest
    }
}
