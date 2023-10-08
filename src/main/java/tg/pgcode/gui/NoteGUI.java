package tg.pgcode.gui;

import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.text.BreakIterator;
import java.util.List;
import java.util.Locale;

public class NoteGUI extends AbstractWGUI {

    @Override
    protected void setInfo() {
        name = Text.translatable("gui.writer.note.label");
        allowedItems.add(Items.PAPER);

        widgetsList.add(new WTextField(Text.translatable("gui.writer.note.text")).setMaxLength(1000)); //text
        widgetsList.add(new WTextField(Text.translatable("gui.writer.note.postscript")).setMaxLength(230));
    }

    @Override
    protected void sign() {
        List<WTextField> fieldsList = widgetsList.stream()
                .map(wTextField -> (WTextField) wTextField).toList();
        sign(client, fieldsList.get(0).getText(), fieldsList.get(1).getText());
        super.sign();
    }

    private void sign(MinecraftClient client, String text, String postscript){
        ClientPlayerEntity player = client.player;
        player.networkHandler.sendChatCommand
                ("ie lore set 1 &#9b869e*" + getStringByLangKey("item.writer.note") + "*");

        int counter = 1;
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.ROOT);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {

            player.networkHandler.sendChatCommand("ie lore set " + (2 + counter) + " &7" + text.substring(start,end));
            counter++;
        }

        if (!postscript.equals(""))
            player.networkHandler.sendChatCommand("ie lore set " + (3 + counter) + " &#85837d" + postscript);
    }
}
