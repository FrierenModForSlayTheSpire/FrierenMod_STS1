package FrierenMod.cards.purple;

import FrierenMod.actions.ExhaustAllManaAction;
import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.cards.tempCards.Mana;
import FrierenMod.enums.CardEnums;
import FrierenMod.gameHelpers.CombatHelper;
import FrierenMod.powers.ConcentrationPower;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SorryICant extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(SorryICant.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final CardInfo info = new CardInfo(ID, 0, CardType.SKILL, CardEnums.FERN_CARD, CardRarity.UNCOMMON, CardTarget.SELF);

    public SorryICant() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.cardsToPreview = new Mana();
        this.exhaust = true;
        this.tags.add(Enum.SPEED);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ExhaustAllManaAction());
        this.addToBot(new GainBlockAction(p, this.block));
        this.addToBot(new ApplyPowerAction(p, p, new ConcentrationPower(this.magicNumber)));
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int amount = CombatHelper.getAllManaNum();
        this.magicNumber = this.baseMagicNumber = this.baseBlock = amount;
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        if (amount > 0)
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void applyPowers() {
        int amount = CombatHelper.getAllManaNum();
        this.magicNumber = this.baseMagicNumber = this.baseBlock = amount;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        if (amount > 0)
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
}
