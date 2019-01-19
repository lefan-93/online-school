package net.thumbtack.school.hiring.model;

public class Skill implements Comparable<Skill>{
    private String name;
    private int level;

    public Skill(String skillName, int level) {
        this.name = skillName;
        this.level = level;
    }

    public String getName() {
        return name;
    }


    public void setSkill(String skill) {
        this.name = skill;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(Skill o) {
        if (getName().compareTo(o.getName())!=0)
            return getName().compareTo(o.getName());
        return getLevel()-o.getLevel();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;

        Skill skill1 = (Skill) o;

        if (getLevel() != skill1.getLevel()) return false;
        return getName().equals(skill1.getName());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getLevel();
        return result;
    }


}
