package io.greitan.mineserv;

import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.greitan.mineserv.listeners.PlayerJoinHandler;
import io.greitan.mineserv.utils.*;

import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.link.PlayerLink;

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

    public Boolean isLinkedAccount(Player player) {
        FloodgateApi api = FloodgateApi.getInstance();
        PlayerLink link = api.getPlayerLink();
        
        UUID uuid = player.getUniqueId();
        CompletableFuture<Boolean> linkedFuture = link.isLinkedPlayer(uuid);

        boolean isLinked;
        try {
            isLinked = linkedFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return isLinked;
    }
}