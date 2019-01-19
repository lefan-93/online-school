package net.thumbtack.school.database.model;

import java.util.Objects;

public class Trainee {

    private int id;
    private String firstName;
    private String lastName;
    private int rating;

    public Trainee() {
    }

    public Trainee(int id, String firstName, String lastName, int rating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
    }

    public Trainee(String firstName, String lastName, int rating) {
        id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trainee)) return false;

        Trainee trainee = (Trainee) o;

        if (getId() != trainee.getId()) return false;
        if (getRating() != trainee.getRating()) return false;
        if (getFirstName() != null ? !getFirstName().equals(trainee.getFirstName()) : trainee.getFirstName() != null)
            return false;
        return getLastName() != null ? getLastName().equals(trainee.getLastName()) : trainee.getLastName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + getRating();
        return result;
    }
}
