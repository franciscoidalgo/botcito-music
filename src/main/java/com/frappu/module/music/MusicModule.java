package com.frappu.module.music;

import com.frappu.app.IModule;
import com.frappu.app.command.ICommand;
import com.frappu.module.music.command.Kick;
import com.frappu.module.music.command.Pause;
import com.frappu.module.music.command.Play;
import com.frappu.module.music.command.Queue;
import com.frappu.module.music.command.Show;
import com.frappu.module.music.command.Skip;
import com.frappu.module.music.command.Stop;

public class MusicModule implements IModule {

  @Override
  public ICommand[] getCommands() {
    return new ICommand[]{new Play(), new Pause(), new Stop(), new Skip(), new Queue(), new Show(), new Kick()};
  }

}
