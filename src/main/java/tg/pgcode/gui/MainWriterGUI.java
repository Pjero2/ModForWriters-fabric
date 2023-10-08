package tg.pgcode.gui;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MainWriterGUI extends AbstractWGUI {

    @Override
    protected void setInfo() {
        name = Text.translatable("gui.writer.label");
        allowedItems = null;

        AbstractWGUI[] templatesList = {
                new NoteGUI(),
                new IdentityDocumentGUI(),
                new FolderCreatorGUI(),
                new AutographGUI()
        };

        for (AbstractWGUI template : templatesList) {
            if(template.getAllowedItems() == null) addButton(template);
            else {
                for (Item allowedItem : template.getAllowedItems()) {
                    if (client.player.getInventory().getMainHandStack().isOf(allowedItem)) {
                        addButton(template);
                        break;
                    }
                }
            }
        }
        widgetsList.add(new WButton(Text.translatable("gui.writer.lorereset")
                .formatted(Formatting.RED)).setOnClick(() -> {
            client.player.networkHandler.sendChatCommand("ie lore reset");
            client.setScreen(null);
        }));
    }

    private void addButton(AbstractWGUI target){
        widgetsList.add(new WButton(target.getName())
                .setOnClick(() -> client.setScreen(new WriterScreen(target))));
    }

    @Override
    protected void gui() {
        root.add(widgetListGenerator(widgetsList, 90), 10, 40, 280, 90);
    }

    @Override
    protected void onBackClick() {
        MinecraftClient.getInstance().setScreen(null);
    }
}
