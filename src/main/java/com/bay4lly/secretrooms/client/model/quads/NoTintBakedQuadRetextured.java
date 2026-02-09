package com.bay4lly.secretrooms.client.model.quads;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import java.util.Arrays;

/**
 * Ali, bu sinif artik atlasla ugrasmiyor. 
 * Sadece orijinal quad'i alip tint'ini kapatiyor (-1) ve cam dokusunu tasiyor.
 */
public class NoTintBakedQuadRetextured extends BakedQuad {
    private final TextureAtlasSprite texture;

    public NoTintBakedQuadRetextured(BakedQuad quad, TextureAtlasSprite texture) {
        // -1 tintIndex vererek bloktaki (toprak/cim vb.) renklenmeyi tamamen kapatiyoruz.
        super(Arrays.copyOf(quad.getVertices(), quad.getVertices().length), 
              -1, 
              FaceBakery.calculateFacing(quad.getVertices()), 
              texture, 
              quad.isShade());
        this.texture = texture;
    }

    @Override
    public TextureAtlasSprite getSprite() {
        return this.texture;
    }
}