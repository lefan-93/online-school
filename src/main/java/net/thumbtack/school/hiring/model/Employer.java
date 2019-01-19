package net.thumbtack.school.hiring.model;

import java.util.ArrayList;
import java.util.List;

public class Employer extends User {

    private String companyName;
    private String address;
    private List<Vacancy> vacancies = new ArrayList<>();

    public Employer(String companyName, String address, String firstName, String surName, String middleName, String login, String password, String email) {
        super(firstName, surName, middleName, login, password, email);
        this.companyName = companyName;
        this.address = address;
    }

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }

    public Vacancy getVacancie(String jobTitle) {
        for (Vacancy v : vacancies) {
            if (v.getJobTitle().equals(jobTitle)) {
                return v;
            }
        }
        return null;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Vacancy> getVacancis() {
        return vacancies;
    }

    public void deleteVacancie(String vacancie) {
        for (Vacancy v : vacancies) {
            if (v.getJobTitle().equals(vacancie)) {
                vacancies.remove(v);
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employer)) return false;
        if (!super.equals(o)) return false;

        Employer employer = (Employer) o;

        if (getCompanyName() != null ? !getCompanyName().equals(employer.getCompanyName()) : employer.getCompanyName() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(employer.getAddress()) : employer.getAddress() != null)
            return false;
        return vacancies != null ? vacancies.equals(employer.vacancies) : employer.vacancies == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCompanyName() != null ? getCompanyName().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (vacancies != null ? vacancies.hashCode() : 0);
        return result;
    }
}
