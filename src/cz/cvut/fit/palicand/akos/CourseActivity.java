package cz.cvut.fit.palicand.akos;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import cz.cvut.fit.palicand.akos.resources.*;
import cz.cvut.fit.palicand.akos.views.PairedTextView;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 23/01/13
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class CourseActivity extends Activity implements OnResourceProcessedListener {
    private CourseEnrollment courseEnrollment;
    private Course course;

    private TextView courseName;
    private TextView finished;
    private PairedTextView description;
    private PairedTextView completion;
    private PairedTextView credits;

    private Handler handler;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        setupViews();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == 0) {
                    course = (Course)msg.obj;
                    updateView();
                }
                return false;
            }
        });


        if(savedInstanceState == null) {
            Intent intent = getIntent();
            courseEnrollment = (CourseEnrollment)intent.getSerializableExtra(getString(R.string.bundle_key_enrolled_course));
            acquireCourse();
        } else {
            courseEnrollment = (CourseEnrollment)savedInstanceState.getSerializable(getString(R.string.bundle_key_enrolled_course));
            course = (Course)savedInstanceState.getSerializable(getString(R.string.bundle_key_course));
            updateView();
        }
    }

    private void acquireCourse() {
        courseEnrollment.fetchCourse(this);
    }

    private void updateView() {
        courseName.setText(courseEnrollment.getName() + " (" + courseEnrollment.getCode() + ")");
        boolean completed = courseEnrollment.isCompleted();
        if(completed) {
            finished.setTextColor(Color.BLUE);
            finished.setText(R.string.course_finished);
        } else {
            finished.setTextColor(Color.RED);
            finished.setText(R.string.course_not_finished);
        }
        credits.setContentText(Integer.toString(course.getCredits()));
        description.setContentText(course.getDescription());
        completion.setContentText(course.getCompletion().getCode());

    }

    private void setupViews() {
        courseName = (TextView)findViewById(R.id.course_name);
        finished = (TextView)findViewById(R.id.course_finished);
        description = (PairedTextView)findViewById(R.id.course_description);
        completion = (PairedTextView)findViewById(R.id.course_completion);
        credits = (PairedTextView)findViewById(R.id.course_credits);
    }

    @Override
    public void onResourceProcessed(KOSResource resource) {
        Message.obtain(handler, 0, resource).sendToTarget();
    }
}