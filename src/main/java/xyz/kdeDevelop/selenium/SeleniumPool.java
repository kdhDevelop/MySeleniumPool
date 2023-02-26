package xyz.kdeDevelop.selenium;

import java.util.Vector;

public class SeleniumPool {
    private final int size;

    private final SeleniumData seleniumData;
    private final Vector<Selenium> seleniumList = new Vector<>();
    private final Vector<Selenium> seleniumPool = new Vector<>();
    class Init implements Runnable {
        @Override
        public void run() {
            Selenium selenium = new Selenium(seleniumData);
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

    public SeleniumPool(int size, SeleniumData seleniumData) {
        this.size = size;
        this.seleniumData = seleniumData;

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