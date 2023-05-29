package urfu.bookingStand.domain.services;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.Booking;
import urfu.bookingStand.database.entities.Stand;
import urfu.bookingStand.database.repositories.BookingRepository;
import urfu.bookingStand.database.repositories.StandRepository;
import urfu.bookingStand.domain.abstractions.ReportService;
import urfu.bookingStand.domain.models.report.TeamStandReportModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class ReportServiceImpl implements ReportService {

    private final StandRepository standRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public ReportServiceImpl(StandRepository standRepository,
                             BookingRepository bookingRepository) {

        this.standRepository = standRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void getOrCreateReport(UUID teamId, Date date) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        var stands = standRepository.getByTeamId(teamId);
        for (Stand stand : stands) {
            createReportForStand(stand, date, workbook);
        }

        try (FileOutputStream out = new FileOutputStream(new File("E:\\booking\\report.xls"))) {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createReportForStand(Stand stand, Date date, HSSFWorkbook workbook) {
        var sheet = workbook.createSheet(stand.getName() + "(" + stand.getId() + ")");
        var startDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var endDateTime = date.toInstant().atZone(ZoneId.systemDefault())
                .plusHours(23)
                .plusMinutes(59)
                .plusMinutes(59)
                .toLocalDateTime();
        var bookings = bookingRepository.findAllBookingsBetween(stand.getId(), startDateTime, endDateTime);
        List<TeamStandReportModel> data = new ArrayList<>();
        for (Booking booking : bookings) {
            var dataRow = new TeamStandReportModel();
            dataRow.setStartDate(booking.getStartTime());
            dataRow.setEndDate(booking.getEndTime());
            dataRow.setUsername(booking.getUser().getShortname());
            data.add(dataRow);
        }

        int rowNum = 0;

        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Начало брони");
        row.createCell(1).setCellValue("Конец брони");
        row.createCell(2).setCellValue("Имя занявшего");

        for (TeamStandReportModel dataModel : data) {
            createSheetHeader(sheet, ++rowNum, dataModel);
        }
    }

    private static void createSheetHeader(HSSFSheet sheet, int rowNum, TeamStandReportModel dataModel) {
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(dataModel.getStartDate().toString());
        row.createCell(1).setCellValue(dataModel.getEndDate().toString());
        row.createCell(2).setCellValue(dataModel.getUsername());
    }

}
