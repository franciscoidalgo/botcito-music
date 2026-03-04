package com.frappu.app.command;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public interface IOption {

  IOption[] EMPTY = {};

  OptionData getOptionData();

  String getName();

}
