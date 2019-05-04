package com.ericlam.mc.ranking.bukkit.commands.datahandle;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

abstract class CommandRunnable extends BukkitRunnable {

    OfflinePlayer player;
    CommandSender sender;

    CommandRunnable setPlayer(OfflinePlayer player) {
        this.player = player;
        return this;
    }

    CommandRunnable setSender(CommandSender sender) {
        this.sender = sender;
        return this;
    }
}
