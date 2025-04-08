package com.frappu;

import com.frappu.app.CommandManager;
import com.frappu.app.IModule;
import com.frappu.app.command.Help;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Botcito {

  private static final String DISCORD_BOT_TOKEN = "DISCORD_BOT_TOKEN";

  public static void run(IModule... modules) {
    String token = System.getenv(DISCORD_BOT_TOKEN);
    JDA jda = JDABuilder
        .createDefault(token)
        .build();
    CommandManager commandManager = new CommandManager();
    commandManager.addCommands(new Help());
    for (IModule module : modules) {
      commandManager.addCommands(module.getCommands());
    }
    jda.addEventListener(commandManager);
  }

}
