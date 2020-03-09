package test.powerlog.mobile.springboot.service.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;
import test.powerlog.mobile.springboot.domain.table.LogTb;
import test.powerlog.mobile.springboot.domain.table.LogTbRepository;
import test.powerlog.mobile.springboot.domain.table.WorkoutTb;
import test.powerlog.mobile.springboot.domain.table.WorkoutTbRepository;
import test.powerlog.mobile.springboot.web.dto.kiosk.request.ReqKioskMeasureDto;
import test.powerlog.mobile.springboot.web.dto.kiosk.request.ReqKioskWorkoutDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SaveMeasureService {
    @Autowired
    private LogTbRepository logTbRepository;

    public LogTb ToSaveRecordForm(ReqKioskMeasureDto reqKioskMeasureDto){
        System.out.println("!!!!!23123");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        LogTb logTb = LogTb.builder()
                .lgEmail(reqKioskMeasureDto.getEmail())
                .lgDateTime(localDateTime)
                .lgCommonCode(reqKioskMeasureDto.getCommonCode())
                .lgAvg(reqKioskMeasureDto.getAverage())
                .lgDevice(reqKioskMeasureDto.getDevice())
                .lgMax(reqKioskMeasureDto.getMax())
                .lgMin(reqKioskMeasureDto.getMin())
                .lgStart(reqKioskMeasureDto.getStart())
                .lgDate(localDate)
                .build();

        return logTb;
    }

    public void SaveRecord(LogTb logTb){
        System.out.println("123123123123");
        logTbRepository.save(logTb);
    }

}
