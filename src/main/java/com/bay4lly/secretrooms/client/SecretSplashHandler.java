package com.bay4lly.secretrooms.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.Random;

@Mod.EventBusSubscriber(modid = "secretrooms", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SecretSplashHandler {
    private static final Random RANDOM = new Random();
    private static final String[] CUSTOM_SPLASHES = {
        "Subscribe to bay4lly",
        "<3 byd3n1z",
        "Greetings to Shady1545!",
        "Hello-Hello! goodbird!",
        "Why is there no Kurdish language in Minecraft?",
        "The Best Modder McHorse!"
    };

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        // Sadece Ana Menü açıldığında çalış
        if (event.getScreen() instanceof TitleScreen titleScreen) {
            
            // %20 ihtimal kontrolü (0.20f = %20)
            if (RANDOM.nextFloat() < 0.20f) {
                String splashText = CUSTOM_SPLASHES[RANDOM.nextInt(CUSTOM_SPLASHES.length)];
                applySplash(titleScreen, new SplashRenderer(splashText));
            } else {
                // Şans tutmazsa Minecraft'ın kendi orijinal splash'ini geri yükle
                // Bu sayede ayarlar menüsünden dönünce senin yazın takılı kalmaz.
                SplashRenderer originalSplash = Minecraft.getInstance().getSplashManager().getSplash();
                if (originalSplash != null) {
                    applySplash(titleScreen, originalSplash);
                }
            }
        }
    }

    private static void applySplash(TitleScreen screen, SplashRenderer renderer) {
        try {
            boolean found = false;
            // SplashRenderer tipindeki field'ı bul ve değiştir
            for (Field field : TitleScreen.class.getDeclaredFields()) {
                if (field.getType() == SplashRenderer.class) {
                    field.setAccessible(true);
                    field.set(screen, renderer);
                    found = true;
                    break;
                }
            }
            
            // Eğer field bulunamazsa (obfuscation farkı vs.) fallback olarak isimle dene
            if (!found) {
                Field field = TitleScreen.class.getDeclaredField("splash"); // Yarn/Mojmap ismi
                field.setAccessible(true);
                field.set(screen, renderer);
            }
        } catch (Exception e) {
            // Hata durumunda oyunu çökertme, sadece logla
            System.err.println("SecretRooms: Splash yazılamadı!");
        }
    }
}