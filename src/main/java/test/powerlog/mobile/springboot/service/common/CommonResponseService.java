package test.powerlog.mobile.springboot.service.common;

import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import test.powerlog.mobile.springboot.web.dto.kiosk.response.RspKioskLoginDto;
import test.powerlog.mobile.springboot.web.dto.mobile.response.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service // 해당 Class가 Service임을 명시합니다.
public class CommonResponseService {

    String noErrorMessage = "Valid request received. Check result";
    String invalidParamMessage = "Invalid parameter included.";

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
            tmpDto.setMessage(noErrorMessage);
            presentWorkoutList = (ArrayList) logRecordMap.get("presentWorkoutcode");
            tmpDto.setResultPresentList(presentWorkoutList);
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
            tmpDto.setMessage(noErrorMessage);

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
                tmpDto.setMessage(noErrorMessage);
            }
        }
        return tmpDto;
    }

    public <T> RspEmailPasswordCheckDto<T> getRspEmailPasswordCheckDto(List<T> errorList, HashMap<String, Object> map) {
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
                tmpDto.setMessage(noErrorMessage);
            }
        }
        return tmpDto;
    }

    public <T> RspDeleteUserDto<T> getRspDeleteUserDto(List<ObjectError> invalidParamList, HashMap<String, Object> map) {
        RspDeleteUserDto<T> tmpDto = new RspDeleteUserDto<>();
        tmpDto.setIsMatch((Boolean) map.get("isMatch"));
        //Common Part
        if (map.get("error") != null) {
            tmpDto.setIsError(true);
            tmpDto.setMessage((String) map.get("error"));
        } else {
            tmpDto.setIsError(false);
            tmpDto.setMessage(noErrorMessage);
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
            resultDto.setMessage(noErrorMessage);
        }
        return resultDto;
    }

    public <T> RspKioskLoginDto<T> getRspKioskMainDto(List<ObjectError> invalidParamList,
                                                      HashMap<String, Object> onDateWrkotMap,
                                                      HashMap<String, Object> uidLoginResult) {
        RspKioskLoginDto<T> resultDto = new RspKioskLoginDto<>();
        if (invalidParamList != null) {
            resultDto.setIsPresent(null);
            resultDto.setName(null);
            resultDto.setResultData(onDateWrkotMap);
            resultDto.setIsError(true);
            resultDto.setInvalidParamList(invalidParamList);
            resultDto.setMessage(invalidParamMessage);
        } else {
            resultDto.setIsPresent((Boolean) uidLoginResult.get("isPresent"));
            resultDto.setName((String) uidLoginResult.get("name"));
            resultDto.setResultData(onDateWrkotMap);
            resultDto.setIsError(false);
            resultDto.setInvalidParamList(invalidParamList);
            resultDto.setMessage(noErrorMessage);
        }
        return resultDto;
    }
}
