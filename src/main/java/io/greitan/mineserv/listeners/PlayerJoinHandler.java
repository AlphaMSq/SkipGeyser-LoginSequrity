package io.greitan.mineserv.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.greitan.mineserv.SkipGeyser;
import io.greitan.mineserv.utils.Logger;
import io.greitan.mineserv.utils.RandomPassword;

import java.util.Objects;

public class PlayerJoinHandler implements Listener {

    private final SkipGeyser plugin;

    public PlayerJoinHandler(SkipGeyser plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Boolean isBedrock = plugin.isGeyserPlayer(player);
        String playerPassword = plugin.getConfig().getString("config.players." + player.getName());

        if(isBedrock){
            if(Objects.isNull(playerPassword)){
                String randomPassword = RandomPassword.generatePassword();
                Bukkit.dispatchCommand(player, "register " + randomPassword);
                plugin.getConfig().set("config.players." + player.getName(), randomPassword);
                plugin.saveConfig();
                Logger.info("Created password " + randomPassword + " for player " + player.getName());
            } else {
                Bukkit.dispatchCommand(player, "login " + playerPassword);
                Logger.info("Used password " + playerPassword + " for player " + player.getName());
            }
        } else {
            if(Objects.nonNull(playerPassword)){
                Boolean isLinked = plugin.isLinkedAccount(player);
                if(isLinked){
                    Bukkit.dispatchCommand(player, "login " + playerPassword);
                    Logger.info("Used password " + playerPassword + " for java player " + player.getName());
                }
            }
        }
    }
}
