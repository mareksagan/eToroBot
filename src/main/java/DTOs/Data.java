package DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
    private String symbol;
    private int totalRecords;
    private int offset;
    private int limit;
    private NLS headers;
    private NLS[] rows;

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("totalRecords")
    public int getTotalRecords() {
        return totalRecords;
    }

    @JsonProperty("totalRecords")
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    @JsonProperty("offset")
    public int getOffset() {
        return offset;
    }

    @JsonProperty("offset")
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @JsonProperty("limit")
    public int getLimit() {
        return limit;
    }

    @JsonProperty("limit")
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @JsonProperty("headers")
    public NLS getHeaders() {
        return headers;
    }

    @JsonProperty("headers")
    public void setHeaders(NLS headers) {
        this.headers = headers;
    }

    @JsonProperty("rows")
    public NLS[] getRows() {
        return rows;
    }

    @JsonProperty("rows")
    public void setRows(NLS[] rows) {
        this.rows = rows;
    }
}
