package test.powerlog.mobile.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import test.powerlog.mobile.springboot.web.dto.response.*;

import java.util.HashMap;
import java.util.List;

@Service // 해당 Class가 Service임을 명시합니다.
public class ResponseService {

    String noErrorMessage = "Valid request received. Check result";
    String invalidParamMessage = "Invalid parameter included.";

    //completed
    // 다중건 결과를 처리하는 메소드이기 때문에 getRspLoginDto 는 List를 받아서 보여줄 수 있게 되어있다.
    public <T> RspLoginDto<T> getRspLoginDto(List<ObjectError> invalidParamList,
                                             List<T> list,
                                             HashMap<String, Object> map) {
        RspLoginDto<T> tmpDto = new RspLoginDto<>();
        if (invalidParamList != null) {
            tmpDto.setInvalidParamList(invalidParamList);
            tmpDto.setIsError(true);
            tmpDto.setIsMatch(null);
            tmpDto.setResultData(null);
            tmpDto.setName(null);
            tmpDto.setMessage(invalidParamMessage);
        } else if (invalidParamList == null && list == null) {
            tmpDto.setInvalidParamList(null);
            tmpDto.setIsError(false);
            tmpDto.setIsMatch(false);
            tmpDto.setResultData(null);
            tmpDto.setName(null);
            tmpDto.setMessage("No registered data existed.");
        } else {
            tmpDto.setIsMatch((Boolean) map.get("isMatch"));
            tmpDto.setName((String) map.get("name"));
            tmpDto.setIsError(false);
            tmpDto.setMessage(noErrorMessage);
            if (!list.isEmpty()) {
                tmpDto.setResultData(list);
            } else {
                tmpDto.setResultData(null);
            }
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

//    public CommonResult getFailResult(HashMap<String, Object> map) {
//        CommonResult tmpDto = new CommonResult();
//
//        tmpDto.setIsError(true);
//        tmpDto.setMessage((String) map.get("error"));
//
//        return tmpDto;
//    }
//
//    public CommonResult getSuccessResult(HashMap<String, Object> map) {
//        CommonResult tmpDto = new CommonResult();
//
//        tmpDto.setIsError(false);
//        tmpDto.setMessage("Job is Done. Error: " + (String) map.get("error"));
//
//        return tmpDto;
//    }
//
//    public <T> FailListResultDto<T> getFailListResult(List<T> list) {
//        FailListResultDto tmpDto = new FailListResultDto();
//
//        tmpDto.setIsError(true);
//        tmpDto.setResultData(list);
//        tmpDto.setMessage("Invalid input value included.");
//
//        return tmpDto;
//    }
}
