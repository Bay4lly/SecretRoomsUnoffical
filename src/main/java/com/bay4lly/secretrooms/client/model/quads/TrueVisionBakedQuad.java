package com.bay4lly.secretrooms.client.model.quads;

import com.bay4lly.secretrooms.SecretRooms6;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;

import java.util.Arrays;

import static java.lang.Float.floatToRawIntBits;
import static java.lang.Float.intBitsToFloat;

public class TrueVisionBakedQuad {
    private static final ResourceLocation OVERLAY_LOCATION = ResourceLocation.fromNamespaceAndPath(SecretRooms6.MODID, "block/overlay");
    private static TextureAtlasSprite overlaySprite;

    public static void onTextureStitch(TextureAtlasStitchedEvent event) {
        if (InventoryMenu.BLOCK_ATLAS.equals(event.getAtlas().location())) {
            overlaySprite = event.getAtlas().getSprite(OVERLAY_LOCATION);
        }
    }

    public static BakedQuad generateQuad(BakedQuad quad) {
        if (overlaySprite == null) return quad; 
        
        int[] data = Arrays.copyOf(quad.getVertices(), quad.getVertices().length);
        
        // 1.21'de vertex basina 8 integer duser (32 byte / 4).
        final int INT_PER_VERTEX = 8;

        for (int i = 0; i < 4; i++) {
            int j = INT_PER_VERTEX * i; // Burasi 0, 8, 16, 24 olacak. 32 olmayacak!

            float x = intBitsToFloat(data[j]) + 0.001F * quad.getDirection().getStepX();
            float y = intBitsToFloat(data[j + 1]) + 0.001F * quad.getDirection().getStepY();
            float z = intBitsToFloat(data[j + 2]) + 0.001F * quad.getDirection().getStepZ();

            data[j] = floatToRawIntBits(x);
            data[j + 1] = floatToRawIntBits(y);
            data[j + 2] = floatToRawIntBits(z);

            float ui, vi;
            switch (quad.getDirection().getAxis()) {
                case X -> { ui = z; vi = 1 - y; }
                case Z -> { ui = x; vi = 1 - y; }
                default -> { ui = x; vi = z; }
            }

            // UV ve Isik verilerini yeni format indekslerine gore yaziyoruz
            data[j + 4] = floatToRawIntBits(overlaySprite.getU(Math.abs(ui % 1.0F) * 16F));
            data[j + 5] = floatToRawIntBits(overlaySprite.getV(Math.abs(vi % 1.0F) * 16F));
            data[j + 6] = (240 << 16) | 240; // Full light
        }
        return new BakedQuad(data, -1, quad.getDirection(), overlaySprite, quad.isShade());
    }
}