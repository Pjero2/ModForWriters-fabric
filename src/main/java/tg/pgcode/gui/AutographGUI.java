package tg.pgcode.gui;

import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class AutographGUI extends AbstractWGUI{
    @Override
    protected void setInfo() {
        name = Text.translatable("gui.writer.autograph.label");
        allowedItems = null;

        widgetsList.add(new WTextField(Text.translatable("gui.writer.autograph.text")).setMaxLength(128));
        WTextField[] secondLine = {
                null,
                new WTextField(Text.translatable("gui.writer.autograph.autograph")).setMaxLength(24)
        };
        widgetsList.add(secondLine);
    }

    @Override
    protected void sign() {
        ClientPlayerEntity player = client.player;
        String autograph = ((WTextField[]) widgetsList.get(1))[1].getText();
        if (!autograph.equals("")) {
            String firstLine = ((WTextField) widgetsList.get(0)).getText();

            byte counter = 1;
            if(!firstLine.equals("")) {
                player.networkHandler.sendChatCommand
                        ("ie lore set " + counter + " &#85837d" + firstLine);
                counter++;
            }
            player.networkHandler.sendChatCommand
                    ("ie lore set " + counter + " &#9b869e" + getStringByLangKey("item.writer.autograph") + ": &9&o" +
                            autograph);
            super.sign();
        } else player.sendMessage(Text.translatable("gui.writer.fillform").formatted(Formatting.RED), true);
    }
}
