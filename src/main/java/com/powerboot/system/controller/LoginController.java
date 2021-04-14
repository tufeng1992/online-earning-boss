package com.powerboot.system.controller;

import com.powerboot.common.annotation.Log;
import com.powerboot.common.config.PowerbootConfig;
import com.powerboot.common.controller.BaseController;
import com.powerboot.common.domain.Tree;
import com.powerboot.common.service.FileService;
import com.powerboot.common.utils.MD5Utils;
import com.powerboot.common.utils.R;
import com.powerboot.common.utils.RandomValidateCodeUtil;
import com.powerboot.common.utils.ShiroUtils;
import com.powerboot.common.utils.StringUtils;
import com.powerboot.system.domain.MenuDO;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.MenuService;
import com.powerboot.system.service.SmsService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController extends BaseController {

    private static final List<String> SMS_SUCCESS = new ArrayList<String>() {{
        add("success");
        add("send success");
    }};
    private static final String USER_IP_COUNT_KEY = "boss:userIpCount:%s";
    private static final Integer USER_IP_COUNT_TIME = 60;
    private static final String USER_IP_LIMIT_KEY = "boss:userIpLimit:%s";
    private static final Integer USER_IP_LIMIT_TIME = 3600 * 24 * 365;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
    @Autowired
    PowerbootConfig powerbootConfig;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private SmsService smsService;

    @GetMapping({"/", ""})
    String welcome(Model model) {
        return "redirect:/login";
    }

    @Log("请求访问主页")
    @GetMapping({"/index"})
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        model.addAttribute("picUrl", "/img/logo11111.png");
        model.addAttribute("username", getUser().getUsername());
        return "index_v1";
    }

    @Log("登录")
    @GetMapping("/login")
    String login(Model model, HttpServletRequest request) {
        return "login";
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(String username, String password, String verify, HttpServletRequest request) {
        try {
            String random = (String) request.getSession().getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
            if (StringUtils.isBlank(verify)) {
                return R.error("code empty");
            }
            if (random.equals(verify)) {
            } else {
                return R.error("code error");
            }
        } catch (Exception e) {
            logger.error("验证码校验失败", e);
            return R.error("code error");
        }
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
        }
        return R.error("username or password error");
    }


    @Log("登出")
    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    /**
     * 生成验证码
     */
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            logger.error("获取验证码失败>>>> ", e);
        }
    }

}
