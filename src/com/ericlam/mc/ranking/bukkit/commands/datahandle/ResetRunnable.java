package com.ericlam.mc.ranking.bukkit.commands.datahandle;

import com.ericlam.mc.rankcal.RankDataManager;

public class ResetRunnable extends CommandRunnable {

    @Override
    public void run() {
        if (player == null) {
            sender.sendMessage("§cPlayer not found.");
            return;
        }

        boolean success = RankDataManager.getInstance().getDataHandler().removePlayerData(player);
        sender.sendMessage("§edelete" + (success ? "success" : "failed"));
    }

}
