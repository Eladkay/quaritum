package eladkay.quaritum.common.block.chalk;

import com.google.common.collect.Lists;
import eladkay.quaritum.common.block.base.BlockModColored;
import eladkay.quaritum.common.item.ModItems;
import eladkay.quaritum.common.lib.LibNames;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockChalk extends BlockModColored {

    protected static final AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    public BlockChalk() {
        super(LibNames.CHALK_BLOCK, Material.PISTON);
        setHardness(0.5f);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CARPET_AABB;
    }

    @Override
    public MapColor getMapColor(IBlockState state) {
        return MapColor.CLAY;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.inventory.getCurrentItem() != null && playerIn.inventory.getCurrentItem().getItem() == ModItems.chalk) {
            worldIn.setBlockState(pos, getStateFromMeta(playerIn.inventory.getCurrentItem().getItemDamage()));
            return playerIn.inventory.getCurrentItem().getItemDamage() != getMetaFromState(state);
        }
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ModItems.chalk, 1, getMetaFromState(state));
    }

    private boolean canBlockStay(World worldIn, BlockPos pos) {
        return !worldIn.isAirBlock(pos.down());
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP || blockAccess.getBlockState(pos.offset(side)).getBlock() == this || super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public boolean shouldHaveItemForm() {
        return false;
    }
}
