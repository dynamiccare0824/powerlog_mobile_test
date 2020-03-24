package test.powerlog.mobile.springboot.service.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.table.*;
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
    @Autowired
    private PlannerByDayTbRepository2 plannerByDayTbRepository2;
    @Autowired
    private PlannerByProgramTbRepository2 plannerByProgramTbRepository2;

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

        int index = Integer.parseInt(reqKioskWorkoutDto.getIndex().split(" ")[0]);
        String email = reqKioskWorkoutDto.getIndex().split(" ")[1];
        String isProgram = reqKioskWorkoutDto.getIndex().split(" ")[2];
        System.out.println(index);
        System.out.println(email);
        System.out.println(isProgram
        );
        System.out.println(reqKioskWorkoutDto.getOnSchedule());

        if(!reqKioskWorkoutDto.getIsProgram() && reqKioskWorkoutDto.getOnSchedule()){
            Optional<PlannerByDayTb> record = plannerByDayTbRepository2.findById(index);
            PlannerByDayTb plannerByDayTb = PlannerByDayTb.builder()
                    .index(index)
                    .email(record.get().getPdEmail())
                    .date(record.get().getPdDate())
                    .dayOfWk(record.get().getPdDayOfWk())
                    .commonCode(record.get().getPdCommonCode())
                    .weight(record.get().getPdWeight())
                    .count(record.get().getPdCount())
                    .set(record.get().getPdSet())
                    .level(record.get().getPdLevel())
                    .rest(record.get().getPdRest())
                    .program(record.get().getPdProgram())
                    .onSchedule(record.get().getPdOnSchedule())
                    .done("true")
                    .created(record.get().getPdCreated())
                    .updated(localDateTime)
                    .build();
            plannerByDayTbRepository2.save(plannerByDayTb);
        }
        else if(reqKioskWorkoutDto.getIsProgram() && reqKioskWorkoutDto.getOnSchedule()){
            Optional<PlannerByProgramTb> record = plannerByProgramTbRepository2.findById(index);
            PlannerByProgramTb plannerByProgramTb = PlannerByProgramTb.builder()
                    .index(index)
                    .email(record.get().getPlnEmail())
                    .startDate(record.get().getPlnStartDate())
                    .endDate(record.get().getPlnEndDate())
                    .onDate(record.get().getPlnOnDate())
                    .onDay(record.get().getPlnOnDay())
                    .chosenDayOfWk(record.get().getPlnChosenDayOfWk())
                    .commonCode(record.get().getPlnCommonCode())
                    .weight(record.get().getPlnWeight())
                    .count(record.get().getPlnCount())
                    .set(record.get().getPlnSet())
                    .level(record.get().getPlnLevel())
                    .rest(record.get().getPlnRest())
                    .program(record.get().getPlnProgram())
                    .done("true")
                    .onSchedule(record.get().getPlnOnSchedule())
                    .created(record.get().getPlnCreated())
                    .updated(localDateTime)
                    .build();
            plannerByProgramTbRepository2.save(plannerByProgramTb);
        }
        // 새로 만든 플랜일 경우
        else{
            PlannerByDayTb plannerByDayTb = PlannerByDayTb.builder()
                    .email(reqKioskWorkoutDto.getEmail())
                    .date(localDate)
                    .dayOfWk(Integer.toString(localDate.getDayOfWeek().getValue()))
                    .commonCode(reqKioskWorkoutDto.getCommonCode())
                    .weight(reqKioskWorkoutDto.getWeight())
                    .count(reqKioskWorkoutDto.getCount())
                    .set(reqKioskWorkoutDto.getSet())
                    .level(reqKioskWorkoutDto.getLevel())
                    .rest(reqKioskWorkoutDto.getRest())
                    .program("false")
                    .onSchedule("false")
                    .done("true")
                    .created(localDateTime)
                    .updated(localDateTime)
                    .build();
            plannerByDayTbRepository2.save(plannerByDayTb);
        }

        return workoutTb;
    }

    public void SaveRecord(WorkoutTb workoutTb){
        System.out.println("123123123123");
        workoutTbRepository.save(workoutTb);
    }

}