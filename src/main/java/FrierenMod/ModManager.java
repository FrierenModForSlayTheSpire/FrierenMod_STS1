package FrierenMod;


import FrierenMod.Characters.Fern;
import FrierenMod.Characters.Frieren;
import FrierenMod.Characters.Stark;
import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.enums.CharacterEnums;
import FrierenMod.gameHelpers.DataObject;
import FrierenMod.gameHelpers.SaveFileHelper;
import FrierenMod.utils.*;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static basemod.BaseMod.logger;
import static com.megacrit.cardcrawl.core.Settings.language;


@SpireInitializer
public class ModManager implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber, PostInitializeSubscriber, CustomSavable<String> {
    private static String modID;
    public static DataObject saveData = new DataObject();

    public ModManager() {
        BaseMod.subscribe(this);
        setModID(ModInformation.MOD_NAME);
        RegisterHelper.registerColor();
    }

    public void setModID(String ID) {
        Gson coolG = new Gson();
        InputStream in = ModManager.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: {}", ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULT_ID))
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        if (ID.equals(EXCEPTION_STRINGS.DEV_ID)) {
            modID = EXCEPTION_STRINGS.DEFAULT_ID;
        } else {
            modID = ID;
        }
        logger.info("Success! ID is {}", modID);
    }

    public static void initialize() {
        new ModManager();
    }

