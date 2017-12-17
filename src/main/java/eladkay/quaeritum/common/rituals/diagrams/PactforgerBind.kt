package eladkay.quaeritum.common.rituals.diagrams

import com.teamwizardry.librarianlib.features.helpers.ItemNBTHelper
import eladkay.quaeritum.api.contract.ContractRegistry
import eladkay.quaeritum.api.rituals.IDiagram
import eladkay.quaeritum.api.rituals.PositionedBlock
import eladkay.quaeritum.api.rituals.PositionedBlockChalk
import eladkay.quaeritum.common.item.ModItems
import eladkay.quaeritum.common.item.oath
import net.minecraft.item.EnumDyeColor
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World



/**
 * @author WireSegal
 * Created at 12:04 AM on 12/17/17.
 */
class PactforgerBind : IDiagram {
    override fun getUnlocalizedName(): String {
        return "pactforger"
    }

    override fun run(world: World, pos: BlockPos, tile: TileEntity) {
        val oathStack = IDiagram.Helper.stacksAroundAltar(tile, 4.0).firstOrNull { it.item == ModItems.scroll } ?: return
        val id = oathStack.oath
        if (id > 0 && id <= ContractRegistry.getMaxId()) {
            val oath = ContractRegistry.getOathFromId(id)
            if (oath != null) {
                val playerUUID = ItemNBTHelper.getUUID(oathStack, "uuid")
                val player = if (playerUUID != null)
                    world.playerEntities.firstOrNull { it.uniqueID == playerUUID }
                else null
                oath.fireContract(player, oathStack, world, pos)
            }
        }
    }

    override fun canRitualRun(world: World?, pos: BlockPos, tile: TileEntity): Boolean {
        return true
    }

    override fun hasRequiredItems(world: World?, pos: BlockPos, tile: TileEntity): Boolean {
        return IDiagram.Helper.stacksAroundAltar(tile, 4.0).any { it.item == ModItems.scroll }
    }

    override fun buildChalks(chalks: MutableList<PositionedBlock>) {
        chalks.add(PositionedBlockChalk(null, BlockPos(-1, 0, -4)))
        chalks.add(PositionedBlockChalk(null, BlockPos(0, 0, -4)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLACK, BlockPos(-3, 0, -3)))
        chalks.add(PositionedBlockChalk(null, BlockPos(-2, 0, -3)))
        chalks.add(PositionedBlockChalk(null, BlockPos(-1, 0, -3)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLACK, BlockPos(0, 0, -3)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLUE, BlockPos(1, 0, -3)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLUE, BlockPos(2, 0, -3)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLACK, BlockPos(3, 0, -3)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.LIGHT_BLUE, BlockPos(-3, 0, -2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.YELLOW, BlockPos(-2, 0, -2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.RED, BlockPos(-1, 0, -2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLUE, BlockPos(0, 0, -2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLUE, BlockPos(1, 0, -2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.YELLOW, BlockPos(2, 0, -2)))
        chalks.add(PositionedBlockChalk(null, BlockPos(3, 0, -2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.LIGHT_BLUE, BlockPos(-3, 0, -1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.LIGHT_BLUE, BlockPos(-2, 0, -1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.PURPLE, BlockPos(-1, 0, -1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.PURPLE, BlockPos(0, 0, -1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.PURPLE, BlockPos(1, 0, -1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.RED, BlockPos(2, 0, -1)))
        chalks.add(PositionedBlockChalk(null, BlockPos(3, 0, -1)))
        chalks.add(PositionedBlockChalk(null, BlockPos(4, 0, -1)))
        chalks.add(PositionedBlockChalk(null, BlockPos(-4, 0, 0)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLACK, BlockPos(-3, 0, 0)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.LIGHT_BLUE, BlockPos(-2, 0, 0)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.PURPLE, BlockPos(-1, 0, 0)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.PURPLE, BlockPos(1, 0, 0)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.GRAY, BlockPos(2, 0, 0)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLACK, BlockPos(3, 0, 0)))
        chalks.add(PositionedBlockChalk(null, BlockPos(4, 0, 0)))
        chalks.add(PositionedBlockChalk(null, BlockPos(-4, 0, 1)))
        chalks.add(PositionedBlockChalk(null, BlockPos(-3, 0, 1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.RED, BlockPos(-2, 0, 1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.PURPLE, BlockPos(-1, 0, 1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.PURPLE, BlockPos(0, 0, 1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.PURPLE, BlockPos(1, 0, 1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.GRAY, BlockPos(2, 0, 1)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.GRAY, BlockPos(3, 0, 1)))
        chalks.add(PositionedBlockChalk(null, BlockPos(-3, 0, 2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.YELLOW, BlockPos(-2, 0, 2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.ORANGE, BlockPos(-1, 0, 2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.ORANGE, BlockPos(0, 0, 2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.RED, BlockPos(1, 0, 2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.YELLOW, BlockPos(2, 0, 2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.GRAY, BlockPos(3, 0, 2)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLACK, BlockPos(-3, 0, 3)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.ORANGE, BlockPos(-2, 0, 3)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.ORANGE, BlockPos(-1, 0, 3)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLACK, BlockPos(0, 0, 3)))
        chalks.add(PositionedBlockChalk(null, BlockPos(1, 0, 3)))
        chalks.add(PositionedBlockChalk(null, BlockPos(2, 0, 3)))
        chalks.add(PositionedBlockChalk(EnumDyeColor.BLACK, BlockPos(3, 0, 3)))
        chalks.add(PositionedBlockChalk(null, BlockPos(0, 0, 4)))
        chalks.add(PositionedBlockChalk(null, BlockPos(1, 0, 4)))
    }
}