package com.hodvidar.miscellaneous.livecoding.analysis;

import com.hodvidar.miscellaneous.livecoding.analysis.custom.ProjectBeanCustom;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

class ProjectBeanCustomTest {

    @Test
    void test() {
        final Date dataSart = new Date();
        final Date dataEnd = new Date();
        final ProjectBeanCustom pbc1 = new ProjectBeanCustom("Projet Recruteemnt", 10, dataSart, dataEnd, "GOING");

        // final ProjectBeanCustom pbc2 = (ProjectBeanCustom) new ProjectBean ("Projet Recruteemnt", 10, dataSart, dataEnd);

        // ProjectBeanCustom pbc3 = new GenericBean ("Projet Recruteemnt", 10, dataSart, dataEnd);
    }

    @Test
    @Disabled
    void testSaveBean() throws Exception {
        final HttpSession session = null;
        final String titre = "Projet Recrutement";
        final int numero = 10;
        final Date dateDebut = new Date();
        // Set dateFin as 12/11/2024
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final Date dateFin = sdf.parse("12/11/2024");
        final String etat = "GOING";
        // Construct the JSON string
        final String json = String.format("{\"titre\":\"%s\",\"numero\":%d,\"dateDebut\":\"%s\",\"dateFin\":\"%s\",\"etat\":\"%s\"}",
                titre, numero, sdf.format(dateDebut), sdf.format(dateFin), etat);
        // Now save the bean using the saveBean method
        final ProjectBean bean = (ProjectBean) ProjectGenericBean.saveBean(session, json, ProjectBeanCustom.class, true);
    }
}
