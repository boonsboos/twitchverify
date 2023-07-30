package nl.boonsboos.twitchverify;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class VerifyCommand implements SimpleCommand {

    private final ProxyServer server;
    private final Logger log;

    public VerifyCommand(ProxyServer s, Logger l) {
        this.server = s;
        this.log = l;

        log.info("TwitchVerify starting...");
    }


    @Override
    public void execute(Invocation invocation) {
        Player p = (Player) invocation.source();

        if (invocation.arguments().length == 0) {
            p.sendMessage(Component.text("Please enter your twitch username!", TextColor.fromHexString("#FF0000")));
            return;
        }

        String link = "verify.boonsboos.nl/?username=" + invocation.arguments()[0];

        p.sendMessage(
            Component.text("Click here to verify your twitch account: ")
                .append(Component.text(link)
                    .clickEvent(ClickEvent.openUrl(link)))
        );

        p.createConnectionRequest(server.getServer("smp").get()).fireAndForget();
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return ((Player) invocation.source())
                .getCurrentServer().get().getServer().equals(server.getServer("verify").get());
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return List.of("<twitch username>");
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.completedFuture(List.of("<twitch username>"));
    }
}
