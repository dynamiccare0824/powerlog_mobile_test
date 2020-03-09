package test.powerlog.mobile.springboot.service.mobile.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.table.UserTbRepository;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVwRepository;
import test.powerlog.mobile.springboot.domain.view.ProgramTypeVw;
import test.powerlog.mobile.springboot.domain.view.ProgramTypeVwRepository;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.service.mobile.account.NumberGenService;
import test.powerlog.mobile.springboot.service.mobile.account.SendEmailService;
import test.powerlog.mobile.springboot.service.mobile.account.SignUpService;
import test.powerlog.mobile.springboot.web.dto.mobile.request.planner.ReqProgramGenerateDto;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    public HashMap<String, Object> getProgramDetail(ReqProgramGenerateDto reqProgramGenerateDto) throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        Date startDate = transFormat.parse(reqProgramGenerateDto.getStartDate());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int number = 1;

        //받은 요일을 int 배열로 바꾼다.
        String[] originalList = reqProgramGenerateDto.getDayOfWk().split(",");
        int[] tmpList = new int[originalList.length];
        for (int i = 0; i < originalList.length; i++) {
            if (originalList[i].equals("7")) {
                tmpList[i] = 1;
            } else {
                int tmp = Integer.parseInt(originalList[i]) + 1;
                tmpList[i] = tmp;
            }
        }
        //며칠 운동하고 싶은지를 통해서 프로그램 생성

        if(reqProgramGenerateDto.getNumberPerWk().equals("2")){
            ArrayList<String> mChildListTwoOne = new ArrayList<>(Arrays.asList("A01", "C01", "F01", "H01"));
            ArrayList<String> mChildListTwoTwo = new ArrayList<>(Arrays.asList("B01", "D01", "E01", "G01"));
            ArrayList<ArrayList<String>> mGroupListTwo = new ArrayList<>(Arrays.asList(mChildListTwoOne, mChildListTwoTwo));

            Queue<ArrayList> programQueue = new LinkedList<>();
            for(int i=0; i<10; i++){
                for(ArrayList<String> object:mGroupListTwo){
                    programQueue.offer(object);
                }
            }
            for(int i=0; i<10; i++){
                for(ArrayList<String> object:mGroupListTwo){
                    programQueue.offer(object);
                }
            }
            while(!programQueue.isEmpty()){
                ArrayList<String> Templist = programQueue.poll();
            }
        }
        //하나를 뽑을 때마다
        //loglatemsrVw에서 최근 운동에 뭐가 있는지를 먼저 찾고
        //그 운동에 대한 max를 찾아서
        //formual의 count, weight, set, rest, level 등을 적용하는데
        //weight의 경우 이 사람이 female, 65이상, careerNow가 3 이하면 초보자로 구분하여 0.8을 곱한다





        //오늘 날짜에 하루씩 더해서


        HashMap<String, Object> tmpMap = new HashMap();
        tmpMap.put("startDate", startDate);
        tmpMap.put("tmpList", tmpList);


        return tmpMap;
    }
}
