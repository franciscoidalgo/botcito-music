package com.frappu.command;

import com.frappu.command.music.MusicCommands;
import com.frappu.utils.ColorConstants;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Help implements ICommand {

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
    EmbedBuilder embedBuilder = new EmbedBuilder()
        .setColor(ColorConstants.INFO)
        .setTitle("Music commands")
        .setDescription("Available commands:");

    for (ICommand musicCommand : MusicCommands.get()) {
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
