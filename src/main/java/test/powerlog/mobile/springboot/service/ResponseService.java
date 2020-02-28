package test.powerlog.mobile.springboot.service;

import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.web.dto.response.CommonResult;
import test.powerlog.mobile.springboot.web.dto.response.ListResult;
import test.powerlog.mobile.springboot.web.dto.response.ResponseLoginDto;
import test.powerlog.mobile.springboot.web.dto.response.SingleResult;
import java.util.List;

@Service // 해당 Class가 Service임을 명시합니다.
public class ResponseService {

    // 결과 모델의 common 부분에 api 요청 실패 값을 세팅해주는 메소드
    private void setIsErrorTrue(CommonResult result) {
        result.setError(true);
        result.setMessage("Invalid Request or Internal Server Error");
    }
    // 결과 모델의 common 파라미터에 api 요청 성공 값을 세팅해주는 메소드
    private void setIsErrorFalse(CommonResult result) {
        result.setError(false);
        result.setMessage("Valid Request, check resultData");
    }

    // 단일건 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> tmpSingle = new SingleResult<>();
        tmpSingle.setResultData(data);
        setIsErrorFalse(tmpSingle);
        return tmpSingle;
    }

    // 다중건 결과를 처리하는 메소드
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> tmpList = new ListResult<>();
        tmpList.setResultData(list);
        setIsErrorFalse(tmpList);
        return tmpList;
    }

    // 다중건 결과를 처리하는 메소드
    public <T> ResponseLoginDto<T> getResponseLoginDto(List<T> list, Boolean match) {
        ResponseLoginDto<T> tmpDto = new ResponseLoginDto<>();
        tmpDto.setResultData(list);
        tmpDto.setIsMatch(match);
        if(match){
            setIsErrorTrue(tmpDto);
        }
        else{
            setIsErrorFalse(tmpDto);
        }
        return tmpDto;
    }
    // 단순 작업 처리 성공 결과만 처리하는 메소드
    public CommonResult getSuccessResult() {
        CommonResult tmpSingle = new CommonResult();
        setIsErrorFalse(tmpSingle);
        return tmpSingle;
    }

    // 단순 작업 처리 실패 결과만 처리하는 메소드
    public CommonResult getFailResult() {
        CommonResult tmpSingle = new CommonResult();
        setIsErrorFalse(tmpSingle);
        return tmpSingle;
    }

}
