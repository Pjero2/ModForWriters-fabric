package tg.pgcode.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import tg.pgcode.ModForWriters;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWGUI extends LightweightGuiDescription {
    protected Text name;
    protected List<Item> allowedItems = new ArrayList<>();

    protected List<Object> widgetsList = new ArrayList<>();
    protected WPlainPanel root;

    protected MinecraftClient client;

    public AbstractWGUI(){
        client = MinecraftClient.getInstance();

        setInfo();
        createTemplateGUI();
        gui();
    }

    protected void setInfo(){
        name = Text.literal("Default");
    }

    protected void createTemplateGUI(){
        root = new WPlainPanel();
        root.setSize(300, 140);
        setRootPanel(root);

        WButton back = new WButton(Text.literal("<"));
        back.setOnClick(this::onBackClick);
        root.add(back, 10, 11, 20, 20);

        WLabel label = new WLabel(name);
        label.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        root.add(label, 290, 17, 0, 5);
    }

    protected void onBackClick(){
        MinecraftClient.getInstance().setScreen(new WriterScreen(new MainWriterGUI()));
    }

    //root.add(text, 10, 40, 280, 20);
    //root.add(postscript, 10, 75, 280, 20);

    protected void gui(){
        root.add(widgetListGenerator(widgetsList, 70), 10, 40, 280, 70);

        WButton signButton = new WButton(Text.translatable("gui.writer.sign"));
        signButton.setOnClick(this::sign);
        root.add(signButton, 50, 110, 200, 20);
    }
    
     protected final WScrollPanel widgetListGenerator(List<Object> list, int height){
        WPlainPanel fieldsPanel = new WPlainPanel();
        WScrollPanel scrollPanel = new WScrollPanel(fieldsPanel);
        scrollPanel.setScrollingHorizontally(TriState.FALSE);

        int width = 278;
        if(getY(list.size()) + 2 > height) width = 270;

        int counter = 0;
        for(Object object : list){
            if (object instanceof WWidget) {
                WWidget widget = (WWidget) object;
                fieldsPanel.add(widget, 1, getY(counter), width, 20);
            } else if (object instanceof WWidget[]) {
                WWidget[] widgetsMassive = (WWidget[]) object;
                if (widgetsMassive.length == 2) {
                    WPlainPanel panel = new WPlainPanel();
                    panel.setSize(width, 20);
                    int indent = 5;
                    if (widgetsMassive[0] != null) panel.add(widgetsMassive[0], 0, 0, width / 2 - indent, 20);
                    if (widgetsMassive[1] != null) panel.add
                            (widgetsMassive[1], width / 2 + indent, 0, width / 2 - indent, 20);
                    fieldsPanel.add(panel, 1, getY(counter), width, 20);
                } else errorLogger();

            } else errorLogger();
            counter++;
        }
        return scrollPanel;
    }

    private void errorLogger(){
        ModForWriters.LOGGER.error("Bad argument was get in " + this.getClass().getName() +
                " widgetListGenerator(List<Object> list, int height) ");
    }

    private int getY(int a){
        return 5 + a * 30;
    }

    protected void sign(){
        client.player.networkHandler.sendChatCommand("me " + getStringByLangKey("gui.writer.me"));

        client.setScreen(null);
    }

    protected final String getStringByLangKey(String key){
        return Text.translatable(key).getString();
    }

    public final Text getName(){
        return this.name;
    }
    public final List<Item> getAllowedItems() {return this.allowedItems;}
}
