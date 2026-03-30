package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.cards.tempCards.SpecializedOffensiveMagic;
import FrierenMod.enums.CardEnums;
import FrierenMod.gameHelpers.CombatHelper;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Rewind extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(Rewind.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 0, CardType.SKILL, CardEnums.FERN_CARD, CardRarity.COMMON, CardTarget.SELF);

    public Rewind() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.raidNumber = this.baseRaidNumber = 2;
        this.tags.add(Enum.RAID);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        ArrayList<AbstractCard> list;
        if (upgraded) {
            list = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        } else
            list = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        if (!list.isEmpty()) {
            for (int i = list.size() - 1; i > 0; i--) {
                if (list.get(i).cardID.equals(SpecializedOffensiveMagic.ID)) {
                    this.cardsToPreview = list.get(i);
                    break;
                }
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.isRaidTriggered = CombatHelper.triggerRaid(raidNumber, () -> {
            if (this.cardsToPreview != null)
                this.addToBot(new MakeTempCardInHandAction(this.cardsToPreview));
        });
    }
}

