package corgitaco.betterweather.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import corgitaco.betterweather.BetterWeather;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = BetterWeather.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BetterWeatherConfig {

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.DoubleValue acidRainChance;
    public static ForgeConfigSpec.IntValue tickBlockDestroySpeed;
    public static ForgeConfigSpec.BooleanValue destroyBlocks;
    public static ForgeConfigSpec.ConfigValue<String> allowedBlocksToDestroy;
    public static ForgeConfigSpec.BooleanValue hurtEntities;
    public static ForgeConfigSpec.IntValue hurtEntityTickSpeed;
    public static ForgeConfigSpec.DoubleValue hurtEntityDamage;
    public static ForgeConfigSpec.ConfigValue<String> entityTypesToDamage;
    public static ForgeConfigSpec.ConfigValue<String> blockToChangeFromGrass;
    public static ForgeConfigSpec.ConfigValue<String> blocksToNotDestroy;

    //Blizzard Configs
    public static ForgeConfigSpec.BooleanValue doBlizzardsOccurInDeserts;
    public static ForgeConfigSpec.BooleanValue spawnSnowAndIce;
    public static ForgeConfigSpec.BooleanValue decaySnowAndIce;
    public static ForgeConfigSpec.BooleanValue doBlizzardsSlowPlayers;
    public static ForgeConfigSpec.BooleanValue doBlizzardsDestroyPlants;
    public static ForgeConfigSpec.IntValue tickSnowAndIcePlaceSpeed;
    public static ForgeConfigSpec.IntValue tickSnowAndIceDecaySpeed;
    public static ForgeConfigSpec.IntValue blizzardSlownessAmplifier;
    public static ForgeConfigSpec.DoubleValue blizzardChance;
    public static ForgeConfigSpec.DoubleValue snowDecayTemperatureThreshold;


    static {
        COMMON_BUILDER.comment("Better Weather Settings").push("Acid_Rain_Settings").push("World_Settings");
        acidRainChance = COMMON_BUILDER.comment("The chance of acid rain that's checked every 5,000 ticks.\n Default: 0.25").defineInRange("AcidRainChance", 0.2, 0.0, 1.0);
        tickBlockDestroySpeed = COMMON_BUILDER.comment("How often blocks are destroyed during an acid rain event.\nDefault: 500").defineInRange("BlockDestroyTickSpeed", 500, 1, 100000);
        destroyBlocks = COMMON_BUILDER.comment("Destroy Blocks?\nDefault: true").define("DestroyBlocks", true);
        allowedBlocksToDestroy = COMMON_BUILDER.comment("Destroy what block materials? Allowed Values: GRASS,LEAVES,PLANTS,CROPS\nDefault: GRASS,LEAVES,PLANTS,CROPS").define("BlocksToDestroy", "GRASS,LEAVES,PLANTS,CROPS");
        blocksToNotDestroy = COMMON_BUILDER.comment("A specific block in one of the materials you want to destroy? Blacklist them here w/ their registry names! \nI.E: \"minecraft:rose,minecraft:wither_rose\"").define("BlocksToNotDestroy", "");
        blockToChangeFromGrass = COMMON_BUILDER.comment("The block to change grass to.\nDefault: \"minecraft:coarse_dirt\"").define("BlockToChangeFromGrass", "minecraft:coarse_dirt");
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Entity_Settings");
        hurtEntities = COMMON_BUILDER.comment("Hurt Entities?\nDefault: true").define("HurtEntities", true);
        hurtEntityTickSpeed = COMMON_BUILDER.comment("How often are entities(including players) hurt?\nDefault: 150").defineInRange("EntityDamageTickSpeed", 150, 10, 100000);
        entityTypesToDamage = COMMON_BUILDER.comment("Allowed Values: PLAYER, MONSTER, ANIMAL.\nDefault: MONSTER,PLAYER").define("EntityTypes", "MONSTER,PLAYER");
        hurtEntityDamage = COMMON_BUILDER.comment("The amount of damage taken.\nDefault is 0.5").defineInRange("DamageStrength", 0.5, 0.0, 20.0);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Blizzard_Settings").push("World_Settings").push("Snow_Generation");
        blizzardChance = COMMON_BUILDER.comment("The chance of a blizzard that's checked every 5,000 ticks.\n Default: 0.1").defineInRange("BlizzardChance", 0.1, 0.0, 1.0);
        doBlizzardsOccurInDeserts = COMMON_BUILDER.comment("Do blizzards appear in and generate snow in deserts?\nDefault: false.").define("BlizzardsInDeserts", false);
        spawnSnowAndIce = COMMON_BUILDER.comment("Do blizzards generate snow and ice?\nDefault: true").define("SpawnSnowAndIce", true);
        tickSnowAndIcePlaceSpeed = COMMON_BUILDER.comment("How often is snow and ice placed during blizzards?\nDefault: 20").defineInRange("SnowAndIceBlockPlaceTickSpeed", 20, 1, 100000);
        doBlizzardsDestroyPlants = COMMON_BUILDER.comment("Do blizzards destroy plants?\nDefault: false").define("BlizzardsDestroyPlants", false);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Snow_Decay");
        decaySnowAndIce = COMMON_BUILDER.comment("Does snow and ice decay after blizzards in biomes within the specified temperature threshold?\nRecommend disabling this if you have a seasons mod installed!\nDefault: true").define("DecaySnowAndIce", true);
        snowDecayTemperatureThreshold = COMMON_BUILDER.comment("Above what biome temperature is snow allowed to decay?\nI.E: Biome Temperature >= 0.15(Snow decays in biome temps warmer than 0.15).\nDefault: 0.15").defineInRange("SnowDecayTemperatureThreshold", 0.15, -2.0, 2.0);
        tickSnowAndIceDecaySpeed = COMMON_BUILDER.comment("How often is snow and ice decayed after blizzards?\nDefault: 50").defineInRange("SnowAndIceBlockDecayTickSpeed", 50, 1, 100000);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Entity_Settings");
        doBlizzardsSlowPlayers = COMMON_BUILDER.comment("Do blizzards slow entities?\nDefault: true.").define("DoBlizzardsSlowEntities", true);
        blizzardSlownessAmplifier = COMMON_BUILDER.comment("How slow are entities during blizzards?\nDefault: 0").defineInRange("BlizzardSlownessAmplifier", 0, 0, 20);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec config, Path path) {
        BetterWeather.LOGGER.info("Loading config: " + path);
        CommentedFileConfig file = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }
}
