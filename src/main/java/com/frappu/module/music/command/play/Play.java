package com.frappu.module.music.command.play;

import com.frappu.app.command.ICommand;
import com.frappu.app.command.IOption;
import com.frappu.module.music.player.MusicManager;
import com.frappu.utils.BotUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

@Singleton
public class Play implements ICommand {

  private final MusicManager musicManager;

  @Inject
  public Play(MusicManager musicManager) {
    this.musicManager = musicManager;
  }

  @Override
  public String getName() {
    return "play";
  }

  @Override
  public String getDescription() {
    return "Mandale mecha";
  }

  @Override
  public IOption[] getOptions() {
    return PlayOptions.values();
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    String name = event
        .getOption(PlayOptions.QUERY.getName())
        .getAsString();
    boolean isSearch = false;
    try {
      new URI(name);
    } catch (URISyntaxException e) {
      name = "ytmsearch:" + name;
      isSearch = true;
    }

    if (!isSearch && !BotUtils.validateAndConnectToVoice(event)) {
      return;
    }
    event
        .deferReply()
        .queue();
    this.musicManager.play(event, name);
  }

  @Override
  public void onSelection(StringSelectInteractionEvent event) {
    if (!BotUtils.validateAndConnectToVoice(event)) {
      return;
    }

    String selectedUri = event
        .getSelectedOptions()
        .getFirst()
        .getValue();

    event
        .deferReply()
        .queue();
    this.musicManager.play(event, selectedUri);
  }

}
