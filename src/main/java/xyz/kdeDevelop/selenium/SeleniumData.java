package xyz.kdeDevelop.selenium;

public class SeleniumData {

    private String url;
    private String headless;
    private String driverDirectory;
    private String profileDirectory;
    private String proxyUrl;

    private SeleniumData(Builder builder) {
        url = builder.url;
        driverDirectory = builder.driverDirectory;
        headless = builder.headless;
        profileDirectory = builder.profileDirectory;
        proxyUrl = builder.proxyUrl;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDriverDirectory(String driverDirectory) {
        this.driverDirectory = driverDirectory;
    }

    public String getDriverDirectory() {
        return driverDirectory;
    }

    public void setHeadless(String headless) {
        this.headless = headless;
    }

    public String getHeadless() {
        return headless;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProfileDirectory(String profileDirectory) {
        this.profileDirectory = profileDirectory;
    }

    public String getProfileDirectory() {
        return profileDirectory;
    }

    public static class Builder {

        private String url;
        private String headless;
        private String driverDirectory;
        private String profileDirectory;
        private String proxyUrl;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder headless(String headless) {
            this.headless = headless;
            return this;
        }

        public Builder driverDirectory(String driverDirectory) {
            this.driverDirectory = driverDirectory;
            return this;
        }

        public Builder profileDirectory(String profileDirectory) {
            this.profileDirectory = profileDirectory;
            return this;
        }

        public Builder proxyData(String proxyData) {
            this.proxyUrl = proxyData;
            return this;
        }

        public SeleniumData build() {
            return new SeleniumData(this);
        }
    }
}