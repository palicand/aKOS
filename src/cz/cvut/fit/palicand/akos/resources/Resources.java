package cz.cvut.fit.palicand.akos.resources;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 17/01/13
 * Time: 00:50
 * To change this template use File | Settings | File Templates.
 */

import cz.cvut.fit.palicand.akos.resources.fetchers.ResourceFetcher;
import cz.cvut.fit.palicand.akos.resources.fetchers.SemesterFetcher;
import cz.cvut.fit.palicand.akos.resources.fetchers.StudentFetcher;

/**
 * Serves to fetch remote root resources
 */

public class Resources {
    /**
     * Gets the student resource for the particular student
     * @param username username of the wanted student
     * @param listener once the resource is fetched, this listener will be called
     */

    private static void start(ResourceFetcher fetcher) {
        new Thread(fetcher).start();
    }

    public static void fetchStudent(String username, OnResourceProcessedListener listener) {
        start(new StudentFetcher(username, listener));
    }

    public static void fetchCurrentSemester(OnResourceProcessedListener listener) {
        start(new SemesterFetcher(listener));
    }

    public static void fetchNextSemester(OnResourceProcessedListener listener) {
        start(new SemesterFetcher(listener, "next"));
    }

}
