public class BrowserHistoryModel {
    private String url;
    private String title;
    private int visitCount;
    private String timestamp;
    private String browserApplication;

    public BrowserHistoryModel(String url, String title, int visitCount, String timestamp, String browserApplication) {
        this.url = url;
        this.title = title;
        this.visitCount = visitCount;
        this.timestamp = timestamp;
        this.browserApplication = browserApplication;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBrowserApplication() {
        return browserApplication;
    }
}
