package FrierenMod.utils;

import FrierenMod.cards.magicItems.AbstractMagicItem;
import FrierenMod.commands.MagicItemCommand;
import FrierenMod.enums.CardEnums;
import FrierenMod.enums.CharacterEnums;
import FrierenMod.events.AnimalWell;
import FrierenMod.events.FoodEvent;
import FrierenMod.events.KraftGift;
import FrierenMod.events.MimicFight;
import FrierenMod.gameHelpers.CombatHelper;
import FrierenMod.gameHelpers.HookHelper;
import FrierenMod.monsters.Mimic;
import FrierenMod.monsters.Spiegel_Frieren;
import FrierenMod.potions.BottledMana;
import FrierenMod.potions.DissolveClothPotion;
import FrierenMod.potions.EmperorWine;
import FrierenMod.rewards.MagicItemReward;
import FrierenMod.ui.panels.ConfigPanel;
import FrierenMod.ui.panels.MagicDeckPanel;
import FrierenMod.ui.screens.MagicDeckScreen;
import FrierenMod.variables.ChantXVariable;
import FrierenMod.variables.RaidVariable;
import FrierenMod.variables.SecondMagicNumberVariable;
import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;

public class RegisterHelper {
    public static void registerColor() {
        Log.logger.info("Creating the color {}", CardEnums.FRIEREN_CARD.toString());
        BaseMod.addColor(CardEnums.FRIEREN_CARD,
                FrierenRes.RENDER_COLOR.cpy(),
                FrierenRes.RENDER_COLOR.cpy(),
                FrierenRes.RENDER_COLOR.cpy(),
                FrierenRes.RENDER_COLOR.cpy(),
                FrierenRes.RENDER_COLOR.cpy(),
                FrierenRes.RENDER_COLOR.cpy(),
                FrierenRes.RENDER_COLOR.cpy(),
                FrierenRes.BG_ATTACK_512,
                FrierenRes.BG_SKILL_512,
                FrierenRes.BG_POWER_512,
                FrierenRes.ENERGY_ORB,
                FrierenRes.BG_ATTACK_1024,
                FrierenRes.BG_SKILL_1024,
                FrierenRes.BG_POWER_1024,
                FrierenRes.BIG_ORB,
                FrierenRes.SMALL_ORB);
        Log.logger.info("Creating the color {}", CardEnums.MAGIC_ITEM.toString());
        BaseMod.addColor(CardEnums.MAGIC_ITEM,
                PublicRes.MAGIC_ITEM_RENDER_COLOR.cpy(),
                PublicRes.MAGIC_ITEM_RENDER_COLOR.cpy(),
                PublicRes.MAGIC_ITEM_RENDER_COLOR.cpy(),
                PublicRes.MAGIC_ITEM_RENDER_COLOR.cpy(),
                PublicRes.MAGIC_ITEM_RENDER_COLOR.cpy(),
                PublicRes.MAGIC_ITEM_RENDER_COLOR.cpy(),
                PublicRes.MAGIC_ITEM_RENDER_COLOR.cpy(),
                PublicRes.BG_MANA_512,
                PublicRes.BG_MANA_512,
                PublicRes.BG_MANA_512,
                FrierenRes.ENERGY_ORB,
                PublicRes.BG_MANA_1024,
                PublicRes.BG_MANA_1024,
                PublicRes.BG_MANA_1024,
                FrierenRes.BIG_ORB,
                FrierenRes.SMALL_ORB);
        if (Config.FERN_ENABLE) {
            Log.logger.info("Creating the color {}", CardEnums.FERN_CARD.toString());
            BaseMod.addColor(CardEnums.FERN_CARD,
                    FernRes.RENDER_COLOR.cpy(),
                    FernRes.RENDER_COLOR.cpy(),
                    FernRes.RENDER_COLOR.cpy(),
                    FernRes.RENDER_COLOR.cpy(),
                    FernRes.RENDER_COLOR.cpy(),
                    FernRes.RENDER_COLOR.cpy(),
                    FernRes.RENDER_COLOR.cpy(),
                    FernRes.BG_ATTACK_512,
                    FernRes.BG_SKILL_512,
                    FernRes.BG_POWER_512,
                    FernRes.ENERGY_ORB,
                    FernRes.BG_ATTACK_1024,
                    FernRes.BG_SKILL_1024,
                    FernRes.BG_POWER_1024,
                    FernRes.BIG_ORB,
                    FernRes.SMALL_ORB);
        }
        Log.logger.info("Done creating the color");
    }

