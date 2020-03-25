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

public class AssignmentTest {

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
        createXMLFile("test-teme.xml");
        Validator<Tema> temaValidator = new TemaValidator();
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "test-teme.xml");
        service = new Service(null, fileRepository2, null);
    }

    @AfterClass
    public static void afterClass() throws IOException {
        Files.delete(Paths.get("test-teme.xml"));
    }

    @Test(expected = ValidationException.class)
    public void addAssignmentTC1()
    {
        assertEquals(0, service.saveTema("4", "test", 16, 5));
    }

    @Test
    public void addAssignmentTC2()
    {
        assertEquals(1, service.saveTema("4", "test", 8, 5));
    }
}
