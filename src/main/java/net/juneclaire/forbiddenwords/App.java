package net.juneclaire.forbiddenwords;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import net.md_5.bungee.api.ChatColor;

public class App extends JavaPlugin  {

    public static App plugin;

    private static String pluginName = "ForbiddenWords";

    private static PluginManager pm;

    public static String jarPath() throws URISyntaxException {
        return new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
    }

    Words words = new Words();

    private static String[] permissions = new String[]{
        "killable",
        "admin",
    };

    //Calculates time until 12am Sunday server time
    public long timeUntil() {
        LocalTime resetTime = LocalTime.parse("23:59:59");
        LocalTime timeNow = LocalTime.now();
        return timeNow.until(resetTime, ChronoUnit.SECONDS);
    }


    // Alters the number on the file that determines how many words have been read through.
    public void incrementWeeks() throws IOException, URISyntaxException {

        File file = new File(jarPath() + "\\ForbiddenWords\\progressed.txt");
        Scanner scnr = new Scanner(file);
        int newNumber = scnr.nextInt() + 1;
        scnr.close();
    
        FileWriter writer = new FileWriter(file, false);
        writer.write(String.valueOf(newNumber));
        writer.close();
        String currentWord = words.chooseWord();
        getLogger().info("Word has been changed to " + currentWord);
    }



    @Override
    public void onEnable() {


        BukkitScheduler scheduler = Bukkit.getScheduler();
        plugin = this;
        pm = getServer().getPluginManager();

        try {
            String currentWord = words.chooseWord();
            getLogger().info("ForbiddenWords activated, current word is " + currentWord);
        } catch (IOException | URISyntaxException e1) {
            Bukkit.getLogger().warning("Go tell June she's an idiot and her plugin broke... \nForbiddenWords broke trying to start up.");
        }

        for (String permission : permissions) {
            pm.addPermission(new org.bukkit.permissions.Permission(pluginName.toLowerCase() + "." + permission));
        }

        this.getCommand("capybara").setExecutor(new BaraCommand());

        pm.registerEvents(new listener(), this);

        /* Calls methods to check what day it is and how long until the word needs to be changed to the next one if it is Saturday.
        The word changes every Sunday at roughly 12am but it is set to Saturday at 23:59:59. */

        String dayOfWeek = LocalDate.now().getDayOfWeek().toString();

        if(dayOfWeek.equalsIgnoreCase("SATURDAY")) {
            getLogger().info(timeUntil() + " seconds until word changes");
            scheduler.runTaskLater(this, new Runnable() {

                @Override
                public void run() {
                    try {
                        String currentWord = words.chooseWord();
                        String previousWord = currentWord;
                        incrementWeeks();
                        Bukkit.broadcastMessage(ChatColor.DARK_RED + "The word has been changed... \nLast week's word was " + previousWord);
                    } catch (IOException | URISyntaxException e) {
                        Bukkit.getLogger().warning("Go tell June she's an idiot and her plugin broke... \nForbiddenWords broke trying to increment to the next week." );
                    }
                    
                }
                
            }, timeUntil()*20);

        }

         
    }
    @Override
    public void onDisable() {
        getLogger().info("Bye bitches");
        plugin = null;
    }

    public static App getPlugin() {
        return plugin;
    }
}
