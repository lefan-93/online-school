package net.thumbtack.school.hiring.model;

import java.util.Objects;

public class Requirement extends Skill {
    private boolean required;

    public Requirement(String name, int lavel, boolean required) {
        super(name, lavel);
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Requirement)) return false;
        if (!super.equals(o)) return false;
        Requirement that = (Requirement) o;
        return isRequired() == that.isRequired();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isRequired());
    }
}

