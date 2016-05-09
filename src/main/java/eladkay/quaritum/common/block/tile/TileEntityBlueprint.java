package eladkay.quaritum.common.block.tile;

import com.google.common.collect.Lists;
import eladkay.quaritum.api.rituals.IDiagram;
import eladkay.quaritum.api.rituals.PositionedBlock;
import eladkay.quaritum.api.rituals.RitualRegistry;
import eladkay.quaritum.common.block.ModBlocks;
import eladkay.quaritum.common.core.PositionedBlockHelper;
import eladkay.quaritum.common.lib.LibMisc;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TileEntityBlueprint extends TileEntity implements IInventory, ITickable {
    public boolean debug = true;
    public ArrayList<ItemStack> items = Lists.newArrayList();
    public boolean debug2 = true;

    @Override
    public void update() {
       boolean dirty = false;
        if (!worldObj.isRemote) {
            List<EntityItem> eitems = worldObj.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.getPos(), getPos().add(1, 1, 1)));
            for (EntityItem item : eitems)
                if (!item.isDead && item.getEntityItem() != null) {
                    ItemStack stack = item.getEntityItem();
                    items.add(stack);
                    item.setDead();
                    dirty = true;
                }
        }
        if(dirty) markDirty();
    }


    private IDiagram getValidRitual(EntityPlayer player) {
        for (IDiagram ritual : RitualRegistry.getDiagramList()) {
            boolean foundAll = items.toString().equals(ritual.getRequiredItems().toString()); //FOR NOW.
            boolean requirementsMet = ritual.canRitualRun(this.getWorld(), player, pos, this);
            //boolean chalks = checkChalk(ritual.buildChalks(Lists.newArrayList()));
            //boolean chalks = worldObj.getBlockState(pos.offset(EnumFacing.UP)).equals(ModBlocks.chalk.getStateFromMeta(EnumDyeColor.BLUE.ordinal()));
            boolean chalks = PositionedBlockHelper.isChalkSetupValid(ritual.buildChalks(Lists.newArrayList()), this);
            if (foundAll && requirementsMet && chalks) {
                return ritual;
            } else if (!requirementsMet) {
                System.out.println("REQUIREMENTS FOR RITUAL " + ritual.getUnlocalizedName() + " WERE NOT MET.");
            } else if (!foundAll) {
                System.out.println("ITEM REQUIREMENTS FOR RITUAL " + ritual.getUnlocalizedName() + " WERE NOT MET. REQUIREMENTS ARE " + ritual.getRequiredItems() + " & THERE IS " + items);
            } else if (!chalks) {
                System.out.println("CHALK REQUIREMENTS FOR RITUAL " + ritual.getUnlocalizedName() + " WERE NOT MET. REQUIREMENTS ARE " + ritual.getRequiredItems() + " & THERE IS " + items);
            } else {
                System.out.println("GENERIC ERROR AT " + ritual.getUnlocalizedName());
            }
        }
        return null;
    }

    private boolean runRitual(IDiagram ritual, EntityPlayer player) {
        if (ritual != null) {
            clear();
            return ritual.run(worldObj, player, pos);
        } else
            return false;


    }

    private boolean checkChalk(List<PositionedBlock> list) {
        for (PositionedBlock block : list) {
            IBlockState state = worldObj.getBlockState(PositionedBlockHelper.blockPosSum(getPos(), block.getPos()));
            if (state.getBlock() != ModBlocks.chalk) {
                System.out.println("BLOCK");
                return false;
            }
            if (!state.equals(block.getState())) {
                System.out.println("CHALK");
                return false;
            }
        }
        return true;
    }


    @Override
    public int getSizeInventory() {
        return LibMisc.INVENTORY_SIZE_BLUEPRINT;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return items.set(index, new ItemStack(items.get(index).getItem(), items.get(index).stackSize - 1, items.get(index).getMetadata()));
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return items.set(index, null);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        items.set(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return LibMisc.INVENTORY_SIZE_BLUEPRINT;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        items = Lists.newArrayList();
    }

    @Override
    public String getName() {
        return "tile.quaritum.blueprint";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("Blueprint");
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        return runRitual(getValidRitual(playerIn), playerIn);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.items = Lists.newArrayList();

        NBTTagList nbttaglist = compound.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < this.items.size()) {
                this.items.set(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
            }
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.items.size(); ++i) {
            if (this.items.get(i) != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.items.get(i).writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", nbttaglist);

    }
}
