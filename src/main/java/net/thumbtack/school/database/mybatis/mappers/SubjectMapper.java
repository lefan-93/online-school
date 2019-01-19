package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SubjectMapper {
    @Delete("DELETE FROM subject")
    void deleteAll();

    @Insert("INSERT INTO subject (subject_name) VALUES" +
            "(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Subject subject);

    @Select("SELECT id, subject_name as name FROM subject WHERE id = #{id}")
    Subject getById(int id);

    @Select("SELECT id, subject_name as name FROM subject")
    List<Subject> getAll();

    @Update("UPDATE subject " +
            "SET subject_name = #{name} " +
            "WHERE id = #{id}")
    void update(Subject subject);

    @Delete("DELETE FROM subject WHERE id = #{id}")
    void delete(Subject subject);

    @Select("SELECT id, subject_name as name FROM subject WHERE id IN (SELECT subject_id FROM subject_group WHERE group_id = #{id})")
    List<Subject> getByGroup(int id);
}
