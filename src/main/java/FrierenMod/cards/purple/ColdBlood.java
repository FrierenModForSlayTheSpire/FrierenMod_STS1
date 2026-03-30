package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ColdBlood extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(ColdBlood.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 2, CardType.ATTACK, CardEnums.FERN_CARD, CardRarity.UNCOMMON, CardTarget.ENEMY);

    public ColdBlood() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.damage = this.baseDamage = 12;
    }

    public void applyPowers() {
        super.applyPowers();
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            for (int i = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1; i >= 0; i--) {
                AbstractCard c = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i);
                if (c.hasTag(Enum.RAID) && c instanceof AbstractBaseCard && ((AbstractBaseCard) c).isRaidTriggered) {
                    this.setCostForTurn(0);
                    break;
                }
            }
        }
    }

    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            for (int i = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1; i >= 0; i--) {
                AbstractCard c = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i);
                if (c.hasTag(Enum.RAID) && c instanceof AbstractBaseCard && ((AbstractBaseCard) c).isRaidTriggered) {
                    this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                    break;
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
}

