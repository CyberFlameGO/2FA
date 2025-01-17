package com.lielamar.auth.bungee.handlers;

import com.lielamar.auth.bungee.TwoFactorAuthentication;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigHandler extends com.lielamar.auth.shared.handlers.ConfigHandler {

    private final TwoFactorAuthentication plugin;

    private boolean disableCommands = true;
    private boolean disableChat = true;
    private boolean disableServerSwitch = true;

    public ConfigHandler(@NotNull TwoFactorAuthentication plugin) {
        this.plugin = plugin;

        reload();
    }

    @Override
    public void reload() {
        if(!this.plugin.getDataFolder().exists())
            this.plugin.getDataFolder().mkdir();

        File file = new File(this.plugin.getDataFolder(), super.configFileName);

        if(!file.exists()) {
            try(InputStream in = this.plugin.getResourceAsStream("bungeeconfig.yml")) {
                Files.copy(in, file.toPath());
            } catch(IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

            if(!config.contains("disabled-events.commands"))
                config.set("disabled-events.commands", this.disableCommands);
            else
                this.disableCommands = config.getBoolean("disabled-events.commands");

            if(!config.contains("disabled-events.chat"))
                config.set("disabled-events.chat", this.disableChat);
            else
                this.disableChat = config.getBoolean("disabled-events.chat");

            if(!config.contains("disabled-events.server-switch"))
                config.set("disabled-events.server-switch", this.disableServerSwitch);
            else
                this.disableServerSwitch = config.getBoolean("disabled-events.server-switch");

            if(!config.contains("whitelisted-commands"))
                config.set("whitelisted-commands", this.whitelistedCommands);
            else
                super.whitelistedCommands = config.getStringList("whitelisted-commands");

            if(!config.contains("blacklisted-commands"))
                config.set("blacklisted-commands", this.blacklistedCommands);
            else
                super.blacklistedCommands = config.getStringList("blacklisted-commands");

            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean isDisableCommands() {
        return this.disableCommands;
    }
    public boolean isDisableChat() {
        return this.disableChat;
    }
    public boolean isDisableServerSwitch() {
        return this.disableServerSwitch;
    }
}