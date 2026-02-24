package com.lz.lzpicturebackend.aop;

import com.lz.lzpicturebackend.annotation.AuthCheck;
import com.lz.lzpicturebackend.exception.BusinessException;
import com.lz.lzpicturebackend.exception.ErrorCode;
import com.lz.lzpicturebackend.model.enity.User;
import com.lz.lzpicturebackend.model.enums.UserRoleEnum;
import com.lz.lzpicturebackend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//  类级注解（核心：标记这是一个AOP切面，且交给Spring管理）
@Aspect // 告诉Spring：这是一个AOP切面类（封装了增强逻辑）
@Component // 告诉Spring：把这个类交给容器管理（Spring才能自动执行它）
public class AuthInterceptor {

    //  注入用户服务（用来获取当前登录用户）
    @Resource // 依赖注入：Spring自动把UserService的实现类塞进来
    private UserService userService;
    /**
     * 执行拦截（核心方法：权限校验的逻辑都在这里）
     *
     * @param joinPoint 切入点：被拦截的那个方法（比如“删除用户”方法）
     * @param authCheck 权限校验注解：拿到注解上配置的mustRole（比如admin）
     */
    //  环绕通知注解：拦截所有标注了@AuthCheck的方法，且把注解对象传入方法
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        //  第一步：拿到注解上要求的角色（比如@AuthCheck(mustRole="admin")，这里就拿到"admin"）
        String mustRole = authCheck.mustRole();

        //  第二步：获取当前的HTTP请求（从Spring的请求上下文里拿）
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        //  第三步：获取当前登录的用户（从请求的Session里拿登录态，UserService里实现）
        User loginUser = userService.getLoginUser(request);

        //  第四步：把注解的角色字符串转成枚举（比如"admin"转成UserRoleEnum.ADMIN）
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);

        //  第五步：如果注解没配mustRole（默认空），说明不需要权限，直接放行
        if (mustRoleEnum == null) {
            return joinPoint.proceed(); // 放行：执行被拦截的目标方法（比如删除用户）
        }

        //  第六步：把当前登录用户的角色转成枚举（比如用户的userRole是"user"，转成UserRoleEnum.USER）
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());

        //  第七步：如果用户角色无效（比如数据库里存了个"test"，枚举里没有），抛无权限异常
        if (userRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR); // 抛异常：前端收到“无权限”提示
        }

        //  第八步：核心校验：要求是管理员，但用户不是，抛异常
        // （比如注解要求mustRole="admin"，但用户是USER，就拒绝）
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum) && !UserRoleEnum.ADMIN.equals(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        //  第九步：所有校验通过，放行执行目标方法
        return joinPoint.proceed();
    }
}
