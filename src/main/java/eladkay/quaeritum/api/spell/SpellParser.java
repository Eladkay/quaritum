package eladkay.quaeritum.api.spell;

import eladkay.quaeritum.api.spell.BaseAlchemicalSpell.SpellRunnable;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WireSegal
 *         Created at 4:08 AM on 7/2/17.
 */
public final class SpellParser {
    @Nonnull
    private final EnumSpellElement[] symbols;
    @Nonnull
    private final SpellInfo[] spells;

    @Nonnull
    private static final List<IAlchemicalSpell> allSpells = new ArrayList<>();

    /**
     * Register your spell to be parsable.
     * @param spell The spell you're registering.
     */
    public static void registerSpell(@Nonnull IAlchemicalSpell spell) {
        allSpells.add(spell);
    }

    /**
     * Register your spell to be parsable, using the base behavior.
     * @param pattern The spell pattern.
     * @param runnable A callback for what to do when the spell is cast.
     */
    public static void registerSpell(@Nonnull EnumSpellElement[] pattern, @Nonnull SpellRunnable runnable) {
        allSpells.add(new BaseAlchemicalSpell(pattern, runnable));
    }

    private static <T> boolean matches(@Nonnull T[] arr, @Nonnull List<T> list) {
        if (arr.length != list.size()) return false;
        for (int i = 0; i < arr.length; i++)
            if (!arr[i].equals(list.get(i)))
                return false;
        return true;
    }

    @Nonnull
    private static <T> T[] combineArrays(@Nonnull T[] first, @Nonnull T[] second) {
        List<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(first));
        list.addAll(Arrays.asList(second));
        return list.toArray(first);
    }

    /**
     * Takes an array of spell symbols and turns it into castable spells.
     * @param input Spell symbols. While the player's symbol cache is capped at eight, this method has no such limit.
     * @return An array of castable spell objects.
     */
    @Nonnull
    public static SpellInfo[] fromElements(@Nonnull EnumSpellElement[] input) {
        ArrayList<SpellInfo> spells = new ArrayList<>();
        ArrayList<EnumSpellElement> temp = new ArrayList<>();
        for (int i = input.length; i > 0; i--) {
            temp.addAll(Arrays.asList(input).subList(0, i));
            for (IAlchemicalSpell spell : allSpells)
                if (matches(spell.getPattern(), temp)) {
                    int trailing = 0;
                    for (int j = i; i < input.length; i++)
                        if (input[j] == EnumSpellElement.AETHER) trailing++;
                        else break;

                    spells.add(new SpellInfo(spell, trailing));
                    return combineArrays(spells.toArray(new SpellInfo[0]), fromElements(
                            Arrays.asList(input)
                                    .subList(i - 1 + trailing, input.length)
                                    .toArray(new EnumSpellElement[0])));
                }
        }
        return spells.toArray(new SpellInfo[0]);
    }

    /**
     * Construct a new parser object from a symbol cache.
     * @param symbols The symbol cache to use.
     */
    public SpellParser(@Nonnull EnumSpellElement[] symbols) {
        this.symbols = symbols;
        this.spells = fromElements(symbols);
    }

    /**
     * Fires, in order, each spell contained within the parsed spell.
     * @param player The player casting the spell.
     */
    public void cast(@Nonnull EntityPlayer player) {
        for (SpellInfo spell : spells)
            spell.getSpell().performEffect(player, spell.getTrailingAether(), spells.length);
    }

    @Nonnull
    public EnumSpellElement[] getSymbols() {
        return symbols;
    }
}
