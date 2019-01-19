package net.thumbtack.school.database.mybatis;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestSchoolGroupSubjectsOperations extends TestBase {

    @Test
    public void testInsertSchoolWithGroupsAndSubjects() {
        School school2018 = insertSchool("TTSchool", 2018);
        Map<String, Subject> subjects = insertSubjects("Linux", "MySQL", "NodeJS");
        List<Group> groups = insertSchoolGroupsWithSubjects(school2018, 2018, subjects);
        school2018.setGroups(groups);
        School schoolFromDB = schoolDao.getById(school2018.getId());
        assertEquals(school2018, schoolFromDB);
    }

    @Test
    public void testInsertAndDeleteTwoSchoolsWithGroupsAndSubjectsGetAllLazy() {
        School school2018 = insertSchool("TTSchool", 2018);
        School school2019 = insertSchool("TTSchool", 2019);
        Map<String, Subject> subjects = insertSubjects("Linux", "MySQL", "NodeJS");
        insertSchoolGroupsWithSubjects(school2018, 2018, subjects);
        insertSchoolGroupsWithSubjects(school2019, 2019, subjects);
        List<School> schoolsFromDB = schoolDao.getAllLazy();
        assertEquals(2, schoolsFromDB.size());
        schoolsFromDB.sort(Comparator.comparingInt(School::getId));
        assertEquals(school2018, schoolsFromDB.get(0));
        assertEquals(school2019, schoolsFromDB.get(1));
        schoolDao.deleteAll();
        schoolsFromDB = schoolDao.getAllLazy();
        assertEquals(0, schoolsFromDB.size());
        List<Group> groupsFromDB = groupDao.getAll();
        assertEquals(0, groupsFromDB.size());

    }

    @Test
    public void testInsertAndDeleteTwoSchoolsWithGroupsAndSubjectsGetAllUsingJoin() {
        School school2018 = insertSchool("TTSchool", 2018);
        School school2019 = insertSchool("TTSchool", 2019);
        Map<String, Subject> subjects = insertSubjects("Linux", "MySQL", "NodeJS");
        insertSchoolGroupsWithSubjects(school2018, 2018, subjects);
        insertSchoolGroupsWithSubjects(school2019, 2019, subjects);
        List<School> schoolsFromDB = schoolDao.getAllUsingJoin();
        assertEquals(2, schoolsFromDB.size());
        schoolsFromDB.sort(Comparator.comparingInt(School::getId));
        assertEquals(school2018, schoolsFromDB.get(0));
        assertEquals(school2019, schoolsFromDB.get(1));
        schoolDao.deleteAll();
        schoolsFromDB = schoolDao.getAllLazy();
        assertEquals(0, schoolsFromDB.size());
        List<Group> groupsFromDB = groupDao.getAll();
        assertEquals(0, groupsFromDB.size());

    }

}
