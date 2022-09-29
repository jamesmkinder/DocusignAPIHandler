public class TestBench {
    //Proof of concept test code.

    public static void main(String[] args) {

        new AppConfiguration("WeldProcessReport", 2042, "G:\\codeRepository\\WeldLoggerProProject\\Weld Process Reports\\FS Weld Reports - -  -  - (97).pdf");
        RequestFactory requestFactory = new RequestFactory();
        requestFactory.requestEmail();
    }
}