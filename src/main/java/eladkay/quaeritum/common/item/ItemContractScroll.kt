package eladkay.quaeritum.common.item

import com.teamwizardry.librarianlib.core.LibrarianLib
import com.teamwizardry.librarianlib.features.base.item.IItemColorProvider
import com.teamwizardry.librarianlib.features.base.item.ItemMod
import com.teamwizardry.librarianlib.features.helpers.ItemNBTHelper
import com.teamwizardry.librarianlib.features.kotlin.isNotEmpty
import com.teamwizardry.librarianlib.features.kotlin.sendSpamlessMessage
import com.teamwizardry.librarianlib.features.utilities.client.TooltipHelper
import eladkay.quaeritum.api.animus.AnimusHelper
import eladkay.quaeritum.api.animus.EnumAnimusTier
import eladkay.quaeritum.api.contract.ContractRegistry
import eladkay.quaeritum.api.rituals.IDiagram
import eladkay.quaeritum.common.block.ModBlocks
import eladkay.quaeritum.common.block.base.BlockModColored
import eladkay.quaeritum.common.lib.LibNames
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.*
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.oredict.OreIngredient
import net.minecraftforge.registries.IForgeRegistryEntry

/**
 * @author WireSegal
 * Created at 5:33 PM on 2/26/17.
 */
class ItemContractScroll : ItemMod(LibNames.SCROLL, LibNames.SCROLL, LibNames.SEALED_SCROLL), IItemColorProvider {
    companion object {
        init {
            ContractRegistry.registerOath("ea", 4)
            ContractRegistry.registerOath("seven", 4)
            ContractRegistry.registerOath("innocentia", 4, { _, _, world, pos ->
                val chalkState = world.getBlockState(pos.add(3, 0, 3))
                val rot = chalkState.block == ModBlocks.chalk && chalkState.getValue(BlockModColored.COLOR) == EnumDyeColor.BROWN
                val items = IDiagram.Helper.entitiesAroundAltar(world.getTileEntity(pos), 4.0)
                if (IDiagram.Helper.matches(items.map { it.item }, mutableListOf(
                        ItemEssence.stackOf(EnumAnimusTier.VERDIS),
                        ItemEssence.stackOf(EnumAnimusTier.LUCIS),
                        ItemEssence.stackOf(EnumAnimusTier.FERRUS),
                        ItemEssence.stackOf(EnumAnimusTier.ARGENTUS),
                        ItemEssence.stackOf(EnumAnimusTier.ATLAS)
                ))) {
                    items.forEach {
                        if (it.item.item !is ItemContractScroll) {
                            it.setDead()
                            (world as WorldServer).spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                                    it.posX, it.posY + 0.25, it.posZ, 10, 0.1, 0.0, 0.1, 0.0)
                        }
                    }
                    val output = EntityItem(world, pos.x + 0.5, pos.y + 1.0, pos.z + 0.5,
                            if (rot) ItemStack(ModItems.hiddenBook) else ItemEssence.stackOf(EnumAnimusTier.QUAERITUS)
                    )
                    output.motionX = 0.0
                    output.motionZ = 0.0
                    output.setNoGravity(true)
                    world.spawnEntity(output)
                    (world as WorldServer).spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                            output.posX, output.posY + 0.25, output.posZ, 10, 0.1, 0.0, 0.1, 0.0)
                }
            })
        }
    }

    override fun getEntityLifespan(itemStack: ItemStack?, world: World?): Int {
        return Int.MAX_VALUE
    }

    override fun getItemStackDisplayName(stack: ItemStack): String {
        val id = stack.oath
        if (id > 0 && id <= ContractRegistry.getMaxId()) {
            val oath = ContractRegistry.getOathFromId(id)
            if (oath != null) {
                return LibrarianLib.PROXY.translate(oath.getUnlocName(stack))
            }
        }
        return super.getItemStackDisplayName(stack)
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        val id = stack.oath
        if (id > 0 && id <= ContractRegistry.getMaxId()) {
            val oath = ContractRegistry.getOathFromId(id)
            if (oath != null) {
                for (line in oath.getUnlocText(stack))
                    TooltipHelper.addToTooltip(tooltip, line)
            }
        }
    }

    override val itemColorFunction: ((ItemStack, Int) -> Int)?
        get() = { _, i ->
            if (i == 1)
                AnimusHelper.Network.getAnimusColor(LibrarianLib.PROXY.getClientPlayer()) // Temporary, will be done via animus binding
            else -1
        }

    /*
        May secrets be kept in the shadows of light,
        may truth be found in the apex of night.
        May life flow through all flesh and blood,
        through soul and storm, let our hope be a flood.
     */
    val WORDS_OF_AGES = 0x2F0E38FE

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val itemStackIn = playerIn.getHeldItem(hand)

        if (!playerIn.isSneaking)
            return ActionResult(EnumActionResult.PASS, itemStackIn)

        if (!worldIn.isRemote && itemStackIn.itemDamage == 0) {
            val oathIndex = itemStackIn.oath
            var newOathIndex = (oathIndex + 1) % ContractRegistry.getMaxId()
            var newOath = ContractRegistry.getOathFromId(newOathIndex)!!
            while (!newOath.unlocked(playerIn)) {
                newOathIndex = (newOathIndex + 1) % ContractRegistry.getMaxId()
                newOath = ContractRegistry.getOathFromId(newOathIndex)!!
            }
            itemStackIn.oath = newOathIndex
            ItemNBTHelper.setUUID(itemStackIn, "uuid", playerIn.uniqueID)
            val component = TextComponentTranslation(newOath.getUnlocName(itemStackIn)).setStyle(Style().setBold(true))
            for (line in newOath.getUnlocText(itemStackIn)) {
                component.appendSibling(TextComponentString("\n | ").setStyle(Style().setBold(false)))
                component.appendSibling(TextComponentTranslation(line).setStyle(Style().setBold(false)))
            }

            playerIn.sendSpamlessMessage(component, WORDS_OF_AGES)
        }

        return ActionResult(EnumActionResult.SUCCESS, itemStackIn)
    }

    override fun getSubItems(tab: CreativeTabs?, subItems: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab))
            subItems.add(ItemStack(this))
    }

}

