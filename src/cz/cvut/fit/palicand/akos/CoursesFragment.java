package cz.cvut.fit.palicand.akos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import cz.cvut.fit.palicand.akos.resources.CourseEnrollment;
import cz.cvut.fit.palicand.akos.resources.KOSResource;
import cz.cvut.fit.palicand.akos.resources.OnResourceProcessedListener;
import cz.cvut.fit.palicand.akos.resources.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 12/01/13
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
public class CoursesFragment extends Fragment {

    private List<CourseEnrollment> courses;
    private Handler handler;
    private Spinner semesters;
    private ListView coursesList;
    OnResourceProcessedListener listener;
    private ArrayAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courses = new ArrayList<CourseEnrollment>();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0) {
                    courses.add((CourseEnrollment)msg.obj);
                    adapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        listener = new OnResourceProcessedListener() {
            @Override
            public void onResourceProcessed(KOSResource resource) {
                Message.obtain(handler, 0, resource).sendToTarget();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        coursesList = (ListView) view.findViewById(R.id.list_courses);
        semesters = (Spinner) view.findViewById(R.id.semesters);

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, courses);
        coursesList.setAdapter(adapter);
        coursesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(parent.getContext(), CourseActivity.class);
                intent.putExtra(getString(R.string.bundle_key_enrolled_course), courses.get(position));
                startActivity(intent);
            }
        });

        fetchCourses();
        return view;
    }

    private void fetchCourses() {
        Resources.fetchStudentCourses(((InfoActivity)getActivity()).getUsername(), listener);
    }
}
