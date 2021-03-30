package ssvv.geo.deea;

import domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    Service service;


    @Before
    public void setUp() throws Exception {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "src/test/java/resources/fisiere/Studenti.xml";
        String filenameTema = "src/test/java/resources/fisiere/Teme.xml";
        String filenameNota = "src/test/java/resources/fisiere/Note.xml";


        //StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
        //TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
        //NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        //NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        service.addStudent(new Student("1","Geo",934,"geodeaa@cool.ro","Andreea Vescan"));
    }

    @After
    public void tearDown() throws Exception {
        Path file = Paths.get("src/test/java/resources/fisiere/Studenti.xml");
        Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
        file = Paths.get("src/test/java/resources/fisiere/Teme.xml");
        Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
        file = Paths.get("src/test/java/resources/fisiere/Note.xml");
        Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
    }

    /**
     * Rigorous Test :-)
     */

    @Test
    public void tc_1_addStudentSuccessfully(){
        Student student=new Student("12id1",	"Georgiana Andreea",	934	,"geodeea@cool.ro",	"Andreea Vescan");

        assertNull(service.addStudent(student));
        Student addedStudent=service.findStudent(student.getID());
        assertEquals(student.getNume(),addedStudent.getNume());
        assertEquals(student.getEmail(),addedStudent.getEmail());
        assertEquals(student.getTeacher(),addedStudent.getTeacher());
        assertEquals(student.getGrupa(),addedStudent.getGrupa());
        //test case EC 1,5,8,10,13 BVA 4

    }
    @Test (expected = ValidationException.class)
    public void tc_5_addStudentError_emptyName(){
        Student student=new Student("12id1",	"",	934	,"geodeea@cool.ro",	"Andreea Vescan");

        service.addStudent(student);
    }
    @Test (expected = ValidationException.class)
    public void tc_6_addStudentError_nullName(){
        Student student=new Student("12id1",	null,	934	,"geodeea@cool.ro",	"Andreea Vescan");

        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void tc_3_addStudentError_emptyId(){
        Student student=new Student("",	"Geo",	934	,"geodeea@cool.ro",	"Andreea Vescan");

        service.addStudent(student);
    }
    @Test(expected = ValidationException.class)
    public void tc_4_addStudentError_idNull(){
        Student student=new Student(null,	"Geo",	934	,"geodeea@cool.ro",	"Andreea Vescan");

        service.addStudent(student);
    }

    @Test
    public void tc_2_addStudentError_idAlreadyExists(){
        Student student=new Student("1",	"Georgiana",	934	,"geodeea@cool.ro",	"Andreea Vescan");
        assertNotNull(service.addStudent(student));
    }
    @Test(expected = ValidationException.class)
    public void tc_7_addStudentError_nullEmail(){
        Student student=new Student("12id1",	"Geo",	934	,null,	"Andreea Vescan");

        service.addStudent(student);

    }

    @Test(expected = ValidationException.class)
    public void tc_8_addStudentError_invalidGroup(){
        Student student=new Student("12id1",	"Geo",	-1	,"geodeea@cool.ro",	"Andreea Vescan");

        service.addStudent(student);
        //BVA 1
    }

    @Test(expected = ValidationException.class)
    public void tc_9_addStudentError_emptyEmail(){
        Student student=new Student("12id1",	"Geo",	934	,"",	"Andreea Vescan");

        service.addStudent(student);
    }
    @Test(expected = ValidationException.class)
    public void tc_10_addStudentError_emptyTeacher(){
        Student student=new Student("12id1",	"Geo",	934	,"geodeea@cool.ro",	"");

        service.addStudent(student);
    }
    @Test(expected = ValidationException.class)
    public void tc_11_addStudentError_nullTeacher(){
        Student student=new Student("12id1",	"Geo",	934	,"geodeea@cool.ro",	null);

        service.addStudent(student);
    }
    @Test
    public void tc_12_addStudentSuccessfully_BVA2(){
        Student student=new Student("10",	"Georgiana Andreea",	0	,"geodeea@cool.ro",	"Andreea Vescan");

        assertNull(service.addStudent(student));



    }
    @Test
    public void tc_13_addStudentSuccessfully_BVA3(){
        Student student=new Student("11",	"Georgiana Andreea",	1	,"geodeea@cool.ro",	"Andreea Vescan");

        assertNull(service.addStudent(student));



    }






}
