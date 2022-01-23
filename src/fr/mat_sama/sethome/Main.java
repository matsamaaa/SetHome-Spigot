package fr.mat_sama.sethome;

import fr.mat_sama.sethome.commands.DelhomeCommand;
import fr.mat_sama.sethome.commands.HomeCommand;
import fr.mat_sama.sethome.commands.SethomeCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public String prefix = "§6[§3SetHome§6] ";

    @Override
    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + getDescription().getName() + " cree par " + getDescription().getAuthors() + " est maintenant charge.");

        getCommand("sethome").setExecutor(new SethomeCommand(this));
        getCommand("delhome").setExecutor(new DelhomeCommand(this));
        getCommand("home").setExecutor(new HomeCommand(this));

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
