package test.powerlog.mobile.springboot.service.mobile.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.old.NewPlannerVw;
import test.powerlog.mobile.springboot.domain.old.NewPlannerVwRepository;
import test.powerlog.mobile.springboot.domain.old.ProgramTypeVwRepository;
import test.powerlog.mobile.springboot.domain.table.*;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.mobile.account.NumberGenService;
import test.powerlog.mobile.springboot.service.mobile.account.SendEmailService;
import test.powerlog.mobile.springboot.service.mobile.account.SignUpService;
import test.powerlog.mobile.springboot.web.dto.mobile.request.planner.*;

import javax.print.DocFlavor;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PlannerService {

    @Autowired
    private UserTbRepository userTbRepository;
    @Autowired
    private UserAccountVwRepository userAccountVwRepository;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private NumberGenService numberGenService;
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private LogLateMsrVwRepository logLateMsrVwRepository;
    @Autowired
    private ProgramTypeVwRepository programTypeVwRepository;
    @Autowired
    private PlannerByProgramTbRepository plannerByProgramTbRepository;
    @Autowired
    private PlannerByDayTbRepository plannerByDayTbRepository;
    @Autowired
    private PlannerByProgramTbRepository2 plannerByProgramTbRepository2;
    @Autowired
    private PlannerByDayTbRepository2 plannerByDayTbRepository2;
    @Autowired
    private NewPlannerVwRepository newPlannerVwRepository;

    public ArrayList<ArrayList<String>> getGroupList(String numberPerWk) {
        switch (numberPerWk) {
            case "2":
                ArrayList<String> mChildListTwoOne = new ArrayList<>(Arrays.asList("A01", "C01", "F01", "H01"));
                ArrayList<String> mChildListTwoTwo = new ArrayList<>(Arrays.asList("B01", "D01", "E01", "G01"));
                ArrayList<ArrayList<String>> groupListTwo = new ArrayList<>(Arrays.asList(mChildListTwoOne, mChildListTwoTwo));
                return groupListTwo;
            case "3":
                ArrayList<String> mChildListThreeOne = new ArrayList<>(Arrays.asList("A01", "C01", "H01"));
                ArrayList<String> mChildListThreeTwo = new ArrayList<>(Arrays.asList("B01", "D01", "E01"));
                ArrayList<String> mChildListThreeThree = new ArrayList<>(Arrays.asList("F01", "G01"));
                ArrayList<ArrayList<String>> groupListThree = new ArrayList<>(Arrays.asList(mChildListThreeOne, mChildListThreeTwo, mChildListThreeThree));
                return groupListThree;
            case "4":
                ArrayList<String> mChildListFourOne = new ArrayList<>(Arrays.asList("A01"));
                ArrayList<String> mChildListFourTwo = new ArrayList<>(Arrays.asList("C01", "F01", "H01"));
                ArrayList<String> mChildListFourThree = new ArrayList<>(Arrays.asList("D01", "G01"));
                ArrayList<String> mChildListFourFour = new ArrayList<>(Arrays.asList("B01", "E01"));
                ArrayList<ArrayList<String>> groupListFour = new ArrayList<>(Arrays.asList(mChildListFourOne, mChildListFourTwo, mChildListFourThree, mChildListFourFour));
                return groupListFour;
            case "5":
                ArrayList<String> mChildListFiveOne = new ArrayList<>(Arrays.asList("A01"));
                ArrayList<String> mChildListFiveTwo = new ArrayList<>(Arrays.asList("C01", "H01"));
                ArrayList<String> mChildListFiveThree = new ArrayList<>(Arrays.asList("D01"));
                ArrayList<String> mChildListFiveFour = new ArrayList<>(Arrays.asList("B01", "E01"));
                ArrayList<String> mChildListFiveFive = new ArrayList<>(Arrays.asList("F01", "G01"));
                ArrayList<ArrayList<String>> groupListFive = new ArrayList<>(Arrays.asList(mChildListFiveOne, mChildListFiveTwo, mChildListFiveThree, mChildListFiveFour, mChildListFiveFive));
                return groupListFive;
        }
        return null;
    }

    public HashMap<String, Object> getFormula(String email) {
        HashMap<String, Object> tmpMap = new HashMap<>();
        Optional<UserAccountVw> userRecord = userAccountVwRepository.findById(email);
        if (userRecord.isPresent()) {
            String bodyShape = userRecord.get().getLoginVwShapeCode();
            String careerNow = userRecord.get().getLoginVwCareerNow();
            String vwAge = userRecord.get().getLoginVwAge();
            double weightRate = 0;
            double defaultvalue = 0;
            int count = 0;
            int set = 0;
            int rest = 0;

            switch (bodyShape) {
                case "A":
                    weightRate = 0.75;
                    count = 8;
                    set = 3;
                    rest = 60;
                    break;
                case "B":
                    weightRate = 0.65;
                    count = 10;
                    set = 4;
                    rest = 60;
                    break;
                case "C":
                    weightRate = 0.45;
                    count = 15;
                    set = 5;
                    rest = 60;
                    break;
            }

            if (Integer.parseInt(careerNow) < 4 || userRecord.get().getLoginVwGender().equals("female") || Integer.parseInt(vwAge) > 50) {
                defaultvalue = 0.2;
            }

            tmpMap.put("weightRate", weightRate);
            tmpMap.put("count", count);
            tmpMap.put("set", set);
            tmpMap.put("rest", rest);
            tmpMap.put("defaultValue", defaultvalue);

            return tmpMap;
        }
        return null;
    }

    public HashMap<String, Object> saveProgramDetail(ReqProgramGenerateDto reqProgramGenerateDto, HashMap<String, Object> commonMap) throws ParseException {
        String email = reqProgramGenerateDto.getEmail();
        //이 사람의 email로 해당 최근 측정 기록이 있는지 조회한다
        Optional<List<LogLateMsrVw>> logRecord = Optional.ofNullable(logLateMsrVwRepository.findAllByLgLateMsrVwEmail(email));
        HashMap<String, Object> presentMap = new HashMap<>();
        //logLateMsr뷰에 기록이 하나라도 존재한다면
        System.out.println(logRecord);
        try{
            if (!logRecord.get().isEmpty()) {
                //이터레이터를 만들고
                for (int i = 0; i < logRecord.get().size(); i++) {
                    if (logRecord.get().get(i).getLgLateMsrVwCommonCode().contains("01")) {
                        presentMap.put(logRecord.get().get(i).getLgLateMsrVwCommonCode(), logRecord.get().get(i).getLgLateMsrVwMax());
                    }
                }

                //받은 요일을 int 배열로 바꾼다.
                String[] originalList = reqProgramGenerateDto.getDayOfWk().split(",");
                ArrayList<Integer> tmpDateList = new ArrayList<>();
                for (int i = 0; i < originalList.length; i++) {
                    int tmp = Integer.parseInt(originalList[i]);
                    tmpDateList.add(tmp);
                }

                //받은 요일과 실제 달력 날짜를 매칭하기 위함
                Queue<String> dateQueue = new LinkedList<>();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
                Date startDate = transFormat.parse(reqProgramGenerateDto.getStartDate());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);

                while (dateQueue.size() < 50) {
                    int dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
                    for (int object : tmpDateList) {
                        System.out.println(object);
                        if (object == dayNumber) {
                            System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
                            Date d = new Date(calendar.getTimeInMillis());
                            String tmpDate = transFormat.format(d);
                            dateQueue.offer(tmpDate);
                        }
                    }
                    calendar.add(calendar.DATE, 1);
                }

                //며칠 운동하고 싶은지를 통해서 프로그램 생성
                ArrayList<ArrayList<String>> groupList = getGroupList(reqProgramGenerateDto.getNumberPerWk());

                Queue<ArrayList> programQueue = new LinkedList<>();
                for (int i = 0; i < 10; i++) {
                    for (ArrayList<String> object : groupList) {
                        programQueue.offer(object);
                    }
                }

                ArrayList<String> commonCodeList = new ArrayList<>();
                ArrayList<Integer> integerMaxList = new ArrayList<>();
                ArrayList<String> stringDateList = new ArrayList<>();
                // 큐에 넣은 운동들이 하나씩 없어질 때까지
                while (!programQueue.isEmpty()) {
                    ArrayList<String> Templist = programQueue.poll();
                    String tmpDate = dateQueue.poll();
                    for (String object : Templist) {
                        if (presentMap.containsKey(object)) {
                            commonCodeList.add(object);
                            integerMaxList.add((Integer) presentMap.get(object));
                            stringDateList.add(tmpDate);
                        }

                    }
                    // 다 돌면 1일치 운동 들어가야하는 게 나오는 거임
                }
                System.out.println(commonCodeList);
                System.out.println(integerMaxList);
                System.out.println(stringDateList);

                HashMap formulaMap = getFormula(email);
                double defaultValue = (double) formulaMap.get("defaultValue");
                double weightRate = (double) formulaMap.get("weightRate");
                int count = (int) formulaMap.get("count");
                int set = (int) formulaMap.get("set");
                int rest = (int) formulaMap.get("rest");

                SimpleDateFormat transFormatReg = new SimpleDateFormat("yyyyMMdd");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDate endDateReg = LocalDate.parse((stringDateList.get(stringDateList.size() - 1)), formatter);
                LocalDate startDateReg = LocalDate.parse(reqProgramGenerateDto.getStartDate(), formatter);
                LocalDateTime localDateTime = LocalDateTime.now();
                ArrayList<PlannerByProgramTb> listReg = new ArrayList<>();
                for (int i = 0; i < commonCodeList.size(); i++) {
                    LocalDate dateReg = LocalDate.parse(stringDateList.get(i), formatter);
                    Date onDate = transFormatReg.parse(stringDateList.get(i));
                    Calendar calendarReg = Calendar.getInstance();
                    calendarReg.setTime(onDate);

                    PlannerByProgramTb plannerByProgramTb = PlannerByProgramTb.builder()
                            .email(email)
                            .startDate(startDateReg)
                            .endDate(endDateReg)
                            .onDate(dateReg)
                            .onDay(Integer.toString(calendarReg.get(Calendar.DAY_OF_WEEK)))
                            .chosenDayOfWk(reqProgramGenerateDto.getDayOfWk())
                            .commonCode(commonCodeList.get(i))
                            .weight((int) (integerMaxList.get(i) * weightRate * (1 - defaultValue)))
                            .count(count)
                            .set(set)
                            .level(0)
                            .rest(rest)
                            .program("true")
                            .done("false")
                            .onSchedule("true")
                            .created(localDateTime)
                            .updated(localDateTime)
                            .build();

                    listReg.add(plannerByProgramTb);
                }
                plannerByProgramTbRepository.saveAll(listReg);
            }
            commonMap.replace("isError", false);
            commonMap.replace("message", null);
        }
        catch(Exception ex){
            commonMap.replace("isError", true);
            commonMap.replace("message", ex.toString());
        }
               //하나를 뽑을 때마다
        //loglatemsrVw에서 최근 운동에 뭐가 있는지를 먼저 찾고
        //그 운동에 대한 max를 찾아서
        //formual의 count, weight, set, rest, level 등을 적용하는데
        //weight의 경우 이 사람이 female, 65이상, careerNow가 3 이하면 초보자로 구분하여 0.8을 곱한다
        return commonMap;
    }

    public HashMap<String, Object> getProgramCheck(ReqCheckProgramDto reqCheckProgramDto, HashMap<String, Object> resultMap) throws ParseException {
        //이거 나중에 뷰로 바꿔야 하나?
        String email = reqCheckProgramDto.getEmail();
        List<PlannerByProgramTb> record = plannerByProgramTbRepository.findAllByPlnEmail(email);
        if (!record.isEmpty()) {
            resultMap.replace("isPresent", true);
            resultMap.replace("message", "generated program already exists");
            return resultMap;
        }
        resultMap.replace("isPresent", false);
        resultMap.replace("message", "generated program doesn't exists");
        return resultMap;
    }

    public HashMap<String, Object> SaveByDay(ReqByDaySaveDto reqByDaySaveDto, HashMap<String, Object> resultMap) throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        Date onDate = transFormat.parse(reqByDaySaveDto.getDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(onDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(reqByDaySaveDto.getDate(), formatter);
        LocalDateTime localDateTime = LocalDateTime.now();

        PlannerByDayTb plannerByDayTb = PlannerByDayTb.builder()
                .email(reqByDaySaveDto.getEmail())
                .date(localDate)
                .dayOfWk(Integer.toString(calendar.DAY_OF_WEEK))
                .commonCode(reqByDaySaveDto.getCommonCode())
                .weight(reqByDaySaveDto.getWeight())
                .count(reqByDaySaveDto.getCount())
                .set(reqByDaySaveDto.getSet())
                .level(reqByDaySaveDto.getLevel())
                .rest(reqByDaySaveDto.getRest())
                .program("false")
                .done("false")
                .onSchedule("true")
                .created(localDateTime)
                .updated(localDateTime).build();

        try {
            plannerByDayTbRepository.save(plannerByDayTb);
            resultMap.replace("isDone", true);
            resultMap.replace("isError", false);
            resultMap.replace("message", "schedule has been registered succesfully");
        } catch (Exception ex) {
            resultMap.replace("isDone", false);
            resultMap.replace("isError", true);
            resultMap.replace("message", "schedule has been registered succesfully");
        }
        return resultMap;
    }

    public HashMap<String, Object> getCalendarList(String year, String month) {


        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //년월일 표시
        Calendar cal = Calendar.getInstance();


        cal.set(Integer.parseInt(year), Integer.parseInt(month), 1); //종료 날짜 셋팅
        String endDate = dateFormat.format(cal.getTime());
        cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1); //시작 날짜 셋팅
        String startDate = dateFormat.format(cal.getTime());


        int i = 0;
        HashMap<String, Object> tmpMap = new HashMap<>();

        while (!startDate.equals(endDate)) { //다르다면 실행, 동일 하다면 빠져나감
            ArrayList<HashMap<String, Object>> tmpList = new ArrayList<>();
            tmpMap.put(startDate, tmpList);
            cal.add(Calendar.DATE, 1); //1일 더해줌
            startDate = dateFormat.format(cal.getTime()); //비교를 위한 값 셋팅
//            //+1달 출력
//            System.out.println(dateFormat.format(cal.getTime()));
            i++;
        }
        return tmpMap;
    }

    public HashMap<String, Object> getPlannerMain(ReqPlannerMainDto reqPlannerMainDto, HashMap<String, Object> resultMap) {
        String email = reqPlannerMainDto.getEmail();
        List<NewPlannerVw> record = newPlannerVwRepository.findAllByPlnVwEmail(email);

        if (!record.isEmpty()) {
            HashMap<String, Object> dateMap = getCalendarList(reqPlannerMainDto.getYear(), reqPlannerMainDto.getMonth());
            for (int i = 0; i < record.size(); i++) {
                HashMap<String, Object> recordMap = new HashMap<>();
                recordMap.put("plnVwCommonCode", record.get(i).getPlnVwCommonCode());
                recordMap.put("plnVwStartDate", record.get(i).getPlnVwStartDate());
                recordMap.put("plnVwEndDate", record.get(i).getPlnVwEndDate());
                recordMap.put("plnVwOnDate", record.get(i).getPlnVwOnDate());
                recordMap.put("plnVwOnDay", record.get(i).getPlnVwOnDay());
                recordMap.put("plnVwChosenDayOfWk", record.get(i).getPlnVwChosenDayOfWk());
                recordMap.put("plnVwWeight", record.get(i).getPlnVwWeight());
                recordMap.put("plnVwCount", record.get(i).getPlnVwCount());
                recordMap.put("plnVwSet", record.get(i).getPlnVwSet());
                recordMap.put("plnVwLevel", record.get(i).getPlnVwLevel());
                recordMap.put("plnVwRest", record.get(i).getPlnVwRest());
                recordMap.put("plnVwIsProgram", record.get(i).getPlnVwIsProgram());
                recordMap.put("plnVwOnSchedule", record.get(i).getPlnVwOnSchedule());
                recordMap.put("plnVwIsDone", record.get(i).getPlnVwIsDone());
                recordMap.put("plnVwCreatedTime", record.get(i).getPlnVwCreatedTime());
                recordMap.put("plnVwUpdatedTime", record.get(i).getPlnVwUpdatedTime());
                recordMap.put("plnVwId", record.get(i).getPlnVwId());
                int recordMonth = record.get(i).getPlnVwOnDate().getMonthValue();
                int recordYear = record.get(i).getPlnVwOnDate().getYear();
                if (recordMonth == Integer.parseInt(reqPlannerMainDto.getMonth())
                        && recordYear == Integer.parseInt(reqPlannerMainDto.getYear())) {
                    ArrayList tmpList = (ArrayList) dateMap.get(record.get(i).getPlnVwOnDate().toString());
                    tmpList.add(recordMap);
                    dateMap.replace(record.get(i).getPlnVwOnDate().toString(), tmpList);
                }
                // 출석 수 센다
//                if(record.get(i).getPlnVwIsDone().equals("true")){
//                    attendanceMap.put(record.get(i).getPlnVwOnDate().toString(), true);
//                }
//                else{
//                    attendanceMap.put(record.get(i).getPlnVwOnDate().toString(), true);
//                }
            }
            int attendance = 0;
            int programTotal = 0;
            int programDone = 0;
            for (String key : dateMap.keySet()) {
                Boolean tmpAttendance = false;
                ArrayList<HashMap<String, Object>> tmpList = new ArrayList<>();
                if(dateMap.get(key)!=null){
                    HashMap<String, Object> tmpMap = new HashMap<>();
                    tmpList =(ArrayList<HashMap<String, Object>>) dateMap.get(key);
                    for(int j=0; j<tmpList.size(); j++){
                        tmpMap = tmpList.get(j);
                        if((tmpMap.get("plnVwIsDone").equals("true"))){
                            tmpAttendance = true;
                        }
                        if((tmpMap.get("plnVwIsProgram").equals("true"))){
                            programTotal = programTotal + 1;
                            if((tmpMap.get("plnVwIsDone").equals("true"))){
                                programDone = programDone + 1;
                            }
                        }
                    }
                    if(tmpAttendance){
                        attendance = attendance + 1;
                    }
                }
                ArrayList<HashMap<String, Object>> emptyList = new ArrayList<>();
                if (dateMap.get(key).equals(emptyList)) {
                    dateMap.replace(key, null);
                }
            }
            // 달성률 만든다.
//            HashMap<String, Object> dateMap2 = dateMap;
//            for (String key : dateMap2.keySet()) {
//                dateMap2.replace(key, false);
//            }
            double acheivementRate = ((double) programDone / (double) programTotal) * 100;
            resultMap.put("attendance", attendance);
            resultMap.put("acheivementRate", acheivementRate);
            resultMap.replace("resultData", dateMap);
            resultMap.replace("isError", false);
        } else {
            resultMap.put("attendance", null);
            resultMap.put("acheivementRate", null);
            resultMap.replace("isError", true);
            resultMap.replace("message", "no registered data found");
        }
        System.out.println(resultMap);
        return resultMap;
    }

    public HashMap<String, Object> getProgramDetail(ReqProgramGenerateDto reqProgramGenerateDto, HashMap<String, Object> resultMap) {
        String email = reqProgramGenerateDto.getEmail();
        List<NewPlannerVw> record = newPlannerVwRepository.findAllByPlnVwEmail(email);
        int totalCount = 0;
        int doneCount = 0;

        if (!record.isEmpty()) {
//            HashMap<String, Object> dateMap = getCalendarList(reqPlannerMainDto.getYear(), reqPlannerMainDto.getMonth());
            HashMap<String, Object> dateMap = new HashMap<>();
            for (int k = 0; k < record.size(); k++) {
                ArrayList<HashMap<String, Object>> emptyList = new ArrayList<>();
                dateMap.put(record.get(k).getPlnVwOnDate().toString(), emptyList);
            }
            // string : 빈 리스트 만들어주었음
            ArrayList<String> codeNull = new ArrayList<>(Arrays.asList("A01", "B01", "C01", "D01", "E01", "F01", "G01", "H01"));
            for (int i = 0; i < record.size(); i++) {
                totalCount = totalCount + 1;
                HashMap<String, Object> recordMap = new HashMap<>();
                String isProgram = record.get(i).getPlnVwIsProgram();
                String isDone = record.get(i).getPlnVwIsDone();
                if (isProgram.equals("true") && isDone.equals("false")) {
                    recordMap.put("plnVwCommonCode", record.get(i).getPlnVwCommonCode());
                    if(codeNull.contains(record.get(i).getPlnVwCommonCode())){
                        codeNull.remove(record.get(i).getPlnVwCommonCode());
                    }
                    recordMap.put("plnVwStartDate", record.get(i).getPlnVwStartDate());
                    recordMap.put("plnVwEndDate", record.get(i).getPlnVwEndDate());
                    recordMap.put("plnVwOnDate", record.get(i).getPlnVwOnDate());
                    recordMap.put("plnVwOnDay", record.get(i).getPlnVwOnDay());
                    recordMap.put("plnVwChosenDayOfWk", record.get(i).getPlnVwChosenDayOfWk());
                    recordMap.put("plnVwWeight", record.get(i).getPlnVwWeight());
                    recordMap.put("plnVwCount", record.get(i).getPlnVwCount());
                    recordMap.put("plnVwSet", record.get(i).getPlnVwSet());
                    recordMap.put("plnVwLevel", record.get(i).getPlnVwLevel());
                    recordMap.put("plnVwRest", record.get(i).getPlnVwRest());
                    recordMap.put("plnVwIsProgram", record.get(i).getPlnVwIsProgram());
                    recordMap.put("plnVwOnSchedule", record.get(i).getPlnVwOnSchedule());
                    recordMap.put("plnIsDone", record.get(i).getPlnVwIsDone());
                    recordMap.put("plnVwCreatedTime", record.get(i).getPlnVwCreatedTime());
                    recordMap.put("plnVwUpdatedTime", record.get(i).getPlnVwUpdatedTime());
                    recordMap.put("plnVwId", record.get(i).getPlnVwId());
                    ArrayList tmpList = (ArrayList) dateMap.get(record.get(i).getPlnVwOnDate().toString());
                    tmpList.add(recordMap);
                    dateMap.replace(record.get(i).getPlnVwOnDate().toString(), tmpList);
                }
                if(record.get(i).getPlnVwIsDone().equals("true")){
                    doneCount = doneCount + 1;
                }
            }
            // 빈 리스트에 추가했음
            int doneRate = doneCount / totalCount;
            String achievementRate = Integer.toString(doneRate);
            String attendance = Integer.toString(doneCount);


            for (String key : dateMap.keySet()) {
                ArrayList<HashMap<String, Object>> tmpList = new ArrayList<>();
                if (dateMap.get(key).equals(tmpList)) {
                    dateMap.replace(key, null);
                }
            }
            //남아있는 빈 리스트에 대해 null로 표시함

            resultMap.put("achievementRate", achievementRate);
            resultMap.put("attendance", attendance);
            resultMap.replace("resultData", dateMap);
            resultMap.replace("isError", false);
            resultMap.put("codeNull", codeNull);

        } else {
            resultMap.put("achievementRate", "0");
            resultMap.put("attendance", "0");
            resultMap.replace("isError", true);
            resultMap.replace("message", "no registered data found");
            resultMap.put("codeNull", null);
        }
        System.out.println(resultMap);
        return resultMap;
    }

    public HashMap<String, Object> DeleteByIndex(ReqDeleteScheduleDto reqDeleteScheduleDto, HashMap<String, Object> resultMap) throws ParseException {
        if (reqDeleteScheduleDto.getIndex() != null) {
            String index = reqDeleteScheduleDto.getIndex();
            String[] splitString = index.split(" ");
            int realIndex = Integer.parseInt(splitString[0]);
//            String email = splitString[1];
            String isProgram = splitString[2];
            try {
                if (isProgram.equals("true")) {
                    plannerByProgramTbRepository2.deleteById(realIndex);
                } else {
                    plannerByDayTbRepository2.deleteById(realIndex);
                }
                resultMap.replace("isDone", true);
                resultMap.replace("isError", false);
                resultMap.replace("message", null);
            } catch (Exception ex) {
                resultMap.replace("isDone", false);
                resultMap.replace("isError", true);
                resultMap.replace("message", ex.toString());
            }

        } else {
            resultMap.replace("isDone", null);
            resultMap.replace("isError", true);
            resultMap.replace("message", "null data received");
        }
        return resultMap;
    }

    public HashMap<String, Object> DeleteProgramByEmail(ReqDeleteProgramDto reqDeleteProgramDto, HashMap<String, Object> resultMap) throws ParseException {
        if (reqDeleteProgramDto.getEmail() != null) {
            String email = reqDeleteProgramDto.getEmail();
            try {
//                plannerByProgramTbRepository.deleteAllByPlnEmail(email);
                plannerByProgramTbRepository.deleteAllByPlnEmailAndPlnDone(email, "false");
                resultMap.replace("isDone", true);
                resultMap.replace("isError", false);
                resultMap.replace("message", null);
            } catch (Exception ex) {
                resultMap.replace("isDone", false);
                resultMap.replace("isError", true);
                resultMap.replace("message", ex.toString());
            }
        } else {
            resultMap.replace("isDone", null);
            resultMap.replace("isError", true);
            resultMap.replace("message", "null data received");
        }
        return resultMap;
    }
}

