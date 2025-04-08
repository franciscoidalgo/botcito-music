package com.frappu.app.command;

import java.util.List;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public interface ICommand {

  String getName();

  String getDescription();

  List<OptionData> getOptions();

  void execute(SlashCommandInteractionEvent event);

  default void onSelection(StringSelectInteractionEvent event) {
  }

}
