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

    public static ForgeConfigSpec.BooleanValue removeSmokeParticles;

    static {
        COMMON_BUILDER.comment("Better Weather Settings").push("Acid_Rain_Settings").push("World_Settings");
        acidRainChance = COMMON_BUILDER.comment("The chance of acid rain that's checked every 5000 ticks.").defineInRange("Chance", 0.1, 0.0, 1.0);
        tickBlockDestroySpeed = COMMON_BUILDER.comment("How often blocks are destroyed during an acid rain event.").defineInRange("BlockDestroyTickSpeed", 500, 10, 100000);
        destroyBlocks = COMMON_BUILDER.comment("Destroy Blocks?").define("DestroyBlocks", true);
        allowedBlocksToDestroy = COMMON_BUILDER.comment("Destroy what block materials? Allowed Values: GRASS,LEAVES,PLANTS,CROPS\nDefault Value: GRASS,LEAVES,PLANTS,CROPS").define("BlocksToDestroy", "GRASS,LEAVES,PLANTS,CROPS");
        blocksToNotDestroy = COMMON_BUILDER.comment("A specific block in one of the materials you want to destroy? Blacklist them here w/ their registry names! \n I.E: \"minecraft:rose,minecraft:wither_rose\"").define("BlocksToNotDestroy", "");
        blockToChangeFromGrass = COMMON_BUILDER.comment("The block to change grass to.\nDefault: \"minecraft:coarse_dirt\"").define("BlockToChangeFromGrass", "minecraft:coarse_dirt");
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Entity_Settings");
        hurtEntities = COMMON_BUILDER.comment("Hurt Entities?").define("HurtEntities", true);
        hurtEntityTickSpeed = COMMON_BUILDER.comment("How often are entities(including players) hurt?").defineInRange("EntityDamageTickSpeed", 150, 10, 100000);
        entityTypesToDamage = COMMON_BUILDER.comment("Allowed Values: PLAYER, MONSTER, ANIMAL.\nDefault: MONSTER,PLAYER").define("EntityTypes", "MONSTER,PLAYER");
        hurtEntityDamage = COMMON_BUILDER.comment("The amount of damage taken.\nDefault is 0.5").defineInRange("DamageStrength", 0.5, 0.0, 20.0);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Client_Settings");
        removeSmokeParticles = COMMON_BUILDER.comment("Remove the smoke particles emitted by the acid rain.\n Default is false.").define("RemoveParticles", false);
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