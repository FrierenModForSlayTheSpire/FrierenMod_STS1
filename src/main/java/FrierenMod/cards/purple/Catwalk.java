package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.powers.FusionPower.StrengthFusionPower;
import FrierenMod.powers.FusionPower.StrengthFusionPowerFusionPower;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Catwalk extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(Catwalk.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 2, CardType.SKILL, CardEnums.FERN_CARD, CardRarity.RARE, CardTarget.SELF);

    public Catwalk() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(Enum.FUSION);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
            this.exhaust = false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthFusionPower(this.magicNumber)));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthFusionPowerFusionPower(this.magicNumber)));
    }
}

