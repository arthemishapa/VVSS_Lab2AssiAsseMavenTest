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
public class IntegrationTest
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
    public void addStudentIntegrationTC1()
    {
        char[] textId = new char[10];
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            textId[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        assertEquals(1, service.saveStudent(textId.toString(), "TestNume", 933));
    }


    @Test
    public void addAssignmentIntegrationTC1()
    {
        assertEquals(1, service.saveTema("4", "test", 8, 5));
    }

    @Test
    public void addGradeIntegrationTC1()
    {
        service.saveStudent("1111", "TestNume", 933);
        service.saveTema("10", "test", 6, 5);
        assertEquals(1, service.saveNota("1111", "10", 7, 6, "ok"));
    }

    @Test
    public void addIntegrationIntegrationTC1()
    {
        assertEquals(1, service.saveStudent("11112", "TestNume", 933));
        assertEquals(1, service.saveTema("101", "test", 6, 5));
        assertEquals(1, service.saveNota("11112", "101", 7, 6, "ok"));
    }

    @Test
    public void addStudentAssignmentIntegrationTC1()
    {
        assertEquals(1, service.saveStudent("11113", "TestNume3", 933));
        assertEquals(1, service.saveTema("1013", "test3", 6, 5));
    }
}