package com.frappu.app.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public interface ICommand {

  String getName();

  String getDescription();

  default IOption[] getOptions() {
    return IOption.EMPTY;
  }

  void execute(SlashCommandInteractionEvent event);

  default void onSelection(StringSelectInteractionEvent event) {
  }

}
