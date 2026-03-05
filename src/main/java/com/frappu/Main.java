package com.frappu;

import com.frappu.module.music.MusicModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

  static void main() {
    Injector injector = Guice.createInjector(new MusicModule());
    Botcito botcito = injector.getInstance(Botcito.class);
    botcito.run();
  }

}