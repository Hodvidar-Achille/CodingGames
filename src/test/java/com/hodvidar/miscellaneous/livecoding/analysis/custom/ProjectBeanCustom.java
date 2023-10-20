package com.hodvidar.miscellaneous.livecoding.analysis.custom;

// package com.edeal.recruitment.custom;

// import com.edeal.recruitment.ProjectBean;
// equivalent to:
import com.hodvidar.miscellaneous.livecoding.analysis.ProjectBean;

import java.util.Date;


public class ProjectBeanCustom extends ProjectBean {

    // 1. package-private visibility, should be private and should have getter/setter if we want to access it
    private final String etat;

    public ProjectBeanCustom(final String titre, final int numero, final Date dateDebut, final Date dateFin, final String etat) {
        super(titre, numero, dateDebut, dateFin);
        this.etat = etat;
    }

    public ProjectBeanCustom(final String titre, final int numero, final Date dateDebut, final Date dateFin) {
        // 2. Let's reuse code instead of duplicating it
        this(titre, numero, dateDebut, dateFin, null);
    }

}
