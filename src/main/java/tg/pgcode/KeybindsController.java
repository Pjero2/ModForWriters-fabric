package tg.pgcode;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import tg.pgcode.gui.MainWriterGUI;
import tg.pgcode.gui.WriterScreen;

public class KeybindsController {
    public static final KeyBinding WRITER_MENU = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.writer.writer",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_I,
            "key.categories.misc"
    ));

    public static void register(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (WRITER_MENU.isPressed()) {
                if (client.player.getInventory().getMainHandStack().isOf(Items.AIR))
                    client.player.sendMessage(Text.translatable("hud.writer.air").formatted(Formatting.GOLD), true);
                else client.setScreen(new WriterScreen(new MainWriterGUI()));
            }
        });
    }
}
