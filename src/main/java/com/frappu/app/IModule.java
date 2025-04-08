package com.frappu.app;

import com.frappu.app.command.ICommand;

public interface IModule {

  ICommand[] getCommands();

}
