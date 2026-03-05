package com.frappu.module.music.command.kick;

import com.frappu.app.command.ICommand;
import com.frappu.utils.BotUtils;
import jakarta.inject.Singleton;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Singleton
public class Kick implements ICommand {

  @Override
  public String getName() {
    return "kick";
  }

  @Override
  public String getDescription() {
    return "Toca de aca";
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    if (!BotUtils.validateIsInSameChannel(event)) {
      return;
    }

    EmbedBuilder embedBuilder = new EmbedBuilder()
        .setTitle("Kick")
        .setDescription("Bye...");

    event
        .replyEmbeds(embedBuilder.build())
        .queue();

    event
        .getGuild()
        .getAudioManager()
        .closeAudioConnection();

  }

}
