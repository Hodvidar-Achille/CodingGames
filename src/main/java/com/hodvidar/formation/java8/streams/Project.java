package com.hodvidar.formation.java8.streams;

public class Project {

    private String name;
    private String team;
    private String projectManager;

    Project() {
    }

    public Project(final String name, final String team, final String projectManager) {
        this.name = name;
        this.team = team;
        this.projectManager = projectManager;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(final String team) {
        this.team = team;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(final String projectManager) {
        this.projectManager = projectManager;
    }
}