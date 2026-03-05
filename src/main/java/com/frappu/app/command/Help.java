package com.frappu.app.command;

import com.frappu.utils.BotColor;
import com.frappu.utils.BotUtils;
import java.util.Set;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Help implements ICommand {

  private final Set<ICommand> commands;

  public Help(Set<ICommand> commands) {
    this.commands = commands;
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
