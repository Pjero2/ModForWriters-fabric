package tg.pgcode.gui;

import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class IdentityDocumentGUI extends AbstractWGUI{
    @Override
    protected void setInfo() {
        name = Text.translatable("gui.writer.id.label");
        allowedItems.add(Items.PAPER);

        widgetsList.add(new WTextField(Text.translatable("gui.writer.id.name")).setMaxLength(48));
        WTextField[] secondLine = {
                new WTextField(Text.translatable("gui.writer.id.author")).setMaxLength(48),
                new WTextField(Text.translatable("gui.writer.id.date")).setMaxLength(24),
        };
        widgetsList.add(secondLine);
        widgetsList.add(new WTextField(Text.translatable("gui.writer.id.postscript")).setMaxLength(200));
    }

    @Override
    protected void sign() {
        WTextField firstLine = (WTextField) widgetsList.get(0);
        WTextField[] secondLine = (WTextField[]) widgetsList.get(1);
        WTextField thirdLine = (WTextField) widgetsList.get(2);

        if (sign(client, firstLine.getText(), secondLine[0].getText(), secondLine[1].getText(),thirdLine.getText()))
            super.sign();
    }

    private boolean sign(MinecraftClient client, String name, String authorName, String date, String postscript){
        ClientPlayerEntity player = client.player;

        if (!(name.equals("") || authorName.equals(""))) {
            player.networkHandler.sendChatCommand
                    ("ie lore set 1 &#9b869e*" + getStringByLangKey("item.writer.id") + "*");
            player.networkHandler.sendChatCommand
                    ("ie lore set 3 &7" + getStringByLangKey("gui.writer.id.name") + ": " + name);

            StringBuilder thirdCommandBuilder = new StringBuilder
                    ("ie lore set 4 &7" + getStringByLangKey("gui.writer.id.author") + " " + authorName);
            if(!date.equals("")) thirdCommandBuilder.append(" | " + date);
            player.networkHandler.sendChatCommand(thirdCommandBuilder.toString());
            if (!postscript.equals(""))
                player.networkHandler.sendChatCommand("ie lore set 5 &#85837d" + postscript);

            return true;
        } else {
            player.sendMessage(Text.translatable("gui.writer.fillform").formatted(Formatting.RED), true);
            return false;
        }
    }
}

