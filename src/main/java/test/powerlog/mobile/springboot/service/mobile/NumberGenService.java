package test.powerlog.mobile.springboot.service.mobile;

import org.springframework.stereotype.Service;

import java.util.Random;
// 고유 번호와 문자 인증 번호를 만들어내기 위한 클래스

@Service
public class NumberGenService {

    private int size;
    private boolean lowerCheck;

    public String Digits(int len, int dupCd ) {

        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++) {

            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));

            if(dupCd==1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    }

    public String ComplicatedDigits(int len, int dupCd ) {

        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++) {

            //0~9 까지 난수 + *과 # 포함 생성
            String ran = Integer.toString(rand.nextInt(12));


            if(dupCd==1) {
                //중복 허용시 numStr에 append
                if(ran.equals("10")){
                    numStr += "#";
                }
                else if(ran.equals("11")){
                    numStr += "*";
                }
                else{
                    numStr += ran;
                }
            }
            else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    if(ran == "11"){
                        numStr += "#";
                    }
                    else if(ran == "12"){
                        numStr += "*";
                    }
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    }


    public String getKey(int size, boolean lowerCheck) {
        this.size = size;
        this.lowerCheck = lowerCheck;
        return init();
    }

    private String init() {
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();

        int num = 0;

        do {
            num = ran.nextInt(75) + 48;

            if((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char)num);
            }else {
                continue;
            }
        } while (sb.length() < size);

        if(lowerCheck) {
            return sb.toString().toLowerCase();
        }

        return sb.toString();
    }
}
