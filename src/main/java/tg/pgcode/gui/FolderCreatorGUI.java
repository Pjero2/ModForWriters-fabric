package tg.pgcode.gui;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class FolderCreatorGUI extends AbstractWGUI{
    @Override
    protected void setInfo() {
        name = Text.translatable("gui.writer.folder.label");
        allowedItems.add(Items.BUNDLE);

        for (FolderEnum folder : FolderEnum.values()){
            widgetsList.add(createFolderButton(folder.getName()));
        }
    }

    private WButton createFolderButton(Text folderName){
        WButton button = new WButton(folderName);
        button.setOnClick(() -> {
            ClientPlayNetworkHandler networkHandler = client.player.networkHandler;
            networkHandler.sendChatCommand("ie lore set 1 &#9b869e*" + folderName.getString() + "*");
            client.setScreen(null);
        });
        return button;
    }

    private enum FolderEnum {
        GRAY(),
        BROWN(),
        RED(),
        ORANGE(),
        YELLOW(),
        LIME(),
        GREEN(),
        CYAN(),
        LIGHT_BLUE(),
        BLUE(),
        PURPLE(),
        MAGENTA(),
        PINK();

        public Text getName() {
            return Text.translatable("item.writer.folder." + this.name().toLowerCase());
        }
    }
}
