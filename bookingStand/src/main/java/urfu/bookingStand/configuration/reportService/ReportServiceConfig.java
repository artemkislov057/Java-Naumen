package urfu.bookingStand.configuration.reportService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportServiceConfig {
    @Value("${reportService.filesPath}")
    private String filesPath;

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }
}
