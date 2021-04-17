package com.ngstudios.game.desktop;

import com.ngstudios.game.SplashWorker;

import java.awt.*;

public class DesktopSplashWorker implements SplashWorker {

    public void closeSplashScreen() {
        SplashScreen splashScreen = SplashScreen.getSplashScreen();
        if(splashScreen != null){
            splashScreen.close();
        }
    }
}
