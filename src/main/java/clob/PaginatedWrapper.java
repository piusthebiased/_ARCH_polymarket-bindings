package clob;

public class PaginatedWrapper {
    public int limit;
    public int count;
    public String next_cursor;

    public PaginatedWrapper(int limit, int count, String next_cursor) {
        this.limit = limit;
        this.count = count;
        this.next_cursor = next_cursor;
    }
}