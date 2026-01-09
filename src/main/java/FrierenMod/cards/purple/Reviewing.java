package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.powers.ConcentrationPower;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Reviewing extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(Reviewing.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final CardInfo info = new CardInfo(ID, 1, CardType.SKILL, CardEnums.FERN_CARD, CardRarity.UNCOMMON, CardTarget.SELF);

    public Reviewing() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.block = baseBlock = 8;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            this.initializeDescription();
            if (!this.hasTag(Enum.SPEED))
                this.tags.add(Enum.SPEED);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(card -> !card.hasTag(Enum.MANA)).count();
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new ApplyPowerAction(p, p, new ConcentrationPower(amount)));
    }

    public void onMoveToDiscard() {
        if (this.upgraded){
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int amount = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(card -> !card.hasTag(Enum.MANA)).count();
        super.calculateCardDamage(mo);
        if (this.upgraded){
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        if (amount > 0)
            this.rawDescription += String.format(cardStrings.EXTENDED_DESCRIPTION[0], amount);
        initializeDescription();
    }

    public void applyPowers() {
        int amount = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(card -> !card.hasTag(Enum.MANA)).count();
        super.applyPowers();
        if (this.upgraded){
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        if (amount > 0)
            this.rawDescription += String.format(cardStrings.EXTENDED_DESCRIPTION[0], amount);
        initializeDescription();
    }
}

