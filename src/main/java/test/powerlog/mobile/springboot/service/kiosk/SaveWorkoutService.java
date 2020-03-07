package test.powerlog.mobile.springboot.service.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.table.WorkoutTb;
import test.powerlog.mobile.springboot.domain.table.WorkoutTbRepository;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.domain.view.WorkoutCodeVwRepository;
import test.powerlog.mobile.springboot.web.dto.kiosk.request.ReqKioskWorkoutDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
public class SaveWorkoutService {
    @Autowired
    private WorkoutTbRepository workoutTbRepository;

    public WorkoutTb ToSaveRecordForm(ReqKioskWorkoutDto reqKioskWorkoutDto){
        System.out.println("!!!!!23123");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        WorkoutTb workoutTb = WorkoutTb.builder()
                .wEmail(reqKioskWorkoutDto.getEmail())
                .wDateTime(localDateTime)
                .wCommonCode(reqKioskWorkoutDto.getCommonCode())
                .wWeight(reqKioskWorkoutDto.getWeight())
                .wCount(reqKioskWorkoutDto.getCount())
                .wSet(reqKioskWorkoutDto.getSet())
                .wLevel(reqKioskWorkoutDto.getLevel())
                .wRest(reqKioskWorkoutDto.getRest())
                .wDevice(reqKioskWorkoutDto.getDevice())
                .wDate(localDate)
                .build();

        return workoutTb;
    }

    public void SaveRecord(WorkoutTb workoutTb){
        System.out.println("123123123123");
        workoutTbRepository.save(workoutTb);
    }

}
