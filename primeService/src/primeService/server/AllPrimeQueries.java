package primeService.server;

import java.util.ArrayList;
import java.util.List;

import primeService.socket.PrimeServerWorker;


public class AllPrimeQueries {
    private static List<PrimeServerWorker> queries=new ArrayList<>();

    public static List<PrimeServerWorker> getQueries() {
        return queries;
    }

    public static void setQueries(List<PrimeServerWorker> queriesIn) {
        queries = queriesIn;
    }

    
}
