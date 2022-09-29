public class TestBench {
    //Proof of concept test code.

    public static void main(String[] args) {

        new AppConfiguration("WeldProcessReport", 2042, "G:\\codeRepository\\WeldLoggerProProject\\ReceivingSC Weld Report 162022 - -  -  - asdf 111111 SEQ 1.pdf");
        RequestFactory requestFactory = new RequestFactory();
        requestFactory.requestEmail();
    }
}