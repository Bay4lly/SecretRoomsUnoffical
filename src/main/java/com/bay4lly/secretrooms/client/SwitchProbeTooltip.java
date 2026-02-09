package com.bay4lly.secretrooms.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;

public class SwitchProbeTooltip extends ClientTextTooltip {

    private final SwitchProbeTooltipComponent component;

    public SwitchProbeTooltip(SwitchProbeTooltipComponent component) {
        super(component.getText());
        this.component = component;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        int xStart = x + super.getWidth(font) + 3;
        int yStart = y - 6;
        if(this.component.getItemStackIfRender() != null) {
            graphics.renderItem(this.component.getItemStackIfRender(), xStart, yStart);
            graphics.renderItemDecorations(font, this.component.getItemStackIfRender(), xStart, yStart);
        } else if(this.component.getSpriteIfRender() != null) {
            int colour = this.component.getSpriteColourIfRender();
            RenderSystem.setShaderColor(
                ((colour >> 16) & 0xFF) / 255F,
                ((colour >> 8) & 0xFF) / 255F,
                (colour & 0xFF) / 255F,
                1F);
            
            // 1.20.1 Düzeltmesi: blit yerine blit içindeki doğru parametre dizilimi
            graphics.blit(xStart, yStart, 0, 16, 16, this.component.getSpriteIfRender());
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f); // Rengi sıfırla
        }
    }

    @Override
    public int getWidth(Font font) {
        int superWidth = super.getWidth(font);
        if(this.component.getItemStackIfRender() == null && this.component.getSpriteIfRender() == null) {
            return superWidth;
        }
        return superWidth + 20;
    }
}