package eladkay.quaritum.common.block

import amerifrance.guideapi.api.IGuideLinked
import amerifrance.guideapi.api.IPage
import amerifrance.guideapi.page.PageText
import com.teamwizardry.librarianlib.client.util.TooltipHelper
import com.teamwizardry.librarianlib.common.base.block.BlockMod
import com.teamwizardry.librarianlib.common.base.block.ItemModBlock
import eladkay.quaritum.api.lib.LibBook
import eladkay.quaritum.api.lib.LibMisc
import eladkay.quaritum.api.lib.LibNBT
import eladkay.quaritum.api.util.ItemNBTHelper
import eladkay.quaritum.common.block.tile.TileEntityBlueprint
import eladkay.quaritum.common.book.ModBook
import eladkay.quaritum.common.lib.LibLocations
import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import java.util.*

class BlockBlueprint(name: String) : BlockMod(name, Material.PISTON), IGuideLinked, ITileEntityProvider {

    init {
        setHardness(1.2f)
        constructBook()
    }

    override fun createItemForm(): ItemBlock? {
        val item = ItemModBlock(this)
        item.addPropertyOverride(LibLocations.FLAT_CHALK) { stack, world, entityLivingBase -> if (ItemNBTHelper.getBoolean(stack.copy(), LibNBT.FLAT, false)) 1.0f else 0.0f }
        return item
    }

    override fun getBoundingBox(state: IBlockState?, source: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
        return BOUNDS
    }

    override fun isOpaqueCube(state: IBlockState?): Boolean {
        return false
    }

    override fun isFullyOpaque(state: IBlockState): Boolean {
        return false
    }

    override fun isFullBlock(state: IBlockState?): Boolean {
        return false
    }

    override fun isFullCube(state: IBlockState?): Boolean {
        return false
    }

    override fun isBlockSolid(worldIn: IBlockAccess, pos: BlockPos, side: EnumFacing?): Boolean {
        return true
    }

    override fun onBlockActivated(worldIn: World?, pos: BlockPos?, state: IBlockState?, playerIn: EntityPlayer?, hand: EnumHand?, heldItem: ItemStack?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        //Quaritum.proxy.spawnStafflikeParticles(worldIn, pos.getX(), pos.getY() + 1, pos.getZ());
        val tile = worldIn!!.getTileEntity(pos!!)
        return tile is TileEntityBlueprint && tile.onBlockActivated()
    }

    override fun onNeighborChange(world: IBlockAccess?, pos: BlockPos?, neighbor: BlockPos?) {
        if ((world as World).isBlockPowered(pos!!)) {
            val tile = world.getTileEntity(pos)
            if (tile is TileEntityBlueprint) tile.onBlockActivated()
        }
    }

    override fun neighborChanged(state: IBlockState?, worldIn: World?, pos: BlockPos?, blockIn: Block?) {
        if (worldIn!!.isBlockPowered(pos!!)) {
            val tile = worldIn.getTileEntity(pos)
            if (tile is TileEntityBlueprint) tile.onBlockActivated()
        }
    }

    override fun getLinkedEntry(world: World, pos: BlockPos, player: EntityPlayer, stack: ItemStack): ResourceLocation {
        return ResourceLocation(LibMisc.MOD_ID, LibBook.ENTRY_BLUEPRINT_NAME)
    }

    fun constructBook() {
        pages.add(PageText(TooltipHelper.local(LibBook.ENTRY_BLUEPRINT_PAGE1)))
        ModBook.register(ModBook.pagesAnimus, LibBook.ENTRY_BLUEPRINT_NAME, pages, ItemStack(ModBlocks.blueprint))
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity {
        return TileEntityBlueprint()
    }

    companion object {

        val BOUNDS = AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0)
        var pages: MutableList<IPage> = ArrayList()
    }
}