var ItemStack.oath: Int
    get() = ItemNBTHelper.getInt(this, "oath", -1)
    set(value) = if (value == -1) ItemNBTHelper.removeEntry(this, "oath") else ItemNBTHelper.setInt(this, "oath", value)

object SigningRecipe : IForgeRegistryEntry.Impl<IRecipe>(), IRecipe {

    val dye = OreIngredient("dyeBlack")
    val scroll: Ingredient = Ingredient.fromStacks(ItemStack(ModItems.scroll))
    val feather = OreIngredient("feather")

    override fun canFit(width: Int, height: Int): Boolean {
        return true
    }

    override fun getRecipeOutput(): ItemStack {
        return ItemStack(ModItems.scroll, 1, 1)
    }

    override fun getCraftingResult(inv: InventoryCrafting): ItemStack {
        var foundDye = ItemStack.EMPTY
        var foundScroll = ItemStack.EMPTY
        var foundFeather = ItemStack.EMPTY
        for (i in 0 until inv.sizeInventory) {
            val stack = inv.getStackInSlot(i)
            if (stack.isNotEmpty) {
                if (dye.test(stack)) {
                    if (foundDye.isNotEmpty) return ItemStack.EMPTY
                    foundDye = stack
                } else if (scroll.test(stack)) {
                    if (foundScroll.isNotEmpty) return ItemStack.EMPTY
                    foundScroll = stack
                } else if (feather.test(stack)) {
                    if (foundFeather.isNotEmpty) return ItemStack.EMPTY
                    foundFeather = stack
                } else return ItemStack.EMPTY
            }
        }

        val result = foundScroll.copy()
        result.itemDamage = 1
        return result
    }

    override fun matches(inv: InventoryCrafting, worldIn: World?): Boolean {
        var foundDye = false
        var foundScroll = false
        var foundFeather = false
        for (i in 0 until inv.sizeInventory) {
            val stack = inv.getStackInSlot(i)
            if (stack.isNotEmpty) {
                if (dye.test(stack)) {
                    if (foundDye) return false
                    foundDye = true
                } else if (scroll.test(stack)) {
                    if (foundScroll) return false
                    foundScroll = true
                } else if (feather.test(stack)) {
                    if (foundFeather) return false
                    foundFeather = true
                } else return false
            }
        }
        return foundDye && foundScroll && foundFeather
    }

    override fun getIngredients(): NonNullList<Ingredient> {
        val list: NonNullList<Ingredient> = NonNullList.create()
        list.add(dye)
        list.add(scroll)
        list.add(feather)
        return list
    }
}
