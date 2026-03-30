package FrierenMod.cards.white;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.effects.PlaySFXEffect;
import FrierenMod.enums.CardEnums;
import FrierenMod.gameHelpers.CombatHelper;
import FrierenMod.gameHelpers.SlotBgHelper;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

public class OrdinaryOffensiveMagic extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(OrdinaryOffensiveMagic.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static CardInfo info = new CardInfo(ID, 2, CardType.ATTACK, CardEnums.FRIEREN_CARD, CardRarity.UNCOMMON, CardTarget.ENEMY);

    public OrdinaryOffensiveMagic() {
        super(info);
    }

    public OrdinaryOffensiveMagic(CardInfo info) {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.baseDamage = 0;
        this.magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    public void applyPowers() {
        int manaCount = CombatHelper.getManaNumInExhaustPile();
        if (manaCount > 0) {
            this.baseDamage = manaCount * magicNumber;
            super.applyPowers();
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int manaCount = CombatHelper.getManaNumInExhaustPile();
        this.baseDamage = manaCount * magicNumber;
        this.calculateCardDamage(m);
        if (this.damage >= 100)
            SlotBgHelper.unlockANewSlot("4008");
        this.addToTop(new VFXAction(p, new PlaySFXEffect("normal_attack.mp3"), 0.1F, true));
        this.addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }
}
