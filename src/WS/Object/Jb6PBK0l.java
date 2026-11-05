package WS.Object;

import SOAP.generated.SoapCharacterService;
import SOAP.object.EmployeeY;
import SOAP.object.ObjectService;
import SOAP.object.SoapObjectService;

import java.util.Comparator;
import java.util.List;

public class Jb6PBK0l {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "Jb6PBK0l";

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        List<EmployeeY> data = port.requestListEmployeeY(sCode, qCode);

        data.sort((a, b) -> a.getStartDate().compare(b.getStartDate()));

        port.submitListEmployeeY(sCode, qCode, data);

    }
}
