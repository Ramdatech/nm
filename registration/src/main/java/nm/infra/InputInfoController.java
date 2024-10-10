package nm.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import nm.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/inputInfos")
@Transactional
public class InputInfoController {

    @Autowired
    InputInfoRepository inputInfoRepository;

    @RequestMapping(
        value = "/inputInfos/createclientinfo",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public InputInfo createClientInfo(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody CreateClientInfoCommand createClientInfoCommand
    ) throws Exception {
        System.out.println("##### /inputInfo/createClientInfo  called #####");
        InputInfo inputInfo = new InputInfo();
        inputInfo.createClientInfo(createClientInfoCommand);
        inputInfoRepository.save(inputInfo);
        return inputInfo;
    }

    @RequestMapping(
        value = "/inputInfos/{id}/modifyclientinfo",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public InputInfo modifyClientInfo(
        @PathVariable(value = "id") Long id,
        @RequestBody ModifyClientInfoCommand modifyClientInfoCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /inputInfo/modifyClientInfo  called #####");
        Optional<InputInfo> optionalInputInfo = inputInfoRepository.findById(
            id
        );

        optionalInputInfo.orElseThrow(() -> new Exception("No Entity Found"));
        InputInfo inputInfo = optionalInputInfo.get();
        inputInfo.modifyClientInfo(modifyClientInfoCommand);

        inputInfoRepository.save(inputInfo);
        return inputInfo;
    }

    @RequestMapping(
        value = "/inputInfos/{id}/deleteclientinfo",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public InputInfo deleteClientInfo(
        @PathVariable(value = "id") Long id,
        @RequestBody DeleteClientInfoCommand deleteClientInfoCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /inputInfo/deleteClientInfo  called #####");
        Optional<InputInfo> optionalInputInfo = inputInfoRepository.findById(
            id
        );

        optionalInputInfo.orElseThrow(() -> new Exception("No Entity Found"));
        InputInfo inputInfo = optionalInputInfo.get();
        inputInfo.deleteClientInfo(deleteClientInfoCommand);

        inputInfoRepository.delete(inputInfo);
        return inputInfo;
    }
}
//>>> Clean Arch / Inbound Adaptor
