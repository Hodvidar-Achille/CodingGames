package com.hodvidar.miscellaneous.livecoding.analysis;

import com.hodvidar.miscellaneous.livecoding.analysis.custom.ProjectBeanCustom;
import org.junit.jupiter.api.Test;

import java.util.Date;

class ProjectBeanCustomTest {

    @Test
    void test() {
        Date dataSart = new Date();
        Date dataEnd = new Date();
        ProjectBeanCustom pbc1 = new ProjectBeanCustom("Projet Recruteemnt", 10, dataSart, dataEnd, "GOING");

        ProjectBeanCustom pbc2 = (ProjectBeanCustom) new ProjectBean ("Projet Recruteemnt", 10, dataSart, dataEnd);

        // ProjectBeanCustom pbc3 = new GenericBean ("Projet Recruteemnt", 10, dataSart, dataEnd);
    }
}
