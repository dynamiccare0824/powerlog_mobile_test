package test.powerlog.mobile.springboot.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;
import test.powerlog.mobile.springboot.web.dto.kiosk.response.RspKioskLoginDto;
import test.powerlog.mobile.springboot.web.dto.kiosk.response.RspKioskWorkoutDto;
import test.powerlog.mobile.springboot.web.dto.mobile.response.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service // 해당 Class가 Service임을 명시합니다.
public class CommonResponseService {

    String validParamMessage = "Valid request received. Check result";
    String invalidParamMessage = "Invalid parameter included.";
    @Autowired
    UserAccountVwRepository userAccountVwRepository;

    //completed
    // 다중건 결과를 처리하는 메소드이기 때문에 getRspLoginDto 는 List를 받아서 보여줄 수 있게 되어있다.
    public <T> RspLoginDto<T> getRspLoginDto(List<ObjectError> invalidParamList,
                                             HashMap<String, Object> logRecordMap, HashMap<String, Object> logRecordFormMap,
                                             HashMap<String, Object> map) {
        RspLoginDto<T> tmpDto = new RspLoginDto<>();
        ArrayList presentWorkoutList = new ArrayList();

        if (invalidParamList != null) {
            tmpDto.setInvalidParamList(invalidParamList);
            tmpDto.setIsError(true);
            tmpDto.setMessage(invalidParamMessage);
        }
        // Request valid but no record
        else if (logRecordMap == null) {
            tmpDto.setIsError(false);
            tmpDto.setIsMatch(false);
            tmpDto.setTotalList((ArrayList) logRecordFormMap.get("LoginWorkoutCode"));
            tmpDto.setMessage("No registered data existed.");
        } else {
            tmpDto.setIsMatch((Boolean) map.get("isMatch"));
            tmpDto.setName((String) map.get("name"));
            tmpDto.setIsError(false);
            tmpDto.setMessage(validParamMessage);
            presentWorkoutList = (ArrayList) logRecordMap.get("presentWorkoutCode");
            if (presentWorkoutList.isEmpty()) {
                tmpDto.setResultPresentList(null);
            } else {
                tmpDto.setResultPresentList(presentWorkoutList);
            }
            tmpDto.setTotalList((ArrayList) logRecordMap.get("LoginWorkoutCode"));
            logRecordMap.remove("presentWorkoutCode");
            logRecordMap.remove("LoginWorkoutCode");
            tmpDto.setResultData(logRecordMap);

        }
        return tmpDto;
    }


    //completed
    public <T> RspDupCheckEmailDto<T> getRspDupCheckEmailDto(List<ObjectError> invalidParamList, HashMap<String, Object> map) {
        RspDupCheckEmailDto<T> tmpDto = new RspDupCheckEmailDto<>();
        if (invalidParamList != null) {
            tmpDto.setInvalidParamList(invalidParamList);
            tmpDto.setIsError(true);
            tmpDto.setMessage(invalidParamMessage);
            tmpDto.setEmailPresent(null);
        } else {
            tmpDto.setEmailPresent((Boolean) map.get("emailPresent"));
            tmpDto.setIsError(false);
            tmpDto.setMessage(validParamMessage);

        }
        return tmpDto;
    }

    public <T> RspDupCheckSendMsg<T> getRspDupCheckSendMsgDto(List<T> inValidParamList, HashMap<String, Object> map) {
        RspDupCheckSendMsg<T> tmpDto = new RspDupCheckSendMsg<>();
        if (inValidParamList != null) {
            tmpDto.setInvalidParamList(inValidParamList);
            tmpDto.setIsError(true);
            tmpDto.setVerificationNum(null);
            tmpDto.setSendMsgResult(null);
            tmpDto.setMessage("Invalid parameter included.");
        } else {
            tmpDto.setPhonePresent((Boolean) map.get("phonePresent"));
            //Common Part
            if (map.get("error") != null) {
                tmpDto.setIsError(true);
                tmpDto.setMessage((String) map.get("error"));
                tmpDto.setVerificationNum(null);
                tmpDto.setSendMsgResult(null);
            } else {
                tmpDto.setIsError(false);
                tmpDto.setVerificationNum((String) map.get("verificationNum"));
                tmpDto.setSendMsgResult((String) map.get("sendMsgResult"));
                tmpDto.setInvalidParamList(null);
                tmpDto.setMessage(validParamMessage);
            }
        }
        return tmpDto;
    }

    public <T> RspEmailPasswordCheckDto<T> getRspEmailPasswordCheckDto(List<ObjectError> errorList, HashMap<String, Object> map) {
        RspEmailPasswordCheckDto<T> tmpDto = new RspEmailPasswordCheckDto<>();
        if (errorList != null) {
            tmpDto.setInvalidParamList(errorList);
            tmpDto.setIsMatch(null);
            tmpDto.setIsError(true);
            tmpDto.setMessage("Invalid parameter included.");
        } else {
            tmpDto.setIsMatch((Boolean) map.get("isMatch"));
            if (map.get("error") != null) {
                tmpDto.setIsError(true);
                tmpDto.setMessage((String) map.get("error"));
            } else {
                tmpDto.setIsError(false);
                tmpDto.setMessage(validParamMessage);
            }
        }
        return tmpDto;
    }

