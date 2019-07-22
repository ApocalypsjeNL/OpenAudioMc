package com.craftmend.openaudiomc;

import com.craftmend.openaudiomc.bungee.OpenAudioMcBungee;
import com.craftmend.openaudiomc.bungee.modules.configuration.BungeeConfigurationModule;
import com.craftmend.openaudiomc.generic.interfaces.ConfigurationInterface;
import com.craftmend.openaudiomc.generic.media.MediaModule;
import com.craftmend.openaudiomc.generic.media.time.TimeService;
import com.craftmend.openaudiomc.generic.platform.Platform;
import com.craftmend.openaudiomc.spigot.OpenAudioMcSpigot;
import com.craftmend.openaudiomc.spigot.modules.api.objects.OpenAudioApi;
import com.craftmend.openaudiomc.spigot.modules.configuration.SpigotConfigurationModule;
import com.craftmend.openaudiomc.generic.networking.NetworkingService;
import com.craftmend.openaudiomc.generic.networking.abstracts.AbstractPacketPayload;
import com.craftmend.openaudiomc.generic.networking.addapter.AbstractPacketAdapter;
import com.craftmend.openaudiomc.spigot.services.server.ServerService;
import com.craftmend.openaudiomc.spigot.services.state.StateService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

@Getter
public class OpenAudioMcCore {

    /**
     * services OpenAudioMc uses in the background
     *
     *  - State service (keeps track of connections and state of the api)
     *  - Server service (compatibility and stuff)
     *  - authentication (auth)
     *  - time service (time sync with clients)
     *  - networking service (api connection)
     */
    private StateService stateService;
    private ServerService serverService;
    private TimeService timeService;
    private NetworkingService networkingService;

    /**
     * modules that are common across all platforms
     */
    private MediaModule mediaModule;
    private ConfigurationInterface configurationInterface;

    /**
     * Constant: main plugin instance
     */
    @Getter
    private static OpenAudioMcCore instance;

    /**
     * Constants:
     *  - api: the api
     *  - LOG_PREFIX: the prefix in server logs
     *  - Gson: the google json instance that is used with the type adapter
     */
    @Getter private static final OpenAudioApi api = new OpenAudioApi();
    @Getter private static final String LOG_PREFIX = "[OpenAudioMc-Log] ";
    @Getter private static final String server = "http://craftmendserver.eu";
    @Getter private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(AbstractPacketPayload.class, new AbstractPacketAdapter())
            .create();

    // The platform, easy for detecting what should be enabled and what not ya know
    private Platform platform;

    public OpenAudioMcCore(Platform platform) {
        instance = this;
        this.platform = platform;

        // if spigot, load the spigot configuration system and the bungee one for bungee
        if (platform == Platform.SPIGOT) {
            this.configurationInterface = new SpigotConfigurationModule(OpenAudioMcSpigot.getInstance());
        } else {
            this.configurationInterface = new BungeeConfigurationModule((OpenAudioMcBungee.getInstance()));
        }

        // enable stuff
        this.stateService = new StateService();
        this.timeService = new TimeService();
        this.mediaModule = new MediaModule();
        this.networkingService = new NetworkingService();
    }

}