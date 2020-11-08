package com.lielamar.auth.bukkit.commands.subcommands;

import com.lielamar.auth.bukkit.Main;
import com.lielamar.auth.bukkit.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand extends Command {

    private final Main main;
    public LoginCommand(String name, Main main) {
        super(name);
        this.main = main;
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String[] getPermissions() {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;

        if(args.length == 0) {
            main.getMessageHandler().sendMessage(player, "&cUsage: /2fa <code>");
        } else {
            try {
                StringBuilder code = new StringBuilder();
                for(String arg : args)
                    code.append(arg);

                boolean isValid = main.getAuthHandler().validateKey(player.getUniqueId(), Integer.valueOf(code.toString()));
                if(isValid) {
                    main.getMessageHandler().sendMessage(player, "&aYou have successfully authenticated");
                } else {
                    main.getMessageHandler().sendMessage(player, "&cIncorrect code, please try again");
                }
            } catch (Exception e) {
                main.getMessageHandler().sendMessage(player, "&cThe code you entered was not valid, please try again");
            }
        }
    }
}
