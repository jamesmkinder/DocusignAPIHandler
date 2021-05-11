public class RequestFactory{

    public void requestEmail() {
        if (AppConfiguration.getAppName().equals("WeldProcessReport")){
            EmailRequest weldProcessReportRequest = new WeldProcessReportRequest();
            weldProcessReportRequest.requestEmail();
        }
    }
}
