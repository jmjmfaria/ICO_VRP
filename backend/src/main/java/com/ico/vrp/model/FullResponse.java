package main.java.com.ico.vrp.model;

public class FullResponse {

    private SingleResponse[] results = new SingleResponse[0];

    public FullResponse(SingleResponse[] results) {
        this.results = results;
    }

    public FullResponse() {
    }

    public SingleResponse[] getResults() {
        return results;
    }

    public void addResult(SingleResponse result) {
        SingleResponse[] holder = results;
        results = new SingleResponse[results.length + 1];

        for (int i = 0; i < holder.length; i++)
            results[i] = holder[i];

        results[results.length - 1] = result;
    }
}
