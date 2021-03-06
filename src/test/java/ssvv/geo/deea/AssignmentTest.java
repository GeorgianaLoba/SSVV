package ssvv.geo.deea;

import domain.Student;
import domain.Tema;
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


public class AssignmentTest {
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
        service.addTema(new Tema("1", "naspa", 8, 7));
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

    @Test(expected = ValidationException.class)
    public void tc_1_WBT(){
        String id = "";
        service.addTema(new Tema(id, "something", 12, 13));

    }

    @Test(expected = ValidationException.class)
    public void tc_2_WBT(){
        String id = "100";
        service.addTema(new Tema(id, "", 12, 13));

    }
    @Test(expected = ValidationException.class)
    public void tc_3_WBT(){
        Integer deadline = 15;
        service.addTema(new Tema("5", "naspa", deadline, 13));

    }
    @Test(expected = ValidationException.class)
    public void tc_4_WBT(){

        service.addTema(new Tema("5", "naspa", 12, 15));
    }
    @Test
    public void tc_5_WBT(){
        String id = "100";
        Tema tema = service.addTema(new Tema(id, "something", 12, 13));
        assertNotNull(service.findTema(id));
    }
}
