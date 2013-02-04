package cz.cvut.fit.palicand.akos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fit.palicand.akos.resources.KOSResource;
import cz.cvut.fit.palicand.akos.resources.OnResourceProcessedListener;
import cz.cvut.fit.palicand.akos.resources.Resources;
import cz.cvut.fit.palicand.akos.resources.Student;
import cz.cvut.fit.palicand.akos.views.PairedTextView;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 12/01/13
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
public class StudentInfoFragment extends Fragment {
    private Student student;
    private String username;
    Handler handler;

    OnResourceProcessedListener onResourceProcessedListener;

    private PairedTextView nameView;
    private PairedTextView usernameView;
    private PairedTextView emailView;
    private PairedTextView orderView;

    @Override
    public  void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = ((InfoActivity)getActivity()).getUsername();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == 0) {
                    student = (Student)msg.obj;
                    ((InfoActivity) getActivity()).setStudent(student);
                    updateView();
                    return true;
                }
                return false;
            }
        });

        onResourceProcessedListener = new OnResourceProcessedListener() {
            @Override
            public void onResourceProcessed(KOSResource resource) {
                Message.obtain(handler, 0, resource).sendToTarget();
            }
        };

    }

    private void updateView() {
        nameView.setContentText(student.getLastName() + " " + student.getFirstName());
        usernameView.setContentText(student.getUsername());
        orderView.setContentText(Integer.toString(student.getGrade()));
        emailView.setContentText(student.getEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);

        nameView = (PairedTextView)view.findViewById(R.id.student_info_name);
        usernameView = (PairedTextView)view.findViewById(R.id.student_info_username);
        emailView = (PairedTextView)view.findViewById(R.id.student_info_email);
        orderView = (PairedTextView)view.findViewById(R.id.student_info_order);

        if(savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.student_bundle_key))) {
            student = (Student)savedInstanceState.getSerializable(getString(R.string.student_bundle_key));
            updateView();
        } else {
            student = ((InfoActivity)getActivity()).getStudent();
            if(student == null)
                acquireStudent();
            else
                updateView();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(getString(R.string.student_bundle_key), student);
    }



    private void acquireStudent() {
        Log.d("akos", "acquiring student");
        Resources.fetchStudent(username, onResourceProcessedListener);
    }
}
