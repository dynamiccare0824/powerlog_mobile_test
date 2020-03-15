package test.powerlog.mobile.springboot.service.mobile.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.old.ProgramTypeVwRepository;
import test.powerlog.mobile.springboot.domain.table.*;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.mobile.account.NumberGenService;
import test.powerlog.mobile.springboot.service.mobile.account.SendEmailService;
import test.powerlog.mobile.springboot.service.mobile.account.SignUpService;
import test.powerlog.mobile.springboot.web.dto.mobile.request.planner.ReqByDaySaveDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.planner.ReqCheckProgramDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.planner.ReqProgramGenerateDto;

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
            double weightRate = 0;
            int count = 0;
            int set = 0;
            int rest = 0;
            double defaultvalue = 1;

            switch (bodyShape) {
                case "A":
                    weightRate = 0.75;
                    count = 8;
                    set = 3;
                    rest = 60;
                case "B":
                    weightRate = 0.65;
                    count = 10;
                    set = 4;
                    rest = 60;
                case "C":
                    weightRate = 0.55;
                    count = 15;
                    set = 5;
                    rest = 60;
            }
            if (Integer.parseInt(careerNow) < 4) {
                defaultvalue = 0.3;
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

    public HashMap<String, Object> getProgramDetail(ReqProgramGenerateDto reqProgramGenerateDto) throws ParseException {
        String email = reqProgramGenerateDto.getEmail();
        //이 사람의 email로 해당 최근 측정 기록이 있는지 조회한다
        Optional<List<LogLateMsrVw>> logRecord = Optional.ofNullable(logLateMsrVwRepository.findAllByLgLateMsrVwEmail(email));
        HashMap<String, Object> presentMap = new HashMap<>();
        //logLateMsr뷰에 기록이 하나라도 존재한다면
        System.out.println(logRecord);
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
                        .weight((int) (integerMaxList.get(i) * weightRate))
                        .count(count)
                        .set(set)
                        .level(0)
                        .rest(rest)
                        .program("true")
                        .done("false")
                        .onSchedule("false")
                        .created(localDateTime)
                        .updated(localDateTime)
                        .build();

                listReg.add(plannerByProgramTb);
            }
            plannerByProgramTbRepository.saveAll(listReg);
        }

        //하나를 뽑을 때마다
        //loglatemsrVw에서 최근 운동에 뭐가 있는지를 먼저 찾고
        //그 운동에 대한 max를 찾아서
        //formual의 count, weight, set, rest, level 등을 적용하는데
        //weight의 경우 이 사람이 female, 65이상, careerNow가 3 이하면 초보자로 구분하여 0.8을 곱한다


        else {
            //에러
            //없다면..! 일괄 없는 것으로 종료.. 애초에 알고리즘 실행하지도 않아
        }
        return null;
    }

    public HashMap<String, Object> getProgramCheck(ReqCheckProgramDto reqCheckProgramDto, HashMap<String, Object> resultMap) throws ParseException
    {
        //이거 나중에 뷰로 바꿔야 하나?
        String email = reqCheckProgramDto.getEmail();
        List<PlannerByProgramTb> record = plannerByProgramTbRepository.findAllByPlnEmail(email);
        if(!record.isEmpty()){
            resultMap.replace("isPresent", true);
            resultMap.replace("message", "generated program already exists");
            return resultMap;
        }
        resultMap.replace("isPresent", true);
        resultMap.replace("message", "generated program doesn't exists");
        return resultMap;
    }

    public HashMap<String, Object> SaveByDay(ReqByDaySaveDto reqByDaySaveDto, HashMap<String, Object> resultMap) throws ParseException
    {
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

        try{
            plannerByDayTbRepository.save(plannerByDayTb);
            resultMap.replace("isDone", true);
            resultMap.replace("isError", false);
            resultMap.replace("message", "schedule has been registered succesfully");
        }
        catch(Exception ex){
            resultMap.replace("isDone", false);
            resultMap.replace("isError", true);
            resultMap.replace("message", "schedule has been registered succesfully");
        }
        return resultMap;
    }

    public HashMap<String, Object> getByDayDetail(ReqProgramGenerateDto reqProgramGenerateDto) throws ParseException
    {
        return null;
    }
}

