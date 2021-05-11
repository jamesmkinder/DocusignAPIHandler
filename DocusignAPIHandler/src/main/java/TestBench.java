public class TestBench {
    //Proof of concept test code.

    public static void main(String[] args) {

        new AppConfiguration("WeldProcessReport", 699, "G:\\codeRepository\\WeldLoggerProProjectSC Weld Reports - -  -  - Geet(699).pdf");
        RequestFactory requestFactory = new RequestFactory();
        requestFactory.requestEmail();
    }
}