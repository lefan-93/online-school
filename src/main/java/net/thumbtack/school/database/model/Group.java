package net.thumbtack.school.database.model;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int id;
    private String name;
    private String room;
    private List<Trainee> trainees;
    private List<Subject> subjects;

    public Group() {
    }

    public Group(int id, String name, String room, List<Trainee> trainees, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.trainees = trainees;
        this.subjects = subjects;
    }

    public Group(int id, String name, String room) {
        this.id = id;
        this.name = name;
        this.room = room;
        trainees = new ArrayList<>();
        subjects = new ArrayList<>();
    }

    public Group(String name, String room) {
        id = 0;
        this.name = name;
        this.room = room;
        trainees = new ArrayList<>();
        subjects = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addTrainee(Trainee trainee) {
        trainees.add(trainee);
    }

    public void removeTrainee(Trainee trainee) {
        trainees.remove(trainee);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;

        Group group = (Group) o;

        if (getId() != group.getId()) return false;
        if (getName() != null ? !getName().equals(group.getName()) : group.getName() != null) return false;
        if (getRoom() != null ? !getRoom().equals(group.getRoom()) : group.getRoom() != null) return false;
        if (getTrainees() != null ? !getTrainees().equals(group.getTrainees()) : group.getTrainees() != null)
            return false;
        return getSubjects() != null ? getSubjects().equals(group.getSubjects()) : group.getSubjects() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getRoom() != null ? getRoom().hashCode() : 0);
        result = 31 * result + (getTrainees() != null ? getTrainees().hashCode() : 0);
        result = 31 * result + (getSubjects() != null ? getSubjects().hashCode() : 0);
        return result;
    }
}
