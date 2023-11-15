package io.greitan.mineserv;

import lombok.Getter;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.greitan.mineserv.listeners.PlayerJoinHandler;
import io.greitan.mineserv.utils.*;

import org.geysermc.floodgate.api.FloodgateApi;

public class SkipGeyser extends JavaPlugin {
    private static @Getter SkipGeyser instance;

    @Override
    public void onEnable() {
        instance = this;
        Logger.info("Plugin loaded");

        getServer().getPluginManager().registerEvents(new PlayerJoinHandler(this), this);

        this.reload();
    }

    public void reload() {
        saveDefaultConfig();
        reloadConfig();
    }

    public Boolean isGeyserPlayer(Player player) {
        FloodgateApi api = FloodgateApi.getInstance();
        Boolean isBedrockPlayer = api.isFloodgatePlayer(player.getUniqueId());

        Logger.info("Player " + player.getName() + "is bedrock? " + isBedrockPlayer);
        return isBedrockPlayer;
    }
}