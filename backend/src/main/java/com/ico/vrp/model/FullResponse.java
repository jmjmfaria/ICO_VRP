package main.java.com.ico.vrp.model;

public class FullResponse {

    private SingleResponse[] results;

    public FullResponse(SingleResponse[] results) {
        this.results = results;
    }

    public SingleResponse[] getResults() {
        return results;
    }
}