    public static void registerSundries() {
        Log.logger.info("Adding hooks...");
        BaseMod.subscribe(new HookHelper());
        Log.logger.info("Done adding hooks");
        Log.logger.info("Adding config panel...");
        ConfigPanel.makeModPanels();
        SpireConfig config = ConfigPanel.makeConfig();
        ConfigPanel.loadProperties(config);
        Log.logger.info("Done adding config panel");
        Log.logger.info("Adding panels and screens...");
        BaseMod.addTopPanelItem(new MagicDeckPanel());
        BaseMod.addCustomScreen(new MagicDeckScreen());
        Log.logger.info("Done adding panels and screens");
        Log.logger.info("Adding rewards...");
        MagicItemReward.register();
        Log.logger.info("Done adding rewards");
        Log.logger.info("Adding commands...");
        MagicItemCommand.register();
        Log.logger.info("Done adding commands");
    }

    public static void registerMonsters() {
        Log.logger.info("Adding monsters...");
        Mimic.register();
        Log.logger.info("Done adding monsters");
    }

    public static void registerBosses() {
        Log.logger.info("Adding bosses...");
        Spiegel_Frieren.register();
        Log.logger.info("Done adding bosses");
    }

    public static void registerAudios() {
        Log.logger.info("Adding audios...");
        BaseMod.addAudio("Frieren_The_Slayer.mp3", ModInformation.makeAudioPath("sound/Frieren_The_Slayer.mp3"));
        Log.logger.info("Done adding audios");
    }

    public static void registerEvents() {
        Log.logger.info("Adding events...");

        AddEventParams foodEventParams = new AddEventParams();
        foodEventParams.playerClasses.add(CharacterEnums.FRIEREN);
        foodEventParams.eventID = FoodEvent.ID;
        foodEventParams.eventClass = FoodEvent.class;
        BaseMod.addEvent(foodEventParams);

        AddEventParams kraftGiftEventParams = new AddEventParams();
        kraftGiftEventParams.playerClasses.add(CharacterEnums.FRIEREN);
        kraftGiftEventParams.eventID = KraftGift.ID;
        kraftGiftEventParams.eventClass = KraftGift.class;
        kraftGiftEventParams.dungeonIDs.add(TheCity.ID);
        kraftGiftEventParams.dungeonIDs.add(TheBeyond.ID);
        BaseMod.addEvent(kraftGiftEventParams);

        AddEventParams animalWellEventParams = new AddEventParams();
        animalWellEventParams.playerClasses.add(CharacterEnums.FRIEREN);
        animalWellEventParams.eventID = AnimalWell.ID;
        animalWellEventParams.eventClass = AnimalWell.class;
        BaseMod.addEvent(animalWellEventParams);

        AddEventParams mimicFightEventParams = new AddEventParams();
        mimicFightEventParams.playerClasses.add(CharacterEnums.FRIEREN);
        mimicFightEventParams.eventID = MimicFight.ID;
        mimicFightEventParams.eventClass = MimicFight.class;
        mimicFightEventParams.endsWithRewardsUI = true;
        mimicFightEventParams.dungeonIDs.add(TheCity.ID);
        BaseMod.addEvent(mimicFightEventParams);

        Log.logger.info("Done adding events");
    }

    public static void registerVariables() {
        Log.logger.info("Adding variables...");
        BaseMod.addDynamicVariable(new ChantXVariable());
        BaseMod.addDynamicVariable(new SecondMagicNumberVariable());
        BaseMod.addDynamicVariable(new RaidVariable());
        DynamicTextBlocks.registerCustomCheck("frierenmod:SlotNumber", card -> {
            if (AbstractDungeon.player != null && CombatHelper.isInCombat()) {
                if (card instanceof AbstractMagicItem) {
                    return ((AbstractMagicItem) card).currentSlot;
                }
            }
            return -1;
        });
        Log.logger.info("Done adding variables");
    }

    public static void registerPotions() {
        Log.logger.info("Adding potions...");
        BaseMod.addPotion(BottledMana.class, Color.BLUE.cpy(), Color.ROYAL.cpy(), Color.ROYAL, BottledMana.POTION_ID, CharacterEnums.FRIEREN);
        BaseMod.addPotion(DissolveClothPotion.class, new Color(149.0F / 255.0F, 122.0F / 255.0F, 157.0F / 255.0F, 1.0F), new Color(149.0F / 255.0F, 122.0F / 255.0F, 157.0F / 255.0F, 1.0F), FrierenRes.RENDER_COLOR.cpy(), DissolveClothPotion.POTION_ID, CharacterEnums.FRIEREN);
        BaseMod.addPotion(EmperorWine.class, FrierenRes.RENDER_COLOR.cpy(), FrierenRes.RENDER_COLOR.cpy(), FrierenRes.RENDER_COLOR.cpy(), EmperorWine.POTION_ID, CharacterEnums.FRIEREN);
        Log.logger.info("Done adding potions");
    }

}
