package com.bay4lly.secretrooms.client.model.quads;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import static java.lang.Float.floatToRawIntBits;
import static java.lang.Float.intBitsToFloat;

public class QuadSpriteMapper {

    public static BakedQuad remapSpriteTiled(BakedQuad quad, TextureAtlasSprite sprite) {
        if (sprite == null) return quad;

        int[] data = java.util.Arrays.copyOf(quad.getVertices(), quad.getVertices().length);
        final int INT_PER_VERTEX = 8;

        for (int i = 0; i < 4; i++) {
            int j = INT_PER_VERTEX * i;

            float x = intBitsToFloat(data[j]);
            float y = intBitsToFloat(data[j + 1]);
            float z = intBitsToFloat(data[j + 2]);

            float ui, vi;
            switch (quad.getDirection().getAxis()) {
                case X -> { ui = z; vi = 1 - y; }
                case Z -> { ui = x; vi = 1 - y; }
                default -> { ui = x; vi = z; }
            }

            data[j + 4] = floatToRawIntBits(sprite.getU(Math.abs(ui % 1.0F) * 16F));
            data[j + 5] = floatToRawIntBits(sprite.getV(Math.abs(vi % 1.0F) * 16F));
            data[j + 6] = (240 << 16) | 240;
        }

        // preserve original tint index so biome tinting still applies
        return new BakedQuad(data, quad.getTintIndex(), quad.getDirection(), sprite, quad.isShade());
    }

    public static BakedQuad remapUsingSourceUV(BakedQuad target, BakedQuad source) {
        if (source == null) return target;

        int[] t = java.util.Arrays.copyOf(target.getVertices(), target.getVertices().length);
        int[] s = source.getVertices();
        final int INT_PER_VERTEX = 8;

        for (int i = 0; i < 4; i++) {
            int tj = INT_PER_VERTEX * i;
            int sj = INT_PER_VERTEX * i;

            // copy UVs and light from source quad
            t[tj + 4] = s[sj + 4];
            t[tj + 5] = s[sj + 5];
            t[tj + 6] = s[sj + 6];
        }

        TextureAtlasSprite sprite = source.getSprite();
        // preserve target tintIndex so registered BlockColor handlers (which lookup mirror state) still apply
        return new BakedQuad(t, target.getTintIndex(), target.getDirection(), sprite, target.isShade());
    }

    public static BakedQuad remapUsingTargetSprite(BakedQuad target, TextureAtlasSprite mirrorSprite) {
        if (mirrorSprite == null) return target;

        int[] data = java.util.Arrays.copyOf(target.getVertices(), target.getVertices().length);
        final int INT_PER_VERTEX = 8;

        TextureAtlasSprite targetSprite = target.getSprite();
        float tu0 = targetSprite.getU0();
        float tu1 = targetSprite.getU1();
        float tv0 = targetSprite.getV0();
        float tv1 = targetSprite.getV1();
        float du = tu1 - tu0;
        float dv = tv1 - tv0;

        for (int i = 0; i < 4; i++) {
            int j = INT_PER_VERTEX * i;
            float origU = intBitsToFloat(data[j + 4]);
            float origV = intBitsToFloat(data[j + 5]);

            float uNorm = du != 0 ? (origU - tu0) / du : 0f;
            float vNorm = dv != 0 ? (origV - tv0) / dv : 0f;

            data[j + 4] = floatToRawIntBits(mirrorSprite.getU(uNorm * 16F));
            data[j + 5] = floatToRawIntBits(mirrorSprite.getV(vNorm * 16F));
            // keep original light
        }

        return new BakedQuad(data, target.getTintIndex(), target.getDirection(), mirrorSprite, target.isShade());
    }
}
