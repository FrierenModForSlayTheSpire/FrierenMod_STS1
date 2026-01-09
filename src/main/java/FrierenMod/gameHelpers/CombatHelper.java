package FrierenMod.gameHelpers;

import FrierenMod.cardMods.DamageMod;
import FrierenMod.cardMods.ExhaustEtherealMod;
import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.cards.magicItems.AbstractMagicItem;
import FrierenMod.cards.magicItems.factors.BluePrint;
import FrierenMod.cards.tempCards.SpecializedOffensiveMagic;
import FrierenMod.patches.fields.GameActionManagerField;
import FrierenMod.patches.fields.MagicDeckField;
import FrierenMod.powers.*;
import FrierenMod.powers.FusionPower.AbstractFusionPower;
import FrierenMod.powers.FusionPower.DamageFusionPower;
import FrierenMod.relics.FakeFlammeGrimoire;
import FrierenMod.relics.FlammeGrimoire;
import FrierenMod.utils.Config;
import FrierenMod.utils.Log;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.HashSet;

public class CombatHelper {
    public static boolean isInCombat() {
        return (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT);
    }

    public static int getManaNumInDrawPile() {
        int counts = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.hasTag(AbstractBaseCard.Enum.MANA)) {
                counts++;
            }
        }
        return counts;
    }

    public static int getManaNumInHand() {
        int counts = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(AbstractBaseCard.Enum.MANA)) {
                counts++;
            }
        }
        return counts;
    }

    public static int getManaNumInDiscardPile() {
        int counts = 0;
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.hasTag(AbstractBaseCard.Enum.MANA)) {
                counts++;
            }
        }
        return counts;
    }

    public static int getManaNumInExhaustPile() {
        int counts = 0;
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.hasTag(AbstractBaseCard.Enum.MANA)) {
                counts++;
            }
        }
        return counts;
    }

    public static int getAllManaNum() {
        return getManaNumInDrawPile() + getManaNumInHand() + getManaNumInDiscardPile();
    }

    public static boolean cannotChant(int chantX) {
        return !canActivateSlot(chantX, 0) && !canActivateSlot(chantX, 1) && !canActivateSlot(chantX, 2);
    }

    public static boolean canActivateSlot(int chantX, int slotNumber) {
        int manaNum;
        switch (slotNumber) {
            case 0:
                manaNum = getManaNumInDrawPile();
                break;
            case 1:
                manaNum = getManaNumInHand();
                break;
            case 2:
                manaNum = getManaNumInDiscardPile();
                break;
            default:
                manaNum = -1;
                Log.logger.info("NO SUCH SLOT NUMBER: {}", slotNumber);
                break;
        }
        return getChantChoices() != null && getChantChoices()[slotNumber] != null && (getManaNeedBeforeLoaded(chantX, getChantChoices()[slotNumber])) <= manaNum;
    }

    public static boolean cannotPlayLegendarySpell() {
        if (AbstractDungeon.player.getRelic(FlammeGrimoire.ID) != null)
            return false;
        FakeFlammeGrimoire relic = (FakeFlammeGrimoire) AbstractDungeon.player.getRelic(FakeFlammeGrimoire.ID);
        if (relic != null && relic.takeEffect)
            return false;
        return getChantCardUsedThisTurn() == 0 && !Config.IN_DEV;
    }

    public static int getChantCardUsedThisTurn() {
        int amounts = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.hasTag(AbstractBaseCard.Enum.CHANT)) {
                amounts++;
            }
        }
        return amounts;
    }

    public static int getLegendarySpellUsedVarietyThisCombat(boolean isInUsingCard) {
        HashSet<String> set = new HashSet<>();
        int size = Math.max(0, (isInUsingCard ? AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1 : AbstractDungeon.actionManager.cardsPlayedThisCombat.size()));
        if (size == 0)
            return 0;
        for (int i = 0; i < size; i++) {
            AbstractCard c = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i);
            if (c.hasTag(AbstractBaseCard.Enum.LEGENDARY_SPELL)) {
                set.add(c.cardID);
            }
        }
        return set.size();
    }

    public static int getLegendarySpellUsedVarietyInDeck(boolean isInUsingCard) {
        HashSet<String> set = new HashSet<>();
        int size = Math.max(0, (isInUsingCard ? AbstractDungeon.player.masterDeck.group.size() - 1 : AbstractDungeon.player.masterDeck.group.size()));
        if (size == 0)
            return 0;
        for (int i = 0; i < size; i++) {
            AbstractCard c = AbstractDungeon.player.masterDeck.group.get(i);
            if (c.hasTag(AbstractBaseCard.Enum.LEGENDARY_SPELL)) {
                set.add(c.cardID);
            }
        }
        return set.size();
    }

    public static int getCardsUsedThisTurnSize(boolean isInUsingCard) {
        int cardsPlayedThisTurnSize = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        return isInUsingCard ? cardsPlayedThisTurnSize - 1 : cardsPlayedThisTurnSize;
    }

    public static int getSynchroTimesThisTurn() {
        int counts = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.hasTag(AbstractBaseCard.Enum.SYNCHRO) || c.hasTag(AbstractBaseCard.Enum.ACCEL_SYNCHRO) || c.hasTag(AbstractBaseCard.Enum.LIMIT_OVER_SYNCHRO))
                counts++;
        }
        return counts;
    }

    public static int getContinualSynchroTimes(AbstractCard card) {
        ArrayList<AbstractCard> cardsPlayedThisTurn = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        int counts = 0;
        for (int i = 0; i < cardsPlayedThisTurn.size(); i++) {
            if (cardsPlayedThisTurn.get(i).uuid.equals(card.uuid)) {
                for (int j = i; j >= 0; j--) {
                    if (cardsPlayedThisTurn.get(j).hasTag(AbstractBaseCard.Enum.SYNCHRO)) {
                        counts++;
                    } else
                        break;
                }
                break;
            }
        }
        return counts;
    }

    public static int getContinualSynchroMaxTimes() {
        int max = 0;
        ArrayList<AbstractCard> cardsPlayedThisTurn = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        for (int i = 0; i < cardsPlayedThisTurn.size(); i++) {
            if (cardsPlayedThisTurn.get(i).hasTag(AbstractBaseCard.Enum.SYNCHRO)) {
                int counts = 0;
                for (int j = i; j < cardsPlayedThisTurn.size(); j++) {
                    if (cardsPlayedThisTurn.get(j).hasTag(AbstractBaseCard.Enum.SYNCHRO))
                        counts++;
                    else
                        break;
                }
                if (counts > max)
                    max = counts;
            }
        }
        return max;
    }

    public static int getWeakenedChantPowerAmt() {
        AbstractPower po = AbstractDungeon.player.getPower(WeakenedChantPower.POWER_ID);
        return po == null ? 0 : po.amount;
    }

    public static int getConcentrationPowerAmt() {
        AbstractPower po = AbstractDungeon.player.getPower(ConcentrationPower.POWER_ID);
        return po == null ? 0 : po.amount;
    }

    public static int getManaNeedBeforeLoaded(int chantX, AbstractMagicItem f) {
        int baseManaNeed = chantX * f.manaNeedMultipleCoefficient + f.manaNeedAddCoefficient;
        return Math.max((baseManaNeed - getConcentrationPowerAmt() + getWeakenedChantPowerAmt()), 0);
    }

    public static int getManaNeedWhenLoading(int chantX, AbstractMagicItem f) {
        int baseManaNeed = chantX * f.manaNeedMultipleCoefficient + f.manaNeedAddCoefficient;
        if (AbstractDungeon.player.hasPower(ChantWithoutManaPower.POWER_ID))
            return 0;
        if (AbstractDungeon.player.hasPower(ChantWithoutManaTimesPower.POWER_ID))
            return 0;
        return Math.max((baseManaNeed - getConcentrationPowerAmt() + getWeakenedChantPowerAmt()), 0);
    }

    public static AbstractMagicItem[] getChantChoices() {
        AbstractMagicItem[] chantChoices = new AbstractMagicItem[3];
        for (AbstractCard c : MagicDeckField.getDeck().group) {
            if (c instanceof AbstractMagicItem && ((AbstractMagicItem) c).currentSlot == 0) {
                chantChoices[0] = (AbstractMagicItem) c.makeStatEquivalentCopy();
            } else if (c instanceof AbstractMagicItem && ((AbstractMagicItem) c).currentSlot == 1) {
                chantChoices[1] = (AbstractMagicItem) c.makeStatEquivalentCopy();
            } else if (c instanceof AbstractMagicItem && ((AbstractMagicItem) c).currentSlot == 2) {
                chantChoices[2] = (AbstractMagicItem) c.makeStatEquivalentCopy();
            }
        }
        boolean isAllCopyFactor = true;
        for (AbstractCard c : chantChoices) {
            if (!(c instanceof BluePrint)) {
                isAllCopyFactor = false;
                break;
            }
        }
        if (isAllCopyFactor)
            return null;
        for (int i = 0; i < chantChoices.length; i++) {
            if (chantChoices[i] instanceof BluePrint) {
                int j = (i + 1) % chantChoices.length;
                while (chantChoices[j] instanceof BluePrint)
                    j = (j + 1) % chantChoices.length;
                AbstractMagicItem targetChantChoice = (AbstractMagicItem) chantChoices[j].makeStatEquivalentCopy();
                chantChoices[i] = targetChantChoice;
                chantChoices[i].currentSlot = i;
            }
        }
        return chantChoices;
    }

    public static AbstractMagicItem[] getLoadedMagicFactor() {
        AbstractMagicItem[] chantChoices = new AbstractMagicItem[3];
        for (AbstractCard c : MagicDeckField.getDeck().group) {
            if (c instanceof AbstractMagicItem && ((AbstractMagicItem) c).currentSlot == 0) {
                chantChoices[0] = (AbstractMagicItem) c;
            } else if (c instanceof AbstractMagicItem && ((AbstractMagicItem) c).currentSlot == 1) {
                chantChoices[1] = (AbstractMagicItem) c;
            } else if (c instanceof AbstractMagicItem && ((AbstractMagicItem) c).currentSlot == 2) {
                chantChoices[2] = (AbstractMagicItem) c;
            }
        }
        return chantChoices;
    }

    public static boolean isAllMagicFactorLoading() {
        for (AbstractMagicItem f : getLoadedMagicFactor()) {
            if (f == null)
                return false;
        }
        return true;
    }

    public static boolean canRaidTakeEffect(int raidNumber) {
        if (Config.IN_DEV)
            return true;
        if (AbstractDungeon.player.hasPower(SocialDancingPower.POWER_ID) && getConcentrationPowerAmt() > 0)
            return raidNumber == getConcentrationPowerAmt() || raidNumber == getConcentrationPowerAmt() + 1 || raidNumber == Math.max(0, getConcentrationPowerAmt() - 1);
        return raidNumber == getConcentrationPowerAmt();
    }

    public static boolean triggerRaid(int raidNumber, ActionHelper.Lambda raidEffect) {
        return triggerRaid(raidNumber, false, raidEffect);
    }

    public static boolean triggerRaid(int raidNumber, boolean isDamage, ActionHelper.Lambda raidEffect) {
        if (!canRaidTakeEffect(raidNumber))
            return false;
        int times = 1;
        for (AbstractPower po : AbstractDungeon.player.powers) {
            if (po instanceof AbstractBasePower) {
                times += ((AbstractBasePower) po).modifyRaidTriggerTimes();
                times += ((AbstractBasePower) po).modifyRaidTriggerTimes(isDamage);
            }
        }
        for (int i = 0; i < times; i++) {
            raidEffect.run();
            for (AbstractPower po : AbstractDungeon.player.powers) {
                if (po instanceof AbstractBasePower)
                    ((AbstractBasePower) po).afterRaidTriggered();
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof AbstractBaseCard) {
                    ((AbstractBaseCard) c).afterRaidTriggered();
                }
            }
        }
        for (AbstractPower po : AbstractDungeon.player.powers) {
            if (po instanceof AbstractBasePower)
                ((AbstractBasePower) po).afterRaidFinished();
        }
        GameActionManagerField.raidTriggeredThisTurn.set(AbstractDungeon.actionManager, GameActionManagerField.raidTriggeredThisTurn.get(AbstractDungeon.actionManager) + times);
        GameActionManagerField.raidTriggeredThisCombat.set(AbstractDungeon.actionManager, GameActionManagerField.raidTriggeredThisCombat.get(AbstractDungeon.actionManager) + times);
        return true;
    }

    public static AbstractCard getPreviewSpecializedOffensiveMagic(int baseDamage) {
        SpecializedOffensiveMagic magic = new SpecializedOffensiveMagic();
        magic.baseDamage = baseDamage;
        CardModifierManager.addModifier(magic, new DamageMod(baseDamage));
        for (AbstractPower po : AbstractDungeon.player.powers) {
            if (po instanceof AbstractFusionPower) {
                if (po instanceof DamageFusionPower) {
                    magic.baseDamage += po.amount;
                } else {
                    CardModifierManager.addModifier(magic, ((AbstractFusionPower) po).modifier);
                }
            }
        }
        CardModifierManager.addModifier(magic, new ExhaustEtherealMod());
        for (AbstractPower po : AbstractDungeon.player.powers) {
            if (po instanceof AbstractBasePower) {
                ((AbstractBasePower) po).beforeGainSpecializedOffensiveMagic(magic);
            }
        }
        magic.rawDescription = magic.usedModifierText;
        magic.initializeDescription();
        return magic;
    }
}
