package cz.cvut.fit.palicand.akos.resources;

import cz.cvut.fit.palicand.akos.R;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 28/01/13
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public enum ParallelType {
    LABORATORY("L", R.string.class_type_laboratory),
    LECTURE("P", R.string.class_type_lecture),
    TUTORIAL("C", R.string.class_type_tutorial),
    UNDEFINED("", R.string.class_type_undefined);

    private String code;
    private int resId;

    ParallelType(String code, int resId) {
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
