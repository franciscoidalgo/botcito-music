package com.frappu.module.music;

import com.frappu.app.IModule;
import com.frappu.app.command.ICommand;
import com.frappu.module.music.command.kick.Kick;
import com.frappu.module.music.command.pause.Pause;
import com.frappu.module.music.command.play.Play;
import com.frappu.module.music.command.queue.Queue;
import com.frappu.module.music.command.show.Show;
import com.frappu.module.music.command.skip.Skip;
import com.frappu.module.music.command.stop.Stop;
import com.frappu.module.music.command.volume.Volume;

public class MusicModule implements IModule {

  @Override
  public ICommand[] getCommands() {
    return new ICommand[]{new Play(), new Pause(), new Stop(), new Skip(), new Queue(), new Show(), new Kick(), new Volume()};
  }

}
