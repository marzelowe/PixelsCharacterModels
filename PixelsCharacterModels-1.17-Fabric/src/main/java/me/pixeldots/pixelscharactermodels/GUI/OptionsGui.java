package me.pixeldots.pixelscharactermodels.GUI;

import org.apache.commons.lang3.math.NumberUtils;

import me.pixeldots.pixelscharactermodels.PixelsCharacterModels;
import me.pixeldots.pixelscharactermodels.GUI.Handlers.GuiHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class OptionsGui extends GuiHandler {
	
	public ButtonWidget Presets;
	public ButtonWidget Editor;
	public ButtonWidget Animation;
	public ButtonWidget Frames;
	
	public TextFieldWidget AnimOneID;
	public TextFieldWidget AnimTwoID;
	public TextFieldWidget AnimThreeID;
	public TextFieldWidget AnimFourID;
	public TextFieldWidget AnimFiveID;
	public ButtonWidget showNameTags;
	public ButtonWidget sendUpdateMessages;
	
	public OptionsGui() {
		super("Options");
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
		
		AnimOneID = addTextField(new TextFieldWidget(textRendererGUI, 35,80,50,20, Text.of("AnimOneID")));
		AnimTwoID = addTextField(new TextFieldWidget(textRendererGUI, 35,100,50,20, Text.of("AnimTwoID")));
		AnimThreeID = addTextField(new TextFieldWidget(textRendererGUI, 35,120,50,20, Text.of("AnimThreeID")));
		AnimFourID = addTextField(new TextFieldWidget(textRendererGUI, 35,140,50,20, Text.of("AnimFourID")));
		AnimFiveID = addTextField(new TextFieldWidget(textRendererGUI, 35,160,50,20, Text.of("AnimFiveID")));
		
		showNameTags = addButton(new ButtonWidget(85, 185, 50, 20, Text.of(PixelsCharacterModels.localData.showNameTags ? "true" : "false"), (button) -> {
			PixelsCharacterModels.localData.showNameTags = !PixelsCharacterModels.localData.showNameTags;
			button.setMessage(Text.of(PixelsCharacterModels.localData.showNameTags ? "true" : "false"));
		}));
		sendUpdateMessages = addButton(new ButtonWidget(85, 210, 50, 20, Text.of(PixelsCharacterModels.localData.showUpdateMessage ? "true" : "false"), (button) -> {
			PixelsCharacterModels.localData.showUpdateMessage = !PixelsCharacterModels.localData.showUpdateMessage;
			button.setMessage(Text.of(PixelsCharacterModels.localData.showUpdateMessage ? "true" : "false"));
		}));
		
		AnimOneID.setText(PixelsCharacterModels.localData.AnimationIDOne);
		AnimTwoID.setText(PixelsCharacterModels.localData.AnimationIDTwo);
		AnimThreeID.setText(PixelsCharacterModels.localData.AnimationIDThree);
		AnimFourID.setText(PixelsCharacterModels.localData.AnimationIDFour);
		AnimFiveID.setText(PixelsCharacterModels.localData.AnimationIDFive);
	}
	
	@Override
	public void tick() {
		PixelsCharacterModels.localData.AnimationIDOne = AnimOneID.getText();
		PixelsCharacterModels.localData.AnimationIDTwo = AnimTwoID.getText();
		PixelsCharacterModels.localData.AnimationIDThree = AnimThreeID.getText();
		PixelsCharacterModels.localData.AnimationIDFour = AnimFourID.getText();
		PixelsCharacterModels.localData.AnimationIDFive = AnimFiveID.getText();
		super.tick();
	}
	
	public boolean isNumeric(String s) {
		return NumberUtils.isCreatable(s);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		drawString(matrices, "Animation IDs:", 5, 60, 16777215);
		
		drawString(matrices, "One", 5, 80, 16777215);
		drawString(matrices, "Two", 5, 100, 16777215);
		drawString(matrices, "Three", 5, 120, 16777215);
		drawString(matrices, "Four", 5, 140, 16777215);
		drawString(matrices, "Five", 5, 160, 16777215);
		
		drawString(matrices, "show NameTags", 5, 185);
		drawString(matrices, "show Messages", 5, 210);
		super.render(matrices, mouseX, mouseY, delta);
	}
	
}