    @Override
    public void receivePostInitialize() {
        try {
            AntiPirateHelper.antiPirate();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        RegisterHelper.registerPotions();
        RegisterHelper.registerBosses();
        RegisterHelper.registerMonsters();
        RegisterHelper.registerAudios();
        RegisterHelper.registerEvents();
        RegisterHelper.registerSundries();
        BaseMod.addSaveField(ModInformation.MOD_NAME, this);
    }

    @Override
    public void receiveEditCards() {
        pathCheck();
        RegisterHelper.registerVariables();
        Log.logger.info("Adding cards");
        String optionCardsClassPath = getModID() + ".cards.optionCards";
        String tempCardsClassPath = getModID() + ".cards.tempCards";
        String FrierenCardsClassPath = getModID() + ".cards.white";
        String FernCardsClassPath = getModID() + ".cards.purple";
        String magicItems = getModID() + ".cards.magicItems";
        ArrayList<String> classPaths = new ArrayList<>();
        classPaths.add(optionCardsClassPath);
        classPaths.add(tempCardsClassPath);
        classPaths.add(FrierenCardsClassPath);
        classPaths.add(magicItems);
        for (String classPath : classPaths) {
            (new AutoAdd(getModID())).packageFilter(classPath).setDefaultSeen(true).any(AbstractCard.class, (info, card) -> {
                BaseMod.addCard(card);
                if (Config.IN_DEV && info.seen)
                    UnlockTracker.unlockCard(card.cardID);
                if (Config.FERN_ENABLE && card.hasTag(AbstractBaseCard.Enum.FRIEREN_FERN_CARD) && card instanceof AbstractBaseCard) {
                    AbstractCard secondRegisterCard = ((AbstractBaseCard) card).getCardForSecondRegister();
                    BaseMod.addCard(secondRegisterCard);
                    if (Config.IN_DEV && info.seen)
                        UnlockTracker.unlockCard(secondRegisterCard.cardID);
                }
            });
        }
        if (Config.FERN_ENABLE)
            (new AutoAdd(getModID())).packageFilter(FernCardsClassPath).setDefaultSeen(true).any(AbstractCard.class, (info, card) -> {
                BaseMod.addCard(card);
                if (Config.IN_DEV && info.seen)
                    UnlockTracker.unlockCard(card.cardID);
            });
        Log.logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditRelics() {
        String relicClassPath = getClass().getPackage().getName() + ".relics";
        Log.logger.info("===============Adding relics: search in {}", relicClassPath);
        for (ModInfo info : Loader.MODINFOS)
            Log.logger.info(info.ID);
        (new AutoAdd(getModID())).packageFilter(relicClassPath).any(CustomRelic.class, (info, relic) -> {
            BaseMod.addRelicToCustomPool(relic, CardEnums.FRIEREN_CARD);
            UnlockTracker.markRelicAsSeen(relic.relicId);
            Log.logger.info("Adding relics: {}", relic.relicId);
        });
        Log.logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCharacters() {
        Log.logger.info("Beginning to edit characters. Add {}", CharacterEnums.FRIEREN.toString());
        BaseMod.addCharacter(new Frieren(CardCrawlGame.playerName), FrierenRes.CHARACTER_BUTTON, FrierenRes.CHARACTER_PORTRAIT, CharacterEnums.FRIEREN);
        Log.logger.info("Added {}", CharacterEnums.FRIEREN.toString());
        if (Config.FERN_ENABLE) {
            Log.logger.info("Beginning to edit characters. Add {}", CharacterEnums.FERN.toString());
            BaseMod.addCharacter(new Fern(CardCrawlGame.playerName), FernRes.CHARACTER_BUTTON, FernRes.CHARACTER_PORTRAIT, CharacterEnums.FERN);
            Log.logger.info("Added {}", CharacterEnums.FERN.toString());
        }
        if (Config.STARK_ENABLE) {
            Log.logger.info("Beginning to edit characters. Add {}", CharacterEnums.FERN.toString());
            BaseMod.addCharacter(new Stark(CardCrawlGame.playerName), StarkRes.CHARACTER_BUTTON, StarkRes.CHARACTER_PORTRAIT, CharacterEnums.STARK);
            Log.logger.info("Added {}", CharacterEnums.STARK.toString());
        }
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang;
        switch (language) {
            case ZHS:
                lang = "ZHS";
                break;
            case ZHT:
                lang = "ZHT";
                break;
            default:
            case ENG:
                lang = "ENG";
                break;
        }
        String json = Gdx.files.internal(ModInformation.makeLocalizationPath(lang, "keywords"))
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(ModInformation.KEY_WORD, keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
                Log.logger.info("-----------------add keyword: {}", keyword.NAMES[0]);
            }
        }
    }

    public void receiveEditStrings() {
        String lang;
        switch (language) {
            case ZHS:
                lang = "ZHS";
                break;
            case ZHT:
                lang = "ZHT";
                break;
            default:
            case ENG:
                lang = "ENG";
                break;
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, ModInformation.makeLocalizationPath(lang, "cards"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, ModInformation.makeLocalizationPath(lang, "characters"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, ModInformation.makeLocalizationPath(lang, "relics"));
        BaseMod.loadCustomStringsFile(PotionStrings.class, ModInformation.makeLocalizationPath(lang, "potions"));
        BaseMod.loadCustomStringsFile(PowerStrings.class, ModInformation.makeLocalizationPath(lang, "powers"));
        BaseMod.loadCustomStringsFile(UIStrings.class, ModInformation.makeLocalizationPath(lang, "UIs"));
        BaseMod.loadCustomStringsFile(MonsterStrings.class, ModInformation.makeLocalizationPath(lang, "monsters"));
        BaseMod.loadCustomStringsFile(EventStrings.class, ModInformation.makeLocalizationPath(lang, "events"));
    }

    private static String getModID() {
        return modID;
    }

    private static void pathCheck() {
        Gson coolG = new Gson();
        InputStream in = ModManager.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = (IDCheckDontTouchPls) coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = ModManager.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(EXCEPTION_STRINGS.DEV_ID)) {
            if (!packageName.equals(getModID()))
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            if (!resourcePathExists.exists())
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
        }
    }

    @Override
    public String onSave() {
        SaveFileHelper.save();
        return "";
    }

    @Override
    public void onLoad(String s) {
        SaveFileHelper.load();
    }
}