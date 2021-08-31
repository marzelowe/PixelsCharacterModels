package me.pixeldots.pixelscharactermodels.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import me.pixeldots.pixelscharactermodels.PixelsCharacterModels;
import me.pixeldots.pixelscharactermodels.Animation.PCMFrames;
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
	
	public TextFieldWidget FramesName;
	public ButtonWidget FramesCreate;
	public ButtonWidget FramesRemove;

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
		
		Animations = addButton(new ButtonWidget(120, 5, 50, 20, Text.of(PixelsCharacterModels.TranslatedText.Animations), (button) -> {
			PixelsCharacterModels.client.openScreen(new FramesAnimationGUI());
		}));
		LoopFrames = addButton(new ButtonWidget(175, 5, 50, 20, Text.of("true"), (button) -> {
			if (button.getMessage().asString() == "true") { 
				button.setMessage(Text.of("false"));
				PixelsCharacterModels.PCMClient.currentStoredFrames.Loop = false;
			}
			else {
				button.setMessage(Text.of("true"));
				PixelsCharacterModels.PCMClient.currentStoredFrames.Loop = true;
			}
			PixelsCharacterModels.PCMClient.writeFrames(PixelsCharacterModels.GuiData.SelectedFrames, PixelsCharacterModels.GuiData.entity, PixelsCharacterModels.GuiData.model);
		}));
		
		TimePerFrame = addTextField(new TextFieldWidget(textRendererGUI, 230, 5, 50, 20, Text.of("TimePerFrame")));
		if (PixelsCharacterModels.GuiData.SelectedFramesID == -1) { 
			Animations.active = false;
			LoopFrames.active = false;
			TimePerFrame.active = false;
		} else {
			LoopFrames.setMessage(Text.of(String.valueOf(PixelsCharacterModels.PCMClient.currentStoredFrames.Loop).toLowerCase()));
			TimePerFrame.setText(String.valueOf(PixelsCharacterModels.PCMClient.currentStoredFrames.TimePerFrame));
		}
		
		FramesName = addTextField(new TextFieldWidget(textRendererGUI, 5, 100, 50, 20, Text.of("FramesName")));
		FramesCreate = addButton(new ButtonWidget(5, 125, 50, 20, Text.of(PixelsCharacterModels.TranslatedText.Create), (button) -> {
			if (FramesName.getText().replace(" ", "") == "") PixelsCharacterModels.client.player.sendMessage(Text.of(PixelsCharacterModels.TranslatedText.setFramesName), false);
			else {
				PixelsCharacterModels.PCMClient.currentStoredFrames = new PCMFrames(FramesName.getText());
				PixelsCharacterModels.PCMClient.writeFrames(FramesName.getText(), PixelsCharacterModels.GuiData.entity, PixelsCharacterModels.GuiData.model);
				PixelsCharacterModels.GuiData.SelectedFrames = FramesName.getText();
				PixelsCharacterModels.client.openScreen(new FramesGui());
			}
		}));
		FramesRemove = addButton(new ButtonWidget(5, 150, 50, 20, Text.of(PixelsCharacterModels.TranslatedText.Remove), (button) -> {
			if (PixelsCharacterModels.GuiData.SelectedFramesID != -1) {
				PixelsCharacterModels.PCMClient.DeleteFrames(PixelsCharacterModels.GuiData.SelectedFramesID);
				PixelsCharacterModels.GuiData.SelectedFramesID = -1;
				PixelsCharacterModels.GuiData.SelectedFrames = "";
				PixelsCharacterModels.client.openScreen(new FramesGui());
			}
		}));
		
		int Col = 1;
		int Row = 0;
		File[] files = PixelsCharacterModels.FramesData.getFrames();
		for (int i = 0; i < files.length; i++) {
			int num = i;
			String key = (String) files[i].getName().replace(".json", "");
			FramesList.add(addButton(new ButtonWidget(200+(Row*55), 50+(Col*25), 50, 20, Text.of(key), (button) -> {
				button.active = false;
				SelectFrames(key, num);
			})));
			Col++;
			if (Col > maxCol) {
				Row++;
				Col = 0;
			}
			if (Row > maxRow) break;
		}
		
		for (int i = 0; i < FramesList.size(); i++) {
			if (FramesList.get(i).getMessage().asString().equalsIgnoreCase(PixelsCharacterModels.GuiData.SelectedFrames))
			{FramesList.get(i).active = false; break;}
		}
	}
	
	public void SelectFrames(String name, int id) {
		PixelsCharacterModels.GuiData.SelectedFramesID = id;
		PixelsCharacterModels.GuiData.SelectedFrames = name;
		PixelsCharacterModels.PCMClient.LoadFrames(id, PixelsCharacterModels.GuiData.entity, PixelsCharacterModels.GuiData.model);
		PixelsCharacterModels.client.openScreen(new FramesGui());
	}
	
	@Override
	public void tick() {
		if (isNumeric(TimePerFrame.getText())) {
			PixelsCharacterModels.PCMClient.currentStoredFrames.TimePerFrame = Float.parseFloat(TimePerFrame.getText());
			PixelsCharacterModels.PCMClient.writeFrames(PixelsCharacterModels.GuiData.SelectedFrames, PixelsCharacterModels.GuiData.entity, PixelsCharacterModels.GuiData.model);
		}
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
