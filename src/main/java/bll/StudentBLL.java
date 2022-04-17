package bll;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.StudentAgeValidator;
import bll.validators.Validator;
import dao.StudentDAO;
import model.Student;

public class StudentBLL {

	private List<Validator<Student>> validators;
	private StudentDAO studentDAO;

	public StudentBLL() {
		validators = new ArrayList<Validator<Student>>();
		validators.add(new EmailValidator());
		validators.add(new StudentAgeValidator());

		studentDAO = new StudentDAO();
	}

	public Student findStudentById(int id) {
		Student st = studentDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The student with id =" + id + " was not found!");
		}
		return st;
	}

	public List < Student > findAll() {
		List < Student > st = studentDAO.findAll();
		if (st == null) {
			throw new NoSuchElementException("The student table is empty!");
		}
		return st;
	}

	public Student update(Student t) throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		Student st = studentDAO.update(t);
		if (st == null) {
			throw new NoSuchElementException("The student with id =" + t.getId() + " was not updated!");
		}
		return st;
	}

	public Student insert(Student t) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
		Student st = studentDAO.insert(t);
		if (st == null) {
			throw new NoSuchElementException("The student with id =" + t.getId() + " was not added!");
		}
		return st;
	}

	public void delete(int id) throws Exception {
		try {
			studentDAO.delete(id);
		}
		catch (Exception e){
			throw new NoSuchElementException("The student with id =" + id + " was not deleted!");
		}
	}

}
