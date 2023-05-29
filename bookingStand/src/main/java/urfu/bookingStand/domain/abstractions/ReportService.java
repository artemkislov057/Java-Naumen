package urfu.bookingStand.domain.abstractions;

import java.util.Date;
import java.util.UUID;

public interface ReportService {
    void getOrCreateReport(UUID teamId, Date date);
}
