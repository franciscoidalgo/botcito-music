package com.frappu;

import club.minnced.discord.jdave.interop.JDaveSessionFactory;
import com.frappu.app.CommandManager;
import com.frappu.app.command.Help;
import com.frappu.app.command.ICommand;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Set;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.audio.AudioModuleConfig;

@Singleton
public class Botcito {

  private static final String DISCORD_BOT_TOKEN = "DISCORD_BOT_TOKEN";

  private final Set<ICommand> commands;

  @Inject
  public Botcito(Set<ICommand> commands) {
    this.commands = commands;
  }

  public void run() {
    String token = System.getenv(DISCORD_BOT_TOKEN);
    JDA jda =
        JDABuilder
            .createDefault(token)
            .setAudioModuleConfig(
                new AudioModuleConfig().withDaveSessionFactory(new JDaveSessionFactory()))
            .build();
    CommandManager commandManager = new CommandManager();
    commandManager.addCommand(new Help(this.commands));
    for (ICommand command : this.commands) {
      commandManager.addCommand(command);
    }
    jda.addEventListener(commandManager);
  }

}
