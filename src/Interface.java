
import java.util.*;

interface CodeSet {

    void codeset(Person person);
}

interface DateManage {
    void cusmanage(List<PrintDate> list);

    void codemanage(List<PrintDate> list);

    void goodmanage(List<PrintDate> list);
}

interface PrintDate {

     String print();
}