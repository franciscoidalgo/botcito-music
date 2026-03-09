package com.frappu;

import com.frappu.module.music.command.CommandsModule;
import com.frappu.module.music.player.PlayerModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

  static void main() {
    Injector injector = Guice.createInjector(new CommandsModule(), new PlayerModule());
    Botcito botcito = injector.getInstance(Botcito.class);
    botcito.run();
  }

}