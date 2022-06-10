package me.pixeldots.pixelscharactermodels.gui;

import me.pixeldots.pixelscharactermodels.other.Node;
import me.pixeldots.pixelscharactermodels.other.Node.NodeType;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

public class NodeSelectGui extends GuiHandler {

    public LivingEntity entity;
    public float entityViewScale = 75;

    public NodeSelectGui(LivingEntity _entity, float _entityViewScale) {
        super("Node Selector");
        entity = _entity;
        entityViewScale = _entityViewScale;
    }

    @Override
    public void init() {
        super.init();

        addButton(new ButtonWidget(5, 5, 100, 10, Text.of("Cancel"), (btn) -> {
            this.client.setScreen(new EditorGui(entity, entityViewScale));
        }));

        int row = 1, col = 0;
        for (NodeType t : NodeType.values()) {
            if (10+(row*15) > this.height) {
                col++;
                row = 1;
            }

            addButton(new ButtonWidget(5+(col*105), 10+(row*15), 100, 10, Text.of(t.name().toLowerCase()), (btn) -> {
                Node node = new Node(t);
                node.changed = true;
                EditorGui.nodes.add(node);
                this.client.setScreen(new EditorGui(entity, entityViewScale));
            }));

            row++;
        }
    }

}
