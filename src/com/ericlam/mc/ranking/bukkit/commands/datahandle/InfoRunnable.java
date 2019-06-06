package com.ericlam.mc.ranking.bukkit.commands.datahandle;

import com.ericlam.mc.rankcal.RankDataManager;

public class InfoRunnable extends CommandRunnable {

    @Override
    public void run() {
        if (player == null) {
            sender.sendMessage("§cPlayer not found.");
            return;
        }

        String[] tosend = RankDataManager.getInstance().getDataHandler().showPlayerData(player);

        if (tosend == null || tosend.length == 0) {
            sender.sendMessage("§cThe implementation has empty string on showing data");
            return;
        }
        sender.sendMessage(tosend);
    }
}
