package com.github.Jena;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("deprecation")
public class BungeeExecutor extends Command {
    String string;

    public BungeeExecutor(String string) {
        super(string);
        this.string = string;
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (cs instanceof ProxiedPlayer) {
            final ServerInfo serverInfo = BungeeCord.getInstance().getServerInfo(string);
            if (serverInfo.getMotd() != null) {
                if (serverInfo.canAccess(cs)) {
                    String message = BungeeMain.configuration.getString("message").replace("%s", string);
                    cs.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    ((ProxiedPlayer) cs).connect(serverInfo);
                }
            } else {
                BungeeMain.logger.severe(string + " is an invalid server");
                cs.sendMessage("§cError, check console for more details.");
            }
        } else {cs.sendMessage("§cThis can only be executed by a player.");}
    }
}
