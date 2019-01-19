package net.thumbtack.school.database.mybatis;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestSchoolGroupsOperations extends TestBase {

    @Test
    public void testInsertSchoolWithGroups() {
        School school2018 = insertSchool("TTSchool", 2018);
        insertSchoolGroups(school2018, 2018, new String[]{"FrontEnd", "BackEnd"}, new String[]{"11", "12"});
        School schoolFromDB = schoolDao.getById(school2018.getId());
        assertEquals(school2018, schoolFromDB);
    }

    @Test
    public void testUpdateSchoolGroup() {
        School school2018 = insertSchool("TTSchool", 2018);
        List<Group> groups = insertSchoolGroups(school2018, 2018, new String[]{"FrontEnd", "BackEnd"}, new String[]{"11", "12"});
        groups.get(0).setRoom("100");
        groups.get(0).setName("web");
        groupDao.update(groups.get(0));
        School schoolFromDB = schoolDao.getById(school2018.getId());
        assertEquals(school2018, schoolFromDB);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertGroupWithoutSchool() {
        Group group = new Group("frontend2018", "11");
        groupDao.insert(null, group);
    }


    @Test(expected = RuntimeException.class)
    public void testInsertGroupBeforeInsertingSchool() {
        School school = new School("TTSchool", 2018);
        Group group = new Group("frontend2018", "11");
        groupDao.insert(school, group);

    }

}
