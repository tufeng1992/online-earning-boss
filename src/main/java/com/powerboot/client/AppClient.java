package com.powerboot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "appClient", url = "http://online-earning:80")
public interface AppClient {

    @PostMapping("/pay/order/{orderNo}")
    BaseResponse<PayDO> getPay(@PathVariable("orderNo") String orderNo);

    @PostMapping("/user/pass")
    BaseResponse<PayDO> applyPass(@RequestBody ApplyRequest applyRequest);

    @PostMapping("/user/reject")
    BaseResponse<PayDO> applyReject(@RequestBody ApplyRequest applyRequest);

}
