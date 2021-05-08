import com.docusign.esign.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

/**
 * Utility class to create objects related to envelope.
 */
public final class EnvelopeHelpers {

    public static final String ENVELOPE_STATUS_SENT = "sent";
    public static final String ENVELOPE_STATUS_CREATED = "created";
    public static final String SIGNER_STATUS_CREATED = "Created";
    public static final String DELIVERY_METHOD_EMAIL = "Email";
    public static final String SIGNER_ROLE_NAME = "signer";
    public static final String CC_ROLE_NAME = "cc";
    public static final String WORKFLOW_STEP_ACTION_PAUSE = "pause_before";
    public static final String WORKFLOW_TRIGGER_ROUTING_ORDER = "routing_order";
    public static final String WORKFLOW_STATUS_IN_PROGRESS = "in_progress";


    private EnvelopeHelpers() {}

    /**
     * Loads a file content and copy it into a byte array.
     * @param path the absolute path within the class path
     * @return the new byte array that has been loaded from the file
     * @throws IOException in case of I/O errors
     */
    static byte[] readFile(String path) throws IOException {
        return Files.readAllBytes(Paths.get((path)));
    }

    /**
     * Loads document from a file and creates a document object that represents
     * loaded document.
     * @param fileName name of the file to load document; the extension of the
     * loading file determines an extension of the created document
     * @param docName the name of the document; it may be differ from the file
     * @param docId identifier of the created document
     * @return the {@link Document} object
     * @throws IOException if document cannot be loaded due to some reason
     */
    public static Document createDocumentFromFile(String fileName, String docName, String docId) throws IOException {
        byte[] buffer = readFile(fileName);
        String extention = ".pdf";
        return createDocument(buffer, docName, extention, docId);
    }

    /**
     * Creates a document object from the raw data.
     * @param data the raw data
     * @param documentName the name of the document; it may be differ from the file
     * @param fileExtention the extension of the creating file
     * @param documentId identifier of the created document
     * @return the {@link Document} object
     */
    static Document createDocument(byte[] data, String documentName, String fileExtention, String documentId) {
        Document document = new Document();
        document.setDocumentBase64(Base64.getEncoder().encodeToString(data));
        document.setName(documentName);
        document.setFileExtension(fileExtention);
        document.setDocumentId(documentId);
        return document;
    }

    /**
     * Create SignHere (see {@link SignHere}) field (also known as tabs) on the
     * documents using anchor (autoPlace) positioning.
     * @param anchorString the anchor string; the DocuSign platform searches
     * throughout your envelope's documents for matching anchor strings
     * @param yOffsetPixels the y offset of anchor in pixels
     * @param xOffsetPixels the x offset of anchor in pixels
     * @return the {@link SignHere} object
     */
    static SignHere createSignHere(String anchorString, int yOffsetPixels, int xOffsetPixels) {
        SignHere signHere = new SignHere();
        signHere.setAnchorString(anchorString);
        signHere.setAnchorUnits("pixels");
        signHere.setAnchorYOffset(String.valueOf(yOffsetPixels));
        signHere.setAnchorXOffset(String.valueOf(xOffsetPixels));
        return signHere;
    }

    /**
     * Create Tabs object containing a single SignHere (see {@link SignHere})
     * field (also known as tabs) on the documents using anchor (autoPlace) positioning.
     * @param anchorString the anchor string; the DocuSign platform searches
     * throughout your envelope's documents for matching anchor strings
     * @param yOffsetPixels the y offset of anchor in pixels
     * @param xOffsetPixels the x offset of anchor in pixels
     * @return the {@link Tabs} object containing single SignHere object
     */
    public static Tabs createSingleSignerTab(String anchorString, int yOffsetPixels, int xOffsetPixels) {
        SignHere signHere = createSignHere(anchorString, yOffsetPixels, xOffsetPixels);
        return createSignerTabs(signHere);
    }

    /**
     * Creates {@link SignHere} fields (also known as tabs) on the document.
     * @param signs the array of SignHere (see {@link SignHere})
     * @return the {@link Tabs} object containing passed SignHere objects
     */
    static Tabs createSignerTabs(SignHere... signs) {
        Tabs signerTabs = new Tabs();
        signerTabs.setSignHereTabs(Arrays.asList(signs));
        return signerTabs;
    }

    /**
     * Creates a {@link Recipients} object and add signer and cc to it.
     * @param signer the signer object
     * @return the {@link Recipients} object
     */
    static Recipients createRecipients(Signer[] signer) {
        Recipients recipients = new Recipients();
        recipients.setSigners(Arrays.asList(signer));
        return recipients;
    }
}
