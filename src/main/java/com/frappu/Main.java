package com.frappu;

import com.frappu.command.Help;
import com.frappu.command.music.MusicCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {

  private static final String DISCORD_BOT_TOKEN = "DISCORD_BOT_TOKEN";

  public static void main(String[] args) {
    String token = System.getenv(DISCORD_BOT_TOKEN);
    JDA jda = JDABuilder
        .createDefault(token)
        .build();
    CommandManager commandManager = new CommandManager();
    commandManager.addCommands(MusicCommands.get());
    commandManager.addCommands(new Help());
    jda.addEventListener(commandManager);
  }

}