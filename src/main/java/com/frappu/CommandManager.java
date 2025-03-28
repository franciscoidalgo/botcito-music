package com.frappu;

import com.frappu.command.ICommand;
import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {

  private final Map<String, ICommand> commands;

  public CommandManager() {
    this.commands = new HashMap<>();
  }

  @Override
  public void onReady(ReadyEvent event) {
    event
        .getJDA()
        .getGuilds()
        .forEach(this::configureGuildCommands);
  }

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    this.commands
        .get(event.getName())
        .execute(event);
  }


  @Override
  public void onStringSelectInteraction(StringSelectInteractionEvent event) {
    this.commands
        .get(event
                 .getSelectMenu()
                 .getId())
        .onSelection(event);
  }

  public void addCommands(ICommand... newCommands) {
    for (ICommand command : newCommands) {
      this.commands.put(command.getName(), command);
    }
  }

  private void configureGuildCommands(Guild guild) {
    this.commands.forEach((commandName, command) -> {
      if (command.getOptions() == null || command
          .getOptions()
          .isEmpty()) {
        guild
            .upsertCommand(commandName, command.getDescription())
            .queue();
      } else {
        guild
            .upsertCommand(commandName, command.getDescription())
            .addOptions(command.getOptions())
            .queue();
      }
    });
  }


}
