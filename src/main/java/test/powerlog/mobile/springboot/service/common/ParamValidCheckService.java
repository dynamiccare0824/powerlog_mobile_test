package test.powerlog.mobile.springboot.service.common;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import java.util.List;

@Service
public class ParamValidCheckService {
    public List<ObjectError> getInvalidParamList(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
            return invalidParamList;
        }
        else{
            return null;
        }
    }
}
