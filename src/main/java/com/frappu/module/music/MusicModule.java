package com.frappu.module.music;

import com.frappu.app.command.ICommand;
import com.frappu.module.music.command.kick.Kick;
import com.frappu.module.music.command.pause.Pause;
import com.frappu.module.music.command.play.Play;
import com.frappu.module.music.command.queue.Queue;
import com.frappu.module.music.command.show.Show;
import com.frappu.module.music.command.skip.Skip;
import com.frappu.module.music.command.stop.Stop;
import com.frappu.module.music.command.volume.Volume;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.ProvidesIntoSet;

public class MusicModule extends AbstractModule {

  @ProvidesIntoSet
  ICommand play() {
    return new Play();
  }

  @ProvidesIntoSet
  ICommand pause() {
    return new Pause();
  }


  @ProvidesIntoSet
  ICommand stop() {
    return new Stop();
  }

  @ProvidesIntoSet
  ICommand skip() {
    return new Skip();
  }

  @ProvidesIntoSet
  ICommand queue() {
    return new Queue();
  }

  @ProvidesIntoSet
  ICommand show() {
    return new Show();
  }

  @ProvidesIntoSet
  ICommand kick() {
    return new Kick();
  }

  @ProvidesIntoSet
  ICommand volume() {
    return new Volume();
  }


}
