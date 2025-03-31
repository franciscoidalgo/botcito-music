package com.frappu.command.music;

import com.frappu.command.ICommand;

public final class MusicCommands {

  private static final ICommand[] COMMANDS = {new Play(), new Pause(), new Stop(), new Skip(), new Queue(), new Show(), new Kick()};

  private MusicCommands() {
  }

  public static ICommand[] get() {
    return COMMANDS;
  }

}
