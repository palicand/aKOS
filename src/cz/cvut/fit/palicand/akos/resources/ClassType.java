package cz.cvut.fit.palicand.akos.resources;

import android.content.Context;
import cz.cvut.fit.palicand.akos.R;

import java.util.EnumMap;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 25/01/13
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public enum ClassType {
    ATELIER("A", R.string.class_type_atelier),
    BLOCK("B", R.string.class_type_block),
    CONSULTATION("K", R.string.class_type_consultation),
    LABORATORY("L", R.string.class_type_laboratory),
    LECTURE("P", R.string.class_type_lecture),
    PROJECT("PR", R.string.class_type_project),
    PROJECT_INDV("PRI", R.string.class_type_project_indv),
    PROJECT_TEAM("PRT", R.string.class_type_project_team),
    PROSEMINAR("R", R.string.class_type_proseminar),
    PT_COURSE("TVK",R.string.class_type_pt_course),
    SEMINAR("S", R.string.class_type_seminar),
    TUTORIAL("C", R.string.class_type_tutorial),
    UNDEFINED(null, R.string.class_type_undefined);

    private final String code;
    private final int resId;


    ClassType(String code, int resId) {
        this.code = code;
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public String getCode() {
        return code;
    }


}
