package me.pixeldots.pixelscharactermodels.GUI;

import org.apache.commons.lang3.math.NumberUtils;
import org.spongepowered.asm.mixin.Overwrite;

import me.pixeldots.pixelscharactermodels.PixelsCharacterModels;
import me.pixeldots.pixelscharactermodels.GUI.Handlers.GuiHandler;
import me.pixeldots.pixelscharactermodels.utils.MapVec3;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class AnimationGui extends GuiHandler {

	public ButtonWidget Presets;
	public ButtonWidget Editor;
	public ButtonWidget Animation;
	public ButtonWidget Frames;
	
	public ButtonWidget Parts;
	
	public ButtonWidget SelectedAnimation;
	
	public TextFieldWidget RotXField;
	public TextFieldWidget RotYField;
	public TextFieldWidget RotZField;
	public ButtonWidget setRot;
	public ButtonWidget removeRot;
	
	public TextFieldWidget PlayerTransformXField;
	public TextFieldWidget PlayerTransformYField;
	public TextFieldWidget PlayerTransformZField;
	public ButtonWidget setPlayerTransform;
	public ButtonWidget removePlayerTransform;
	
	public String selectedAnimation = "";
	
	public AnimationGui() {
		super("Animation");
	}
	
	@Override
	public void init() {
		super.init();
		Presets = addButton(new ButtonWidget(5,5,50,20, Text.of(PixelsCharacterModels.TranslatedText.Presets), (button) -> {
			client.openScreen(new PresetsGui());
		}));
		Editor = addButton(new ButtonWidget(60,5,50,20, Text.of(PixelsCharacterModels.TranslatedText.Editor), (button) -> {
			client.openScreen(new EditorGui());
		}));
		Animation = addButton(new ButtonWidget(5,30,50,20, Text.of(PixelsCharacterModels.TranslatedText.Animation), (button) -> {
			client.openScreen(new AnimationGui());
		}));
		Frames = addButton(new ButtonWidget(60,30,50,20, Text.of(PixelsCharacterModels.TranslatedText.Frames), (button) -> {
			client.openScreen(new FramesGui());
		}));
		Animation.active = false;
		
		String selectedPart = PixelsCharacterModels.GuiData.SelectedPart;
		selectedAnimation = PixelsCharacterModels.GuiData.SelectedAnimation;
		Parts = addButton(new ButtonWidget(120, 30, 50, 20, Text.of(selectedPart == "" ? PixelsCharacterModels.TranslatedText.Parts : selectedPart), (button) -> {
			client.openScreen(new PartsGui(this));
		}));
		SelectedAnimation = addButton(new ButtonWidget(180, 30, 50, 20, Text.of(selectedAnimation == "" ? PixelsCharacterModels.TranslatedText.Animations : selectedAnimation), (button) -> {
			client.openScreen(new AnimationsGui(this));
		}));
		if (selectedAnimation != "") {
			if (selectedPart != "") {
				RotXField = addTextField(new TextFieldWidget(textRendererGUI, 225, 100, 100, 25, Text.of("RotX")));
				RotYField = addTextField(new TextFieldWidget(textRendererGUI, 225, 130, 100, 25, Text.of("RotY")));
				RotZField = addTextField(new TextFieldWidget(textRendererGUI, 225, 160, 100, 25, Text.of("RotZ")));
				setRot = addButton(new ButtonWidget(225, 190, 100, 20, Text.of("Set"), (button) -> {
					setRotation();
				}));
				removeRot = addButton(new ButtonWidget(225, 220, 100, 20, Text.of("Remove"), (button) -> {
					if (PixelsCharacterModels.animations.get(selectedAnimation).LimbRotations.containsKey(PixelsCharacterModels.GuiData.SelectedPartModel)) {
						PixelsCharacterModels.animations.get(selectedAnimation).LimbRotations.remove(PixelsCharacterModels.GuiData.SelectedPartModel);
					}
				}));
				
				if (PixelsCharacterModels.dataPackets.containsKey(PixelsCharacterModels.GuiData.SelectedPartModel)) {
					RotXField.setText(String.valueOf(PixelsCharacterModels.dataPackets.get(PixelsCharacterModels.GuiData.SelectedPartModel).rot.X));
					RotYField.setText(String.valueOf(PixelsCharacterModels.dataPackets.get(PixelsCharacterModels.GuiData.SelectedPartModel).rot.Y));
					RotZField.setText(String.valueOf(PixelsCharacterModels.dataPackets.get(PixelsCharacterModels.GuiData.SelectedPartModel).rot.Z));
				}
			}
			
			PlayerTransformXField = addTextField(new TextFieldWidget(textRendererGUI, 335, 100, 100, 25, Text.of("PlayerTransformX")));
			PlayerTransformYField = addTextField(new TextFieldWidget(textRendererGUI, 335, 130, 100, 25, Text.of("PlayerTransformY")));
			PlayerTransformZField = addTextField(new TextFieldWidget(textRendererGUI, 335, 160, 100, 25, Text.of("PlayerTransformZ")));
			setPlayerTransform = addButton(new ButtonWidget(335, 190, 100, 20, Text.of("Set"), (button) -> {
				setTransform();
			}));
			removePlayerTransform = addButton(new ButtonWidget(335, 220, 100, 20, Text.of("Remove"), (button) -> {
				PixelsCharacterModels.animations.get(selectedAnimation).playerTransform = new MapVec3(0,0,0);
			}));
		}
		
		/*if (PixelsCharacterModels.dataPackets.containsKey(PixelsCharacterModels.GuiData.SelectedPartModel)) {
			RotXField.setText(String.valueOf(PixelsCharacterModels.dataPackets.get(PixelsCharacterModels.GuiData.SelectedPartModel).rot.X));
			RotYField.setText(String.valueOf(PixelsCharacterModels.dataPackets.get(PixelsCharacterModels.GuiData.SelectedPartModel).rot.Y));
			RotZField.setText(String.valueOf(PixelsCharacterModels.dataPackets.get(PixelsCharacterModels.GuiData.SelectedPartModel).rot.Z));
		}*/
	}
	
	@Override
	public void tick() {
		super.tick();
	}
	
	public void setRotation() {
		if (PixelsCharacterModels.dataPackets.get(PixelsCharacterModels.GuiData.SelectedPartModel) == null) return;
		if (!PixelsCharacterModels.animations.get(selectedAnimation).LimbRotations.containsKey(PixelsCharacterModels.GuiData.SelectedPartModel)) {
			PixelsCharacterModels.animations.get(selectedAnimation).LimbRotations.put(PixelsCharacterModels.GuiData.SelectedPartModel, new MapVec3(0,0,0));
		}
		if (isNumeric(RotXField.getText())) {
			PixelsCharacterModels.animations.get(selectedAnimation).LimbRotations.get(PixelsCharacterModels.GuiData.SelectedPartModel).X = Float.parseFloat(RotXField.getText());
		}
		if (isNumeric(RotYField.getText())) {
			PixelsCharacterModels.animations.get(selectedAnimation).LimbRotations.get(PixelsCharacterModels.GuiData.SelectedPartModel).Y = Float.parseFloat(RotYField.getText());
		}
		if (isNumeric(RotZField.getText())) {
			PixelsCharacterModels.animations.get(selectedAnimation).LimbRotations.get(PixelsCharacterModels.GuiData.SelectedPartModel).Z = Float.parseFloat(RotZField.getText());
		}
		PixelsCharacterModels.dataPackets.get(PixelsCharacterModels.GuiData.SelectedPartModel).useRotation = true;
	}
	
	public void setTransform() {
		String selectedAnimation = PixelsCharacterModels.GuiData.SelectedAnimation;
		if (isNumeric(PlayerTransformXField.getText())) {
			PixelsCharacterModels.animations.get(selectedAnimation).playerTransform.X = Float.parseFloat(PlayerTransformXField.getText());
		}
		if (isNumeric(PlayerTransformYField.getText())) {
			PixelsCharacterModels.animations.get(selectedAnimation).playerTransform.Y = Float.parseFloat(PlayerTransformYField.getText());
		}
		if (isNumeric(PlayerTransformZField.getText())) {
			PixelsCharacterModels.animations.get(selectedAnimation).playerTransform.Z = Float.parseFloat(PlayerTransformZField.getText());
		}
	}
	
	public boolean isNumeric(String s) {
		return NumberUtils.isCreatable(s);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if (selectedAnimation == "") drawString(matrices, "select an animation to show options", 240, 30, 16777215);
		drawString(matrices, "Limb Rotation", 225, 85, 16777215);
		drawString(matrices, "Player Transform", 335, 85, 16777215);
		super.render(matrices, mouseX, mouseY, delta);
	}
	
}
