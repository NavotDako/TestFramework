package selenium.ex;

/*import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;*/
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;

import java.io.*;
import java.util.List;


public class compare {

    public static void main(String[] args) throws IOException, SAXException {
        FileInputStream fis1 = new FileInputStream("C:\\Users\\DELL\\Desktop\\testP\\testP\\xmls\\output1552907838594.xml");
        FileInputStream fis2 = new FileInputStream("C:\\Users\\DELL\\Desktop\\testP\\testP\\xmls\\output1552907954426.xml");

        Diff xmlDiff = DiffBuilder.compare(Input.from(fis1)).withTest(Input.from(fis2)).ignoreWhitespace().build();
        printDifferences(xmlDiff.getDifferences());



    }

    public static void printDifferences(Iterable<Difference> differences) {

        for (Difference difference :  differences) {
            System.out.println(difference);
        }
    }
}
