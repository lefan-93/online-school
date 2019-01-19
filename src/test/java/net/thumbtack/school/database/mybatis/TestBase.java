package net.thumbtack.school.database.mybatis;

import net.thumbtack.school.database.mybatis.dao.*;
import net.thumbtack.school.database.mybatis.daoimpl.*;
import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.utils.MyBatisUtils;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.*;

import static org.junit.Assert.assertNotEquals;

public class TestBase {

    private CommonDao commonDao = new CommonDaoImpl();
    protected SchoolDao schoolDao = new SchoolDaoImpl();
    protected GroupDao groupDao = new GroupDaoImpl();
    protected TraineeDao traineeDao = new TraineeDaoImpl();
    protected SubjectDao subjectDao = new SubjectDaoImpl();

    private static boolean setUpIsDone = false;

    @BeforeClass()
    public static void setUp() {
        if (!setUpIsDone) {
            Assume.assumeTrue(MyBatisUtils.initSqlSessionFactory());
            setUpIsDone = true;
        }
    }

    @Before()
    public void clearDatabase() {
        commonDao.clear();
    }

    protected Trainee insertTrainee(String firstName, String lastName, int rating, Group group) {
        Trainee trainee = new Trainee(firstName, lastName, rating);
        traineeDao.insert(group, trainee);
        assertNotEquals(0, trainee.getId());
        return trainee;
    }

    protected Subject insertSubject(String name) {
        Subject subject = new Subject(name);
        subjectDao.insert(subject);
        assertNotEquals(0, subject.getId());
        return subject;
    }

    protected Map<String, Subject> insertSubjects(String... subjectNames) {
        Map<String, Subject> subjects = new HashMap<>();
        for (String name : subjectNames) {
            subjects.put(name, insertSubject(name));
        }
        return subjects;
    }

    protected School insertSchool(String name, int year) {
        School school = new School(name, year);
        schoolDao.insert(school);
        assertNotEquals(0, school.getId());
        return school;
    }


    protected Group insertGroup(School school, String name, String room, int year) {
        Group group = new Group(name + year, room);
        groupDao.insert(school, group);
        assertNotEquals(0, group.getId());
        return group;
    }


    protected List<Group> insertSchoolGroups(School school, int year, String[] names, String[] rooms) {
        Group group0 = insertGroup(school, names[0], rooms[0], year);
        Group group1 = insertGroup(school, names[1], rooms[1], year);
        List<Group> groups = new ArrayList<>();
        groups.add(group0);
        groups.add(group1);
        school.setGroups(groups);
        return groups;
    }

    protected Group insertGroupWithSubjects(School school, int year, String name, String room,
                                            Map<String, Subject> subjects, String[] subjectNames) {
        Group group = insertGroup(school, name, room, year);
        for (String subjectName : subjectNames) {
            group.addSubject(subjects.get(subjectName));
            groupDao.addSubjectToGroup(group, subjects.get(subjectName));
        }
        return group;
    }


    protected List<Group> insertSchoolGroupsWithSubjects(School school, int year, Map<String, Subject> subjects) {
        Group groupFrontEnd = insertGroupWithSubjects(school, year, "FrontEnd", "11", subjects, new String[]{"Linux", "NodeJS"});
        Group groupBackEnd = insertGroupWithSubjects(school, year, "BackEnd", "12", subjects, new String[]{"MySQL", "NodeJS"});
        List<Group> groups = new ArrayList<>();
        groups.add(groupFrontEnd);
        groups.add(groupBackEnd);
        school.setGroups(groups);
        return groups;
    }

    protected void insertBackendTrainees(Group groupBackEnd) {
        Trainee traineeSidorov = insertTrainee("Сидор", "Сидоров", 2, groupBackEnd);
        Trainee traineeSmirnov = insertTrainee("Николай", "Смирнов", 3, groupBackEnd);
        groupBackEnd.addTrainee(traineeSidorov);
        groupBackEnd.addTrainee(traineeSmirnov);
    }

    protected void insertFrontEndTrainees(Group groupFrontEnd) {
        Trainee traineeIvanov = insertTrainee("Иван", "Иванов", 5, groupFrontEnd);
        Trainee traineePetrov = insertTrainee("Петр", "Петров", 4, groupFrontEnd);
        groupFrontEnd.addTrainee(traineeIvanov);
        groupFrontEnd.addTrainee(traineePetrov);
    }
}
