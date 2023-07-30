package nl.boonsboos.twitchverify;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

@Plugin(id = "twitchverify", name = "TwitchVerify", version = "1.0",
        url = "https://github.com/boonsboos/twitchverify", description = "Make sure your users are subbed to your Twitch channel", authors = {"boonsboos"})
public class TwitchVerifyMain {

    private final ProxyServer server;
    private final Logger log;

    @Inject
    public TwitchVerifyMain(ProxyServer s, Logger l) {
        this.server = s;
        this.log = l;

        log.info("TwitchVerify starting...");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.from("bungeecord:main"));
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.from("twitchverify:main"));

        server.getCommandManager().register("verify", new VerifyCommand(server, log));
        log.info("Fully initialised and listening for registrations.");

    }



}
