package primeService.client;

import java.util.ArrayList;
import java.util.List;

public class Client{
    private String name;
    private List<Integer> queries=new ArrayList<>();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Integer> getQueries() {
        return queries;
    }
    public void setQueries(List<Integer> queries) {
        this.queries = queries;
    }
    @Override
    public String toString() {
        return "Client [name=" + name + ", queries=" + queries + "]";
    }
    
}