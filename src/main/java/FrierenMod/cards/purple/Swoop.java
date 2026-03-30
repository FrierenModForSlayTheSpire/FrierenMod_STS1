package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.powers.FusionPower.DamageFusionPower;
import FrierenMod.powers.FusionPower.DrawCardFusionPower;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Swoop extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(Swoop.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 1, CardType.ATTACK, CardEnums.FERN_CARD, CardRarity.COMMON, CardTarget.ENEMY);

    public Swoop() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.damage = this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(Enum.FUSION);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new DrawCardAction(p, this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new DamageFusionPower(this.damage)));
        this.addToBot(new ApplyPowerAction(p, p, new DrawCardFusionPower(this.magicNumber)));
    }
}

