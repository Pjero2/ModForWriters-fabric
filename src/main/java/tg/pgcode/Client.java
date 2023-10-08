package tg.pgcode;

import net.fabricmc.api.ClientModInitializer;

public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeybindsController.register();
    }
}
