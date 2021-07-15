package me.pixeldots.pixelscharactermodels.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import me.pixeldots.pixelscharactermodels.PixelsCharacterModels;
import me.pixeldots.pixelscharactermodels.GUI.Handlers.GuiHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FramesGui extends GuiHandler {
	
	public ButtonWidget Presets;
	public ButtonWidget Editor;
	public ButtonWidget Animation;
	public ButtonWidget Frames;
	
	public ButtonWidget Animations;
	public ButtonWidget LoopFrames;
	public TextFieldWidget TimePerFrame;

	public int maxCol = 10;
	public int maxRow = 5;
	
	public List<ButtonWidget> FramesList = new ArrayList<ButtonWidget>();
	
	public FramesGui() {
		super("Frames");
	}
	
	@Override
	public void init() {
		super.init();
		Presets = addButton(new ButtonWidget(5,5,50,20, Text.of(PixelsCharacterModels.TranslatedText.Presets), (button) -> {
			MinecraftClient.getInstance().openScreen(new PresetsGui());
		}));
		Editor = addButton(new ButtonWidget(60,5,50,20, Text.of(PixelsCharacterModels.TranslatedText.Editor), (button) -> {
			MinecraftClient.getInstance().openScreen(new EditorGui());
		}));
		Animation = addButton(new ButtonWidget(5,30,50,20, Text.of(PixelsCharacterModels.TranslatedText.Animation), (button) -> {
			MinecraftClient.getInstance().openScreen(new AnimationGui());
		}));
		Frames = addButton(new ButtonWidget(60,30,50,20, Text.of(PixelsCharacterModels.TranslatedText.Frames), (button) -> {
			MinecraftClient.getInstance().openScreen(new FramesGui());
		}));
		Frames.active = false;
		
		Animations = addButton(new ButtonWidget(100, 5, 50, 20, Text.of(PixelsCharacterModels.TranslatedText.Animations), (button) -> {
			
		}));
		LoopFrames = addButton(new ButtonWidget(155, 5, 50, 20, Text.of("true"), (button) -> {
			
		}));
		
		TimePerFrame = addTextField(new TextFieldWidget(textRendererGUI, 210, 5, 50, 20, Text.of("TimePerFrame")));
		if (PixelsCharacterModels.GuiData.SelectedFramesID == -1) { 
			Animations.active = false;
			LoopFrames.active = false;
			TimePerFrame.active = false;
		}
		
		int Col = 1;
		int Row = 1;
		File[] files = PixelsCharacterModels.FramesData.getFrames();
		for (int i = 0; i < files.length; i++) {
			String key = (String) files[i].getName().replace(".json", "");
			FramesList.add(addButton(new ButtonWidget(200+(Row*55), 50+(Col*25), 50, 20, Text.of(key), (button) -> {
				button.active = false;
				SelectFrames(key);
			})));
			Col++;
			if (Col > maxCol) {
				Row++;
				Col = 0;
			}
			if (Row > maxRow) break;
		}
		
		for (int i = 0; i < FramesList.size(); i++) {
			if (FramesList.get(i).getMessage().asString() == PixelsCharacterModels.GuiData.SelectedFrames)
			{FramesList.get(i).active = false; break;}
		}
	}
	
	public void SelectFrames(String name) {
		
	}
	
	@Override
	public void tick() {
		super.tick();
	}
	
	public boolean isNumeric(String s) {
		return NumberUtils.isCreatable(s);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
	}
	
}
