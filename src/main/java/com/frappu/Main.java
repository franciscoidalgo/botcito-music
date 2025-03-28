package com.frappu;

import com.frappu.command.music.Kick;
import com.frappu.command.music.Pause;
import com.frappu.command.music.Play;
import com.frappu.command.music.Queue;
import com.frappu.command.music.Show;
import com.frappu.command.music.Skip;
import com.frappu.command.music.Stop;
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
    commandManager.addCommands(new Play(), new Stop(), new Pause(), new Skip(), new Kick(), new Show(), new Queue());
    jda.addEventListener(commandManager);
  }

}