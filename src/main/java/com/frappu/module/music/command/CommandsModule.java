package com.frappu.module.music.command;

import com.frappu.app.command.ICommand;
import com.frappu.module.music.command.kick.Kick;
import com.frappu.module.music.command.pause.Pause;
import com.frappu.module.music.command.play.Play;
import com.frappu.module.music.command.queue.Queue;
import com.frappu.module.music.command.show.Show;
import com.frappu.module.music.command.skip.Skip;
import com.frappu.module.music.command.stop.Stop;
import com.frappu.module.music.command.volume.Volume;
import com.frappu.module.music.player.MusicManager;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.ProvidesIntoSet;

public class CommandsModule extends AbstractModule {

  @ProvidesIntoSet
  ICommand play(MusicManager musicManager) {
    return new Play(musicManager);
  }

  @ProvidesIntoSet
  ICommand pause(MusicManager musicManager) {
    return new Pause(musicManager);
  }


  @ProvidesIntoSet
  ICommand stop(MusicManager musicManager) {
    return new Stop(musicManager);
  }

  @ProvidesIntoSet
  ICommand skip(MusicManager musicManager) {
    return new Skip(musicManager);
  }

  @ProvidesIntoSet
  ICommand queue(MusicManager musicManager) {
    return new Queue(musicManager);
  }

  @ProvidesIntoSet
  ICommand show(MusicManager musicManager) {
    return new Show(musicManager);
  }

  @ProvidesIntoSet
  ICommand kick() {
    return new Kick();
  }

  @ProvidesIntoSet
  ICommand volume(MusicManager musicManager) {
    return new Volume(musicManager);
  }


}
