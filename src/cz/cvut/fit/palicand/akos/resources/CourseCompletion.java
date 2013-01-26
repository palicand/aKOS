package cz.cvut.fit.palicand.akos.resources;

import cz.cvut.fit.palicand.akos.R;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 25/01/13
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
public enum CourseCompletion {
    EXAM("ZK", R.string.course_completion_exam),
    CREDIT("Z", R.string.course_completion_credit),
    CREDIT_EXAM("Z,ZK", R.string.course_completion_credit_exam),
    DEFENCE("D", R.string.course_completion_defence),
    CLFD_CREDIT("KZ", R.string.course_completion_clfd_credit),
    NOTHING("NIC", R.string.course_completion_nothing),
    UNDEFINED(null, R.string.course_completion_undefined);

    private final String code;
    private final int resId;

    CourseCompletion(String code, int resId) {
        this.code = code;
        this.resId = resId;
    }

    public String getCode() {
        return code;
    }

    public int getResId() {
        return resId;
    }
}

