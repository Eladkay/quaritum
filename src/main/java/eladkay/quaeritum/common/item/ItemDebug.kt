package eladkay.quaeritum.common.item

import com.teamwizardry.librarianlib.features.base.item.ItemMod
import com.teamwizardry.librarianlib.features.kotlin.plus
import com.teamwizardry.librarianlib.features.kotlin.sendSpamlessMessage
import eladkay.quaeritum.api.animus.AnimusHelper
import eladkay.quaeritum.api.animus.EnumAnimusTier
import eladkay.quaeritum.common.Quaeritum
import eladkay.quaeritum.common.block.ModBlocks
import eladkay.quaeritum.common.block.base.BlockModColored

import eladkay.quaeritum.common.lib.LibNames
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World

class ItemDebug : ItemMod(LibNames.DEBUG) {

    init {
        setMaxStackSize(1)
    }

    override fun onItemRightClick(worldIn: World?, playerIn: EntityPlayer, hand: EnumHand?): ActionResult<ItemStack> {
        if (!worldIn!!.isRemote)
        //this doesn't work if not inverted and it doesn't matter anyways because this debug item only exists in a dev environment
            if (GuiScreen.isShiftKeyDown()) {
                AnimusHelper.Network.addAnimus(playerIn, 50)
                AnimusHelper.Network.addTier(playerIn, EnumAnimusTier.QUAERITUS)
                playerIn.sendSpamlessMessage("Added 50, current animus level for " + playerIn.name + " is: " + AnimusHelper.Network.getAnimus(playerIn), 10005)
            } else if (GuiScreen.isCtrlKeyDown()) {
                AnimusHelper.Network.addAnimus(playerIn, -50)
                playerIn.sendSpamlessMessage("Took 50, current animus level for " + playerIn.name + " is: " + AnimusHelper.Network.getAnimus(playerIn), 10005)
            } else
                playerIn.sendSpamlessMessage("Current animus level for " + playerIn.name + " is: " + AnimusHelper.Network.getAnimus(playerIn) + " and current rarity is " + AnimusHelper.Network.getTier(playerIn), 10005)

        return super.onItemRightClick(worldIn, playerIn, hand)
    }

    /*@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(RayHelper.raycast(entityIn, 32) == null || !isSelected) return;
        BlockPos pos = RayHelper.raycast(entityIn, 32).getBlockPos();
        IBlockState state = worldIn.getBlockState(pos);
        if(state.getBlock() == ModBlocks.blueprint)
            renderAABB(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), pos.getX() - 4, pos.getY(), pos.getZ() - 4, pos.getX() + 4, pos.getY(), pos.getZ() + 4, 255, 223, 127);
         else if(state.getBlock() == ModBlocks.foundation)
            renderAABB(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), pos.getX() - 12, pos.getY() + 1, pos.getZ() - 12, pos.getX() + 12, pos.getY() + 25, pos.getZ() + 12, 255, 223, 127);
    }*/

    override fun onItemUse(playerIn: EntityPlayer?, worldIn: World?, pos: BlockPos?, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (playerIn!!.isSneaking) {
            var out = ""
            var flag = false
            val state = worldIn!!.getBlockState(pos!!)
            if (state.block === ModBlocks.blueprint) {
                for (shift in BlockPos.getAllInBox(BlockPos(-4, 0, -4), BlockPos(4, 0, 4))) {
                    val bpos = pos.add(shift)
                    val bstate = worldIn.getBlockState(bpos)
                    if (bstate.block === ModBlocks.chalk) {
                        if (flag) out += "\n"
                        flag = true
                        out += "chalks.add(new PositionedBlockChalk(EnumDyeColor." + bstate.getValue(BlockModColored.COLOR).name + ", new BlockPos(" + shift.x + ", " + shift.y + ", " + shift.z + ")));"
                    } else if (bstate.block === ModBlocks.tempest) {
                        if (flag) out += "\n"
                        flag = true
                        out += "chalks.add(new PositionedBlockChalk(null, new BlockPos(" + shift.x + ", " + shift.y + ", " + shift.z + ")));"
                    }
                }
                if (out != "")
                    Quaeritum.proxy.copyText(out)
                if (!worldIn.isRemote) {
                    if (out != "")
                        playerIn.sendMessage(TextComponentString(TextFormatting.GREEN + "Copied Diagrammatic information to clipboard"))
                    else
                        playerIn.sendMessage(TextComponentString(TextFormatting.RED + "Output is empty!"))
                }
            } else if (state.block === ModBlocks.foundation) {
                for (shift in BlockPos.getAllInBox(BlockPos(-12, 1, -12), BlockPos(12, 25, 12))) {
                    val bpos = pos.add(shift)
                    val bstate = worldIn.getBlockState(bpos)
                    if (bstate.block === ModBlocks.chalk) {
                        if (flag) out += "\n"
                        flag = true
                        out += "chalks.add(new PositionedBlockChalk(EnumDyeColor." + bstate.getValue(BlockModColored.COLOR).name + ", new BlockPos(" + shift.x + ", " + shift.y + ", " + shift.z + ")));"
                    } else if (bstate.block === ModBlocks.tempest) {
                        if (flag) out += "\n"
                        flag = true
                        out += "chalks.add(new PositionedBlockChalk(null, new BlockPos(" + shift.x + ", " + shift.y + ", " + shift.z + ")));"
                    } else if (bstate.block !== Blocks.AIR) {
                        if (flag) out += "\n"
                        flag = true
                        if (bstate.block.registryName!!.namespace == "minecraft")
                            out += "chalks.add(new PositionedBlock(Blocks." + bstate.block.registryName!!.path.toUpperCase() + ".getDefaultState(), new BlockPos(" + shift.x + ", " + shift.y + ", " + shift.z + "), null)); // " + bstate
                        else
                            out += "chalks.add(new PositionedBlock(UNKNOWN, new BlockPos(" + shift.x + ", " + shift.y + ", " + shift.z + "), null); // " + bstate
                    }
                }
                if (out != "")
                    Quaeritum.proxy.copyText(out)
                if (!worldIn.isRemote) {
                    if (out != "")
                        playerIn.sendMessage(TextComponentString(TextFormatting.GREEN + "Copied Work information to clipboard"))
                    else
                        playerIn.sendMessage(TextComponentString(TextFormatting.RED + "Output is empty!"))
                }
            }
        }
        return EnumActionResult.SUCCESS
    }

}
