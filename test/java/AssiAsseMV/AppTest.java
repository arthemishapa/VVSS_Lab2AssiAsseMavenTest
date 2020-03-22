package AssiAsseMV;

import domain.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    private static Service service;

    private static void createXMLFile(String fileName) throws ParserConfigurationException, TransformerException
    {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("Entitati");
        document.appendChild(root);
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(fileName));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(domSource, streamResult);
    }

    @BeforeClass
    public static void beforeClass() throws TransformerException, ParserConfigurationException {
        createXMLFile("test-studenti.xml");
        createXMLFile("test-teme.xml");
        createXMLFile("test-note.xml");
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();
        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "test-studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "test-teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "test-note.xml");
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @AfterClass
    public static void afterClass() throws IOException {
        Files.delete(Paths.get("test-studenti.xml"));
        Files.delete(Paths.get("test-teme.xml"));
        Files.delete(Paths.get("test-note.xml"));
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void addStudentTC1()
    {
        char[] textId = new char[10];
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            textId[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        assertEquals(1, service.saveStudent(new String(textId), "TestNume", 933));
    }

    @Test(expected = ValidationException.class)
    public void addStudentTC2()
    {
        assertEquals(1, service.saveStudent("", "", 939));
    }

    @Test(expected = ValidationException.class)
    public void addStudentTC3()
    {
        assertEquals(1, service.saveStudent(null, null, 939));
    }

    @Test(expected = ValidationException.class)
    public void addStudentTC4()
    {
        assertEquals(1, service.saveStudent("", "", 109));
    }

    @Test(expected = ValidationException.class)
    public void addStudentTC5()
    {
        assertEquals(1, service.saveStudent(null, null, 109));
    }

    @Test(expected = ValidationException.class)
    public void addStudentTC6()
    {
        assertEquals(1, service.saveStudent(null, null, 110));
    }

    @Test(expected = ValidationException.class)
    public void addStudentTC7()
    {
        assertEquals(1, service.saveStudent("", "", 110));
    }

    @Test
    public void addStudentTC8()
    {
        char[] textId = new char[10];
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            textId[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        assertEquals(1, service.saveStudent(new String(textId), "TestNume", 111));
    }

    @Test
    public void addStudentTC9()
    {
        char[] textId = new char[10];
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            textId[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        assertEquals(1, service.saveStudent(new String(textId), "BVA TEST", 937));
    }

    @Test(expected = ValidationException.class)
    public void addStudentTC10()
    {
        assertEquals(1, service.saveStudent(null, null, 938));
    }

    @Test(expected = ValidationException.class)
    public void addStudentTC11()
    {
        assertEquals(1, service.saveStudent("", "", 938));
    }

}