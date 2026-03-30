package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.gameHelpers.CombatHelper;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

public class HuddleForWarmth extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(HuddleForWarmth.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 1, CardType.SKILL, CardEnums.FERN_CARD, CardRarity.COMMON, CardTarget.SELF);

    public HuddleForWarmth() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.block = baseBlock = 8;
        this.raidNumber = baseRaidNumber = 2;
        this.tags.add(Enum.RAID);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.isRaidTriggered = CombatHelper.triggerRaid(raidNumber, () -> this.addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 1))));
    }
}