    public RspLostValidPhoneDto getRspLostValidPhoneDto(List<ObjectError> errorList, HashMap<String, Object> commonMap) {
        RspLostValidPhoneDto tmpDto = new RspLostValidPhoneDto();
        if (errorList != null) {
            tmpDto.setInvalidParamList(errorList);
            tmpDto.setIsMatch(null);
            tmpDto.setIsError(true);
            tmpDto.setVerificationNum(null);
            tmpDto.setMessage("Invalid parameter included.");
        } else {
            tmpDto.setIsMatch((Boolean) commonMap.get("isMatch"));
            if ((Boolean) commonMap.get("isMatch")) {
                tmpDto.setInvalidParamList(null);
                tmpDto.setIsMatch(true);
                tmpDto.setIsError(false);
                tmpDto.setVerificationNum((String)commonMap.get("verificationNum"));
                tmpDto.setMessage(validParamMessage);
            } else {
                tmpDto.setInvalidParamList(null);
                tmpDto.setIsMatch(false);
                tmpDto.setIsError(false);
                tmpDto.setVerificationNum((String) commonMap.get("verificationNum"));
                tmpDto.setMessage(validParamMessage);
            }
        }
        return tmpDto;
    }

    public <T> RspDeleteUserDto getRspDeleteUserDto(List<ObjectError> invalidParamList, HashMap<String, Object> map) {
        RspDeleteUserDto tmpDto = new RspDeleteUserDto();
        tmpDto.setIsMatch((Boolean) map.get("isMatch"));
        //Common Part
        if (map.get("error") != null) {
            tmpDto.setIsError(true);
            tmpDto.setMessage((String) map.get("error"));
        } else {
            tmpDto.setIsError(false);
            tmpDto.setMessage(validParamMessage);
        }
        return tmpDto;
    }

    public RspRegisterDto getRspRegisterDto(List<ObjectError> invalidParamList, String email) {
        RspRegisterDto tmpDto = new RspRegisterDto();
        if (invalidParamList != null) {
            tmpDto.setInvalidParamList(invalidParamList);
            tmpDto.setIsError(true);
            tmpDto.setIsPresent(null);
            tmpDto.setMessage("Invalid parameter included.");
        } else {
            tmpDto.setInvalidParamList(null);
            tmpDto.setIsError(false);
            tmpDto.setIsPresent(userAccountVwRepository.findById(email).isPresent());
            tmpDto.setMessage(validParamMessage);
        }
        return tmpDto;
    }

    public <T> RspKioskLoginDto<T> getRspKioskLoginDto(List<ObjectError> invalidParamList,
                                                       HashMap<String, Object> onDateWrkotMap,
                                                       HashMap<String, Object> uidLoginResult,
                                                       HashMap<String, Object> wrkotCodeMap) {
        RspKioskLoginDto<T> resultDto = new RspKioskLoginDto<>();
        if (invalidParamList != null) {
            resultDto.setIsPresent(null);
            resultDto.setName(null);
            resultDto.setResultData(onDateWrkotMap);
            resultDto.setIsError(true);
            resultDto.setWrkotCodeMap(wrkotCodeMap);
            resultDto.setInvalidParamList(invalidParamList);
            resultDto.setMessage(invalidParamMessage);
        } else {
            resultDto.setIsPresent((Boolean) uidLoginResult.get("isPresent"));
            resultDto.setName((String) uidLoginResult.get("name"));
            resultDto.setResultData(onDateWrkotMap);
            resultDto.setIsError(false);
            resultDto.setWrkotCodeMap(wrkotCodeMap);
            resultDto.setInvalidParamList(invalidParamList);
            resultDto.setMessage(validParamMessage);
        }
        return resultDto;
    }

    public RspKioskWorkoutDto getRspKioskWorkoutDto(List<ObjectError> invalidParamList,
                                                                   HashMap<String, Object> commonMap) {
        RspKioskWorkoutDto resultDto = new RspKioskWorkoutDto();
        if (invalidParamList != null) {
            resultDto.setIsError(true);
            resultDto.setIsDone(false);
            resultDto.setInvalidParamList(invalidParamList);
            resultDto.setMessage(invalidParamMessage);
        }
        else if(invalidParamList==null && (Boolean) commonMap.get("isError")) {
            resultDto.setIsError(false);
            resultDto.setIsDone(true);
            resultDto.setInvalidParamList(null);
            resultDto.setMessage(validParamMessage);
        }
        // invalidParamList==null && (Boolean) commonMap.get("isError")==false
        else{
            resultDto.setIsError(true);
            resultDto.setIsDone(false);
            resultDto.setInvalidParamList(null);
            resultDto.setMessage(validParamMessage);
        }
        return resultDto;
    }

    public CommonResponseDto getCommonResponse(List inValidParamList, HashMap<String, Object> map) {
        CommonResponseDto tmpDto = new CommonResponseDto();
        if (inValidParamList != null) {
            tmpDto.setInvalidParamList(inValidParamList);
            tmpDto.setIsError(true);
            tmpDto.setMessage(invalidParamMessage);
        } else {

            tmpDto.setIsError(false);
            tmpDto.setInvalidParamList(null);
            tmpDto.setMessage(validParamMessage);
        }
        return tmpDto;
    }

    public HashMap<String, Object> getCommonHashMap() {
        HashMap<String, Object> tmpMap = new HashMap<>();
        tmpMap.put("verificationNum", null);
        tmpMap.put("phonePresent", null);
        tmpMap.put("emailPresent", null);
        tmpMap.put("isPresent", null);
        tmpMap.put("isError", null);
        tmpMap.put("isMatch", null);
        tmpMap.put("isDone", null);
        tmpMap.put("message", null);
        return tmpMap;
    }

//    public RspEmailPasswordCheckDto getRspEmailPasswordCheckDto(HashMap checkResultMap) {
//    }
}
