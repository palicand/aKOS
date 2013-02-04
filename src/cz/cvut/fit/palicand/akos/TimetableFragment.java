package cz.cvut.fit.palicand.akos;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fit.palicand.akos.resources.*;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 26/01/13
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public class TimetableFragment extends Fragment implements OnResourceProcessedListener {
    private Student student;
    private Semester semester;
    private Cursor cur;
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ID_NAME = 1;
    public static final String[] EVENT_PROJECTION = new String[] {
            Calendars._ID,                           // 0
            Calendars.NAME // 2
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        student = ((InfoActivity)getActivity()).getStudent();
        fetchNextSemester();
    }

    private void fetchNextSemester() {
        Resources.fetchNextSemester(new OnResourceProcessedListener() {
            @Override
            public void onResourceProcessed(KOSResource resource) {
                semester = (Semester)resource;
                fetchParallels();
            }
        });
    }

    private void fetchParallels() {
        student.fetchParallels(this, semester);
    }

    @Override
    public void onResourceProcessed(KOSResource resource) {
        Cursor cur = getCalendarCursor();
        long id = 0;
        while (cur.moveToNext()) {
            id = cur.getLong(PROJECTION_ID_INDEX);
            if(cur.getString(PROJECTION_ID_NAME).equalsIgnoreCase("Rozvrh")) {
                break;
            }
        }
        insertEvent((Parallel)resource, id);
    }



    private Cursor getCalendarCursor() {
        Cursor cur = null;
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        String selection = "((" + Calendars.ACCOUNT_TYPE + " = ?))";
        String[] selectionArgs = new String[] {"com.google"};
        cur = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        return cur;
    }

    void insertEvent(Parallel parallel, long calId) {
        ContentResolver  contentResolver = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        TimetableSlot slot = parallel.getSlot();
        long start = slot.getFirstOccurence(semester.getStartDate());
        values.put(CalendarContract.Events.DTSTART, start);
        values.put(CalendarContract.Events.DURATION, "PT1H30M");
        values.put(CalendarContract.Events.CALENDAR_ID, calId);
        values.put(CalendarContract.Events.TITLE, parallel.getCourse());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Prague");
        int interval;
        if(slot.getParity() == Parity.BOTH) {
            interval = 1;
        } else {
            interval = 2;
        }
        values.put(CalendarContract.Events.RRULE, "FREQ=WEEKLY;COUNT=13;INTERVAL="+Integer.toString(interval));
        Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

    }

}
