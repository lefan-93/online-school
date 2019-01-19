package net.thumbtack.school.database.jdbc;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcService {

    public static void insertTrainee(Trainee trainee) throws SQLException {
        String insertQuery = "INSERT INTO trainee VALUES(?,?,?,?,?)";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, trainee.getFirstName());
            preparedStatement.setString(3, trainee.getLastName());
            preparedStatement.setInt(4, trainee.getRating());
            preparedStatement.setNull(5, Types.INTEGER);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next())
                    trainee.setId(resultSet.getInt(1));
            }
        }
    }

    public static void updateTrainee(Trainee trainee) throws SQLException {
        String updateQuery = "UPDATE trainee SET first_name = ?, last_name = ?, rating = ?, group_id =? WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setString(1, trainee.getFirstName());
            preparedStatement.setString(2, trainee.getLastName());
            preparedStatement.setInt(3, trainee.getRating());
            preparedStatement.setNull(4, java.sql.Types.INTEGER);
            preparedStatement.setInt(5, trainee.getId());
            preparedStatement.executeUpdate();
        }
    }

    public static Trainee getTraineeByIdUsingColNames(int traineeId) throws SQLException {
        String selectQuery = "SELECT * FROM trainee WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, traineeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int rating = resultSet.getInt("rating");
                    return new Trainee(id, firstName, lastName, rating);
                }
            }
        }
        return null;
    }

    public static Trainee getTraineeByIdUsingColNumbers(int traineeId) throws SQLException {

        String selectQuery = "SELECT * FROM trainee WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, traineeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    int rating = resultSet.getInt(4);
                    return new Trainee(id, firstName, lastName, rating);
                }
            }
        }
        return null;
    }

    public static List<Trainee> getTraineesUsingColNumbers() throws SQLException {
        List<Trainee> trainees = new ArrayList<>();
        String selectQuery = "SELECT * FROM trainee";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int rating = resultSet.getInt("rating");
                    trainees.add(new Trainee(id, firstName, lastName, rating));

                }
            }
        }
        return trainees;
    }

    public static List<Trainee> getTraineesUsingColNames() throws SQLException {
        List<Trainee> trainees = new ArrayList<>();
        String selectQuery = "SELECT * FROM trainee";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    int rating = resultSet.getInt(4);
                    trainees.add(new Trainee(id, firstName, lastName, rating));

                }
            }
        }
        return trainees;
    }


    public static void deleteTrainee(Trainee trainee) throws SQLException {
        String deleteQuery = "DELETE FROM trainee WHERE id=? and first_name=? and last_name=? and rating =?";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, trainee.getId());
            preparedStatement.setString(2, trainee.getFirstName());
            preparedStatement.setString(3, trainee.getLastName());
            preparedStatement.setInt(4, trainee.getRating());
            preparedStatement.executeUpdate();
        }
    }

    public static void deleteTrainees() throws SQLException {
        String deleteQuery = "DELETE FROM trainee";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();

        }
    }

    public static void insertSubject(Subject subject) throws SQLException {
        String insertQuery = "INSERT INTO subject VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, subject.getName());
            preparedStatement.setNull(3, java.sql.Types.INTEGER);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next())
                    subject.setId(resultSet.getInt(1));
            }
        }
    }

    public static Subject getSubjectByIdUsingColNames(int subjectId) throws SQLException {
        String selectQuery = "SELECT * FROM subject WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, subjectId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("subject_name");
                    return new Subject(id, name);
                }
            }
        }
        return null;
    }

    public static Subject getSubjectByIdUsingColNumbers(int subjectId) throws SQLException {
        String selectQuery = "SELECT * FROM subject WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, subjectId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    return new Subject(id, name);
                }
            }
        }
        return null;
    }

    public static void deleteSubjects() throws SQLException {
        String deleteQuery = "DELETE FROM subject";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();

        }
    }

    public static void insertSchool(School school) throws SQLException {
        String insertQuery = "INSERT INTO school VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, school.getName());
            preparedStatement.setInt(3, school.getYear());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next())
                    school.setId(resultSet.getInt(1));
            }
        }
    }

    public static School getSchoolByIdUsingColNames(int schoolId) throws SQLException {
        String selectQuery = "SELECT * FROM school WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, schoolId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int year = resultSet.getInt("year");
                    return new School(id, name, year);
                }
            }
        }
        return null;
    }

    public static School getSchoolByIdUsingColNumbers(int schoolId) throws SQLException {
        String selectQuery = "SELECT * FROM school WHERE id = ?";
        try (
                PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, schoolId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    int year = resultSet.getInt(3);
                    return new School(id, name, year);
                }
            }
        }
        return null;
    }

    public static void deleteSchools() throws SQLException {
        String deleteSchoolstQuery = "DELETE FROM school";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(deleteSchoolstQuery)) {
            preparedStatement.executeUpdate();
        }
    }

    public static void insertGroup(School school, Group group) throws SQLException {

        String insertQuery = "INSERT INTO groups VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, group.getName());
            preparedStatement.setString(3, group.getRoom());
            preparedStatement.setInt(4, school.getId());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next())
                    group.setId(resultSet.getInt(1));
            }
        }
    }


    public static School getSchoolByIdWithGroups(int id) throws SQLException {
        String selectSchoolQuery = "SELECT * FROM school s JOIN groups g ON g.school_id=s.id WHERE s.id =?";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectSchoolQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                School school = null;
                ArrayList<Group> groups = new ArrayList<>();
                while (rs.next()) {
                    if (school == null) {
                        school = new School(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getInt(3));
                    }
                    groups.add(new Group(
                            rs.getInt(4),
                            rs.getString(5),
                            rs.getString(6)));
                }
                school.setGroups(groups);
                return school;
            }

        }
    }

    public static List<School> getSchoolsWithGroups() throws SQLException {
        String selectSchoolQuery = "SELECT * FROM school s JOIN groups g ON g.school_id=s.id";
        try (PreparedStatement preparedStatement = JdbcUtils.getConnection().prepareStatement(selectSchoolQuery); ResultSet rs = preparedStatement.executeQuery()) {
            School school = null;
            ArrayList<School> schools = new ArrayList<>();
            ArrayList<Group> groups = null;
            while (rs.next()) {
                if (school == null || school.getId() != rs.getInt(1)) {
                    school = new School(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3));
                    groups = new ArrayList<>();
                    school.setGroups(groups);
                    schools.add(school);
                }
                groups.add(new Group(
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6)));
            }
            return schools;
        }
    }
}