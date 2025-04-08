package com.frappu.app.command;

import com.frappu.app.IModule;
import com.frappu.utils.BotColor;
import com.frappu.utils.BotUtils;
import java.util.Arrays;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Help implements ICommand {

  private final List<ICommand> commands;

  public Help(IModule... modules) {
    this.commands = Arrays
        .stream(modules)
        .map(IModule::getCommands)
        .flatMap(Arrays::stream)
        .toList();
  }

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public String getDescription() {
    return "Ayudame loco";
  }

  @Override
  public List<OptionData> getOptions() {
    return null;
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    EmbedBuilder embedBuilder = BotUtils
        .buildEmbed(BotColor.INFO)
        .setTitle("Music commands")
        .setDescription("Available commands:");

    for (ICommand musicCommand : this.commands) {
      if (!musicCommand
          .getName()
          .equals(this.getName())) {
        embedBuilder.appendDescription("\n- `" + musicCommand.getName() + "`: " + musicCommand.getDescription());
      }
    }
    event
        .replyEmbeds(embedBuilder.build())
        .queue();
  }

}
