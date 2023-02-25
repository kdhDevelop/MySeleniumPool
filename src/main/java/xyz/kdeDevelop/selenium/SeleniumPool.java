package xyz.kdeDevelop.selenium;

import java.util.Vector;

public class SeleniumPool {
    private final int size;

    private final String URL;
    private final String WEB_DRIVER_ID;
    private final String WEB_DRIVER_PATH;
    private final String PROFILE;
    private final Vector<Selenium> seleniumList = new Vector<>();
    private final Vector<Selenium> seleniumPool = new Vector<>();
    class Init implements Runnable {
        @Override
        public void run() {
            Selenium selenium = new Selenium(URL, WEB_DRIVER_ID, WEB_DRIVER_PATH, PROFILE);
            seleniumList.add(selenium);
            seleniumPool.add(selenium);
        }
    }

    class Close implements Runnable {
        @Override
        public void run() {
            Selenium selenium = seleniumList.get(0);
            seleniumList.remove(0);
            selenium.quitDriver();
        }
    }

    public SeleniumPool(int poolSize, String URL, String WEB_DRIVER_ID, String WEB_DRIVER_PATH, String PROFILE) {
        size = poolSize;
        this.URL = URL;
        this.WEB_DRIVER_ID = WEB_DRIVER_ID;
        this.WEB_DRIVER_PATH = WEB_DRIVER_PATH;
        this.PROFILE = PROFILE;
        init();
    }

    private void init() {
        for (int T = 0; T < this.size; T++) {
            Init init = new Init();
            Thread thread = new Thread(init);
            thread.start();
        }

        seleniumPool.addAll(seleniumList);
    }

    public Selenium borrowSelenium() {
        if (seleniumPool.isEmpty())
            return null;
        else {
            Selenium selenium = seleniumPool.get(0);
            seleniumPool.remove(0);
            return selenium;
        }
    }

    public void returnSelenium(Selenium selenium) {
        selenium.url("https://google.com");
        seleniumPool.add(selenium);
    }

    public void close() {
        for (int T = 0; T < size ; T++) {
            Close close = new Close();
            Thread thread = new Thread(close);
            thread.start();
        }
    }
}