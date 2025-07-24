package com.hodvidar.miscellaneous.livecoding.analysis;
// package com.edeal.recruitment;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;


// 1. problem: this class need to implements ProjectGenericBean method getDurationToString(String)
public class ProjectBean extends ProjectGenericBean {

    public static final String EMPTY = "-?-";
    // 2. "-?-" need to be a static final field as well
    private static final String toStringSeparator = " - ";
    public final int numero;
    // 3. Parameters need to be final if they are only set in the constructor
    private final String titre;
    private final Date dateDebut;
    private final Date dateFin;

    // 4. It is a little better to have method parameter as final to avoid errors
    // I personally would prefer all variables/parameters name to be in english
    public ProjectBean(final String titre, final int numero, final Date dateDebut, final Date dateFin) {
        this.titre = titre;
        this.numero = numero;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // 5. The toString method should have the annotation @Override
    @Override
    public String toString() {
        // 6. It is better to directly use String instead of the StringBuilder
        // Because it is easier to read and Java will create the exact same bytecode
        // (let the compiler do the optimization for you and focus on readability)
        return this.numero + toStringSeparator + (StringUtils.isEmpty(this.titre) ? EMPTY : this.titre);
    }

    /*
    7. deprecated Date class and conversion to LocalDate using Calendar.
    It is better to use the new Java 8 Date/Time API directly.
    Also, it is a little nicer to try to have less indentation in the code. By returning early
    */
    @Override
    public Period getDuration() {
        if (this.dateDebut == null || this.dateFin == null) {
            return Period.ZERO;
        }
        final LocalDate startDate = dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final LocalDate endDate = dateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(startDate, endDate);
    }

    @Override
    public String getDurationToString() {
        // 8. Calling this.getDuration() 3 times is a waste of CPU
        final Period duration = this.getDuration();
        return duration.getYears() + "Year(s)" + toStringSeparator
                + duration.getMonths() + "Month(s)" + toStringSeparator
                + duration.getDays() + "Day(s)";
    }

    @Override
    public String getDurationToString(final String flag) {
        switch (flag) {
            case "YEAR" -> {
                final Period duration = getDuration();
                return duration.getYears() + " Year(s)";
            }
            case "MONTH" -> {
                final Period duration = getDuration();
                return (duration.getYears() * 12 + duration.getMonths()) + " Month(s)";
            }
            case "DAY" -> {
                // Since Period does not account for day time-saving and other nuances,
                // using LocalDate to calculate exact days might be more accurate than just using duration.getDays()
                final LocalDate startDate = this.dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                final LocalDate endDate = this.dateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                final long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
                return totalDays + " Day(s)";
            }
            default -> throw new IllegalArgumentException("Invalid flag: " + flag);
        }
    }
}
