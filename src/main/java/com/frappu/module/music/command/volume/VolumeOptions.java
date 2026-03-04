package com.frappu.module.music.command.volume;

import com.frappu.app.command.IOption;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public enum VolumeOptions implements IOption {
  VOLUME_VALUE(OptionType.INTEGER, "volume", "Volume value 0-100", false);

  private final OptionData optionData;

  VolumeOptions(OptionType type, String name, String description, boolean isRequired) {
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
