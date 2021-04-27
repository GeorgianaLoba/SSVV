package ssvv.geo.deea;

import domain.Nota;
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

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;

public class IntegralIntegrationTest {
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
        service.addStudent(new Student("666","Geo",934,"geodeaa@cool.ro","Andreea Vescan"));
        Tema tema = service.addTema(new Tema("666", "something", 4,1));
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

    @Test
    public void tc_1_incremental_top(){
        Student student = new Student("66iar","Geo",934,"geo@yahoo","Andreea Vescan");
        assertNull(service.addStudent(student));
    }

    @Test
    public void tc_2_incremental_middle(){
        String id = "66dar";
        Student student = new Student("66iar","Geo",934,"geo@yahoo","Andreea Vescan");
        assertNull(service.addStudent(student));
        Tema tema = service.addTema(new Tema(id,"do it",4,1));
        assertNotNull(service.findTema(id));
    }

    @Test
    public void tc_3_incremental_bottom(){
        service.addStudent(new Student("66iar","Geo",934,"geo@yahoo","Andreea Vescan"));
        service.addTema(new Tema("66dar","do it",4,1));
        assertNull(service.addNota(new Nota("666","66iar","66dar",9.4,LocalDate.of(2018,11,2)),"good"));
    }

}
