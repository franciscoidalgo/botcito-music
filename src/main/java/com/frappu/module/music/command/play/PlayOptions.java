package com.frappu.module.music.command.play;

import com.frappu.app.command.IOption;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public enum PlayOptions implements IOption {
  QUERY(OptionType.STRING, "query", "Name of the song or url", true);

  private final OptionData optionData;

  PlayOptions(OptionType type, String name, String description, boolean isRequired) {
    this.optionData = new OptionData(type, name, description, isRequired);
  }

  @Override
  public OptionData getOptionData() {
    return this.optionData;
  }

  @Override
  public String getName() {
    return this.optionData.getName();
  }

}
