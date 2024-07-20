package com.summary.front.manager.rest.login;

import com.summary.client.authority.dto.AdminDTO;
import com.summary.client.authority.enums.UserStatusEnum;
import com.summary.client.remote.AdminRemoteService;
import com.summary.common.core.dto.R;
import com.summary.common.core.dto.TokenDTO;
import com.summary.common.core.enums.AppEnum;
import com.summary.common.core.exception.CustomException;
import com.summary.common.core.jwt.JwtUtils;
import com.summary.common.core.utils.crypto.DesUtil;
import com.summary.common.core.utils.ip.IpUtils;
import com.summary.front.manager.rest.login.from.UsernamePasswordLoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.summary.client.authority.code.AuthorityExceptionCode.ADMIN_NON_EXIT_OR_PASSWORD_ERROR;

/**
 * 登陆相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private AdminRemoteService adminRemoteService;

    /**
     * 用户名密码登陆
     *
     * @param param UsernamePasswordLoginForm
     */
    @PostMapping("/login/username")
    public R<TokenDTO> usernamePasswordLogin(@Valid @RequestBody UsernamePasswordLoginForm param, HttpServletRequest request) {

        AdminDTO adminDTO = adminRemoteService.getAdminByUsername(param.getUsername());
        // 账户不存在或账户已注销
        if (null == adminDTO || UserStatusEnum.logout.getCode().equals(adminDTO.getUserStatus())) {
            throw new CustomException(ADMIN_NON_EXIT_OR_PASSWORD_ERROR);
        }

        // 密码
        String password = DesUtil.decrypt(adminDTO.getPassword());
        // 检查密码是否正确
        if (!password.equals(param.getPassword())) {
            // 登录失败增加锁定功能
            throw new CustomException(ADMIN_NON_EXIT_OR_PASSWORD_ERROR);
        }

        TokenDTO tokenDTO = JwtUtils.generateJwtToken(adminDTO.getAdminId(), AppEnum.ADMIN, IpUtils.getIpAddr(request));

        return R.success(tokenDTO);
    }
}
