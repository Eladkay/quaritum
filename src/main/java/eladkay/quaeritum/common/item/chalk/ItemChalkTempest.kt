package eladkay.quaeritum.common.item.chalk

import com.teamwizardry.librarianlib.client.util.TooltipHelper
import com.teamwizardry.librarianlib.common.base.item.ItemMod
import com.teamwizardry.librarianlib.common.util.ItemNBTHelper
import eladkay.quaeritum.api.lib.LibNBT
import eladkay.quaeritum.common.block.ModBlocks

import eladkay.quaeritum.common.lib.LibLocations
import eladkay.quaeritum.common.lib.LibNames
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ItemChalkTempest : ItemMod(LibNames.CHALK_TEMPEST) {
    init {
        addPropertyOverride(LibLocations.FLAT_CHALK) { stack, world, entityLivingBase -> if (ItemNBTHelper.getBoolean(stack.copy(), LibNBT.FLAT, false)) 1.0f else 0.0f }
        setMaxStackSize(1)
    }

    override fun onItemUse(stack: ItemStack?, player: EntityPlayer?, world: World?, blockPos: BlockPos?, hand: EnumHand?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        var blockPos = blockPos
        val iblockstate = world!!.getBlockState(blockPos!!)
        val block = iblockstate.block

        if (!block.isReplaceable(world, blockPos)) {
            blockPos = blockPos.offset(side!!)
        }

        if (stack!!.stackSize != 0 && player!!.canPlayerEdit(blockPos!!, side!!, stack) && world.canBlockBePlaced(ModBlocks.tempest, blockPos, false, side, null, stack)) {
            val i = stack.metadata
            val iblockstate1 = ModBlocks.tempest.onBlockPlaced(world, blockPos, side, hitX, hitY, hitZ, i, player)

            if (placeBlockAt(stack, player, world, blockPos, side, hitX, hitY, hitZ, iblockstate1)) {
                val soundtype = ModBlocks.tempest.soundType
                world.playSound(player, blockPos, soundtype.placeSound, SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f)
            }

            return EnumActionResult.SUCCESS
        } else {
            return EnumActionResult.FAIL
        }
    }

    override fun addInformation(stack: ItemStack, playerIn: EntityPlayer?, tooltip: MutableList<String>, advanced: Boolean) {
        TooltipHelper.addToTooltip(tooltip, getUnlocalizedName(stack) + ".desc")
    }

    private fun placeBlockAt(stack: ItemStack, player: EntityPlayer, world: World, pos: BlockPos, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, newState: IBlockState): Boolean {
        if (!world.setBlockState(pos, newState, 3)) return false

        val state = world.getBlockState(pos)
        if (state.block === ModBlocks.tempest) {
            ItemBlock.setTileEntityNBT(world, player, pos, stack)
            ModBlocks.tempest.onBlockPlacedBy(world, pos, state, player, stack)
        }

        return true
    }
}