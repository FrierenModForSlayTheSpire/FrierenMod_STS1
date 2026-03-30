package FrierenMod.ui.panels;

import FrierenMod.gameHelpers.SlotBgHelper;
import FrierenMod.utils.Config;
import FrierenMod.utils.Log;
import FrierenMod.utils.ModInformation;
import FrierenMod.utils.PublicRes;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Properties;

public class ConfigPanel extends ModPanel {
    public static final String[] AUTHORS = new String[]{"Arkalin", "Figure"};
    private static final float BASE_X_POSE = 380.0F;
    private static final float BASE_Y_POSE = 720.0F;
    private static final float GAP = 40.0F;
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ModInformation.makeID(ConfigPanel.class.getSimpleName())).TEXT;

    @Nullable
    public static SpireConfig makeConfig() {
        Properties properties = new Properties();
        properties.setProperty("ALLOW_SPECIAL_SFX", Boolean.toString(Config.ALLOW_SPECIAL_SFX));
        properties.setProperty("ALLOW_SPECIAL_BGM", Boolean.toString(Config.ALLOW_SPECIAL_BGM));
        properties.setProperty("SHOW_SLOT_TYPE", Boolean.toString(Config.SHOW_SLOT_TYPE));
        properties.setProperty("ALLOW_ACHIEVEMENT", Boolean.toString(Config.ALLOW_ACHIEVEMENT));
        properties.setProperty("ALLOW_PUZZLE_FACTOR_DROP", Boolean.toString(Config.ALLOW_PUZZLE_FACTOR_DROP));
        properties.setProperty("REMOVE_VELVET_CHOKER", Boolean.toString(Config.REMOVE_VELVET_CHOKER));
        properties.setProperty("REMOVE_DEAD_BRANCH", Boolean.toString(Config.REMOVE_DEAD_BRANCH));
        properties.setProperty("REMOVE_STRANGE_SPOON", Boolean.toString(Config.REMOVE_STRANGE_SPOON));
        properties.setProperty("ENCOUNTER_SPIEGEL", Boolean.toString(Config.ENCOUNTER_SPIEGEL));
        properties.setProperty("REMOVE_TIME_EATER", Boolean.toString(Config.REMOVE_TIME_EATER));
        properties.setProperty("SLOT_BG_COLLECTION_PROGRESS", SlotBgHelper.progressString);
        properties.setProperty("SLOT_BG_LOADING", SlotBgHelper.loadingString);
        try {
            return new SpireConfig(ModInformation.MOD_NAME, "frierenmod_config", properties);
        } catch (Exception e) {
            return null;
        }
    }

    public static void loadProperties(SpireConfig config) {
        if (config == null) {
            Log.logger.info("Missing config file");
            return;
        }
        Config.ALLOW_SPECIAL_SFX = config.getBool("ALLOW_SPECIAL_SFX");
        Config.ALLOW_SPECIAL_BGM = config.getBool("ALLOW_SPECIAL_BGM");
        Config.SHOW_SLOT_TYPE = config.getBool("SHOW_SLOT_TYPE");
        Config.ALLOW_ACHIEVEMENT = config.getBool("ALLOW_ACHIEVEMENT");
        Config.ALLOW_PUZZLE_FACTOR_DROP = config.getBool("ALLOW_PUZZLE_FACTOR_DROP");
        Config.REMOVE_VELVET_CHOKER = config.getBool("REMOVE_VELVET_CHOKER");
        Config.REMOVE_DEAD_BRANCH = config.getBool("REMOVE_DEAD_BRANCH");
        Config.REMOVE_STRANGE_SPOON = config.getBool("REMOVE_STRANGE_SPOON");
        Config.ENCOUNTER_SPIEGEL = config.getBool("ENCOUNTER_SPIEGEL");
        Config.REMOVE_TIME_EATER = config.getBool("REMOVE_TIME_EATER");
        SlotBgHelper.progressString = config.getString("SLOT_BG_COLLECTION_PROGRESS");
        SlotBgHelper.loadingString = config.getString("SLOT_BG_LOADING");
    }


    private static void save(SpireConfig config) {
        if (config == null)
            return;
        try {
            config.save();
        } catch (Exception e) {
            Log.logger.info("Failed to save current config file");
        }
    }

    public static void saveSlotChange(String newString) {
        SpireConfig config = makeConfig();
        assert config != null;
        config.setString("SLOT_BG_LOADING", newString);
        save(config);
        SlotBgHelper.loadingString = config.getString("SLOT_BG_LOADING");
    }

    public static void saveSlotProgress(String newString) {
        SpireConfig config = makeConfig();
        assert config != null;
        config.setString("SLOT_BG_COLLECTION_PROGRESS", newString);
        save(config);
        SlotBgHelper.progressString = config.getString("SLOT_BG_COLLECTION_PROGRESS");
    }

    public static void makeModPanels() {
        ModPanel settings = new ModPanel();
        ModLabeledToggleButton allowSFX = new ModLabeledToggleButton(TEXT[0], BASE_X_POSE, BASE_Y_POSE, Color.WHITE.cpy(), FontHelper.charDescFont, Config.ALLOW_SPECIAL_SFX, settings, l -> {
        }, btn -> {
            Config.ALLOW_SPECIAL_SFX = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("ALLOW_SPECIAL_SFX", Config.ALLOW_SPECIAL_SFX);
            save(config);
        });
        ModLabeledToggleButton allowBGM = new ModLabeledToggleButton(TEXT[1], BASE_X_POSE, BASE_Y_POSE - GAP, Color.WHITE.cpy(), FontHelper.charDescFont, Config.ALLOW_SPECIAL_BGM, settings, l -> {
        }, btn -> {
            Config.ALLOW_SPECIAL_BGM = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("ALLOW_SPECIAL_BGM", Config.ALLOW_SPECIAL_BGM);
            save(config);
        });
        ModLabeledToggleButton showSlotType = new ModLabeledToggleButton(TEXT[2], BASE_X_POSE, BASE_Y_POSE - GAP * 2, Color.WHITE.cpy(), FontHelper.charDescFont, Config.SHOW_SLOT_TYPE, settings, l -> {
        }, btn -> {
            Config.SHOW_SLOT_TYPE = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("SHOW_SLOT_TYPE", Config.SHOW_SLOT_TYPE);
            save(config);
        });
        ModLabeledToggleButton allowAchievement = new ModLabeledToggleButton(TEXT[3], BASE_X_POSE, BASE_Y_POSE - GAP * 3, Color.WHITE.cpy(), FontHelper.charDescFont, Config.ALLOW_ACHIEVEMENT, settings, l -> {
        }, btn -> {
            Config.ALLOW_ACHIEVEMENT = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("ALLOW_ACHIEVEMENT", Config.ALLOW_ACHIEVEMENT);
            save(config);
        });
        ModLabeledToggleButton allowPuzzleFactorDrop = new ModLabeledToggleButton(TEXT[4], BASE_X_POSE, BASE_Y_POSE - GAP * 4, Color.WHITE.cpy(), FontHelper.charDescFont, Config.ALLOW_PUZZLE_FACTOR_DROP, settings, l -> {
        }, btn -> {
            Config.ALLOW_PUZZLE_FACTOR_DROP = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("ALLOW_PUZZLE_FACTOR_DROP", Config.ALLOW_PUZZLE_FACTOR_DROP);
            save(config);
        });
        ModLabeledToggleButton removeVelvetChoker = new ModLabeledToggleButton(TEXT[5], BASE_X_POSE, BASE_Y_POSE - GAP * 5, Color.WHITE.cpy(), FontHelper.charDescFont, Config.REMOVE_VELVET_CHOKER, settings, l -> {
        }, btn -> {
            Config.REMOVE_VELVET_CHOKER = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("REMOVE_VELVET_CHOKER", Config.REMOVE_VELVET_CHOKER);
            save(config);
        });
        ModLabeledToggleButton removeDeadBranch = new ModLabeledToggleButton(TEXT[6], BASE_X_POSE, BASE_Y_POSE - GAP * 6, Color.WHITE.cpy(), FontHelper.charDescFont, Config.REMOVE_DEAD_BRANCH, settings, l -> {
        }, btn -> {
            Config.REMOVE_DEAD_BRANCH = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("REMOVE_DEAD_BRANCH", Config.REMOVE_DEAD_BRANCH);
            save(config);
        });
        ModLabeledToggleButton removeStrangeSpoon = new ModLabeledToggleButton(TEXT[7], BASE_X_POSE, BASE_Y_POSE - GAP * 7, Color.WHITE.cpy(), FontHelper.charDescFont, Config.REMOVE_STRANGE_SPOON, settings, l -> {
        }, btn -> {
            Config.REMOVE_STRANGE_SPOON = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("REMOVE_STRANGE_SPOON", Config.REMOVE_STRANGE_SPOON);
            save(config);
        });
        ModLabeledToggleButton encounterSpiegel = new ModLabeledToggleButton(TEXT[8], BASE_X_POSE, BASE_Y_POSE - GAP * 8, Color.WHITE.cpy(), FontHelper.charDescFont, Config.ENCOUNTER_SPIEGEL, settings, l -> {
        }, btn -> {
            Config.ENCOUNTER_SPIEGEL = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("ENCOUNTER_SPIEGEL", Config.ENCOUNTER_SPIEGEL);
            save(config);
        });
        ModLabeledToggleButton removeTimeEater = new ModLabeledToggleButton(TEXT[9], BASE_X_POSE, BASE_Y_POSE - GAP * 9, Color.WHITE.cpy(), FontHelper.charDescFont, Config.REMOVE_TIME_EATER, settings, l -> {
        }, btn -> {
            Config.REMOVE_TIME_EATER = btn.enabled;
            SpireConfig config = makeConfig();
            assert config != null;
            config.setBool("REMOVE_TIME_EATER", Config.REMOVE_TIME_EATER);
            save(config);
        });
        settings.addUIElement(allowSFX);
        settings.addUIElement(allowBGM);
        settings.addUIElement(showSlotType);
        settings.addUIElement(allowAchievement);
        settings.addUIElement(allowPuzzleFactorDrop);
        settings.addUIElement(removeVelvetChoker);
        settings.addUIElement(removeDeadBranch);
        settings.addUIElement(removeStrangeSpoon);
        settings.addUIElement(encounterSpiegel);
        settings.addUIElement(removeTimeEater);
        BaseMod.registerModBadge(ImageMaster.loadImage(PublicRes.MOD_BADGE), ModInformation.MOD_NAME, Arrays.toString(AUTHORS), "An original character", settings);
    }
}
