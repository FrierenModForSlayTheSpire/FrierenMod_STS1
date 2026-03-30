package FrierenMod.cards.purple;

import FrierenMod.actions.GainFernPanelEnergyAction;
import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.gameHelpers.CombatHelper;
import FrierenMod.powers.FusionPower.DamageFusionPower;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Surveillance extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(Surveillance.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 1, CardType.ATTACK, CardEnums.FERN_CARD, CardRarity.UNCOMMON, CardTarget.ENEMY);

    public Surveillance() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.damage = this.baseDamage = 5;
        this.raidNumber = this.baseRaidNumber = 1;
        this.tags.add(Enum.RAID);
        this.tags.add(Enum.FUSION);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(1));
        this.isRaidTriggered = CombatHelper.triggerRaid(raidNumber, () -> this.addToBot(new GainFernPanelEnergyAction(1)));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new ApplyPowerAction(p, p, new DamageFusionPower(this.damage)));
    }
}

