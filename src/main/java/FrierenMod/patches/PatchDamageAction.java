package FrierenMod.patches;

import FrierenMod.patches.fields.DamageActionField;
import FrierenMod.powers.FumeshroomFormPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PatchDamageAction {
    @SpirePatch(clz = DamageAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCreature.class, DamageInfo.class, AbstractGameAction.AttackEffect.class})
    public static class PatchConstructor {
        @SpirePostfixPatch
        public static void PostFix(DamageAction __instance, AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
            if (info.owner != null && info.owner == AbstractDungeon.player && target != AbstractDungeon.player && AbstractDungeon.player.hasPower(FumeshroomFormPower.POWER_ID)) {
                DamageActionField.backUpAction.set(__instance, new DamageAllEnemiesAction((AbstractPlayer) info.owner, info.base, info.type, effect));
            }
        }
    }

    @SpirePatch(clz = DamageAction.class, method = "update")
    public static class PatchUpdate {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(DamageAction __instance) {
            AbstractGameAction backUp = DamageActionField.backUpAction.get(__instance);
            if (backUp != null) {
                backUp.update();
                if (backUp.isDone) {
                    __instance.isDone = true;
                }
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
