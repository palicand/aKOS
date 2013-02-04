package cz.cvut.fit.palicand.akos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cz.cvut.fit.palicand.akos.resources.*;

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
    private Spinner semesterSpinner;
    private List<Semester> semesters;
    private ListView coursesList;
    private OnResourceProcessedListener courseListener;
    private OnResourceProcessedListener semesterListener;
    private ArrayAdapter<CourseEnrollment> coursesAdapter;
    private ArrayAdapter<Semester> semesterAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courses = new ArrayList<CourseEnrollment>();
        semesters = new ArrayList<Semester>();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0) {
                    courses.add((CourseEnrollment)msg.obj);
                    coursesAdapter.notifyDataSetChanged();
                    return true;
                } else if (msg.what == 1) {
                    Semester sem = (Semester) msg.obj;
                    semesters.add(sem);
                    semesters.addAll(((InfoActivity)getActivity()).getStudent().getAllSemesterCodes(sem));
                    semesterAdapter.notifyDataSetChanged();

                    return true;
                }
                return false;
            }
        });

        courseListener = new OnResourceProcessedListener() {
            @Override
            public void onResourceProcessed(KOSResource resource) {
                Message.obtain(handler, 0, resource).sendToTarget();
            }
        };
        semesterListener = new OnResourceProcessedListener() {
            @Override
            public void onResourceProcessed(KOSResource resource) {
                Message.obtain(handler, 1, resource).sendToTarget();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        coursesList = (ListView) view.findViewById(R.id.list_courses);
        semesterSpinner = (Spinner) view.findViewById(R.id.semesters);

        setupCoursesList();
        setupSemesterSpinner();

        fetchSemester();
        return view;
    }

    private void setupSemesterSpinner() {
        semesterAdapter = new ArrayAdapter<Semester>(getActivity(), android.R.layout.simple_spinner_item, semesters);
        semesterSpinner.setAdapter(semesterAdapter);
        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Semester semester = (Semester) parent.getItemAtPosition(position);
                courses.clear();
                semesterSpinner.setSelection(position);
                fetchCourses(semester.getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupCoursesList() {
        coursesAdapter = new ArrayAdapter<CourseEnrollment>(getActivity(), android.R.layout.simple_list_item_1, courses);
        coursesList.setAdapter(coursesAdapter);
        coursesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(parent.getContext(), CourseActivity.class);
                intent.putExtra(getString(R.string.bundle_key_enrolled_course), courses.get(position));
                startActivity(intent);
            }
        });
    }

    private void fetchSemester() {
        Resources.fetchCurrentSemester(semesterListener);
    }

    private void fetchCourses(String semesterCode) {
        Student student = ((InfoActivity)getActivity()).getStudent();
        student.fetchCourses(courseListener, semesterCode);
    }
}
