package com.frappu.module.music.command;

import com.frappu.app.command.ICommand;
import com.frappu.module.music.player.MusicManagers;
import com.frappu.utils.BotUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Play implements ICommand {

  private static final List<OptionData> OPTIONS = Collections.singletonList(
      new OptionData(OptionType.STRING, "query", "Name of the song or url", true));

  @Override
  public String getName() {
    return "play";
  }

  @Override
  public String getDescription() {
    return "Play music";
  }

  @Override
  public List<OptionData> getOptions() {
    return OPTIONS;
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    String name = event
        .getOption("name")
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
    MusicManagers musicManagers = MusicManagers.get();
    musicManagers.play(event, name);
  }

  @Override
  public void onSelection(StringSelectInteractionEvent event) {
    if (!BotUtils.validateAndConnectToVoice(event)) {
      return;
    }

    String selectedUri = event
        .getSelectedOptions()
        .get(0)
        .getValue();

    event
        .deferReply()
        .queue();
    MusicManagers musicManagers = MusicManagers.get();
    musicManagers.play(event, selectedUri);
  }

}
