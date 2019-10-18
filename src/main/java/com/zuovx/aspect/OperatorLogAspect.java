package com.zuovx.aspect;

import com.zuovx.annotation.OperatorLogController;
import com.zuovx.annotation.ServiceExceptionLog;
import com.zuovx.dao.OperatorLogMapper;
import com.zuovx.model.OperatorLog;
import com.zuovx.model.UserInfo;
import com.zuovx.utils.ApplicationUtils;
import com.zuovx.utils.IpAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 操作日志切面
 * @author zuovx
 * @date 2019-10-17 17:13
 */
@Component
@Aspect
@Slf4j
public class OperatorLogAspect {

	/**
	 * fixme  注入Service用于把日志保存数据库，实际项目入库采用队列做异步
	 */
	@Autowired
	private OperatorLogMapper operatorLogMapper;

	/**
	 * Service层切点
	 */
	@Pointcut("@annotation(com.zuovx.annotation.ServiceExceptionLog)")
	public void serviceAspect(){
	}


	/**
	 * Controller层切点
	 */
	@Pointcut("@annotation(com.zuovx.annotation.OperatorLogController)")
	public void controllerAspect(){
	}


	/**
	 * 前置通知 我目前只打算用在controller层上，记录哪些用户用过哪些接口
	 * @param joinPoint j
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String params = getParams(joinPoint);
		UserInfo userInfo = ApplicationUtils.getUserInfo();
		String ip = IpAddressUtils.getIpAdrress(request);
		try {

			//*========控制台输出=========*//
			System.out.println("==============前置通知开始==============");
			System.out.println("请求方法" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()));
			System.out.println("方法描述：" + getControllerMethodDescription(joinPoint));
			System.out.println("请求人："+ userInfo.getAccount());
			System.out.println("请求ip："+ip);
			System.out.println("请求参数:" + params);


			//*========数据库日志=========*//
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setCreatedAt(new Date());
			operatorLog.setIp(ip);
			operatorLog.setUserId(userInfo.getId());
			operatorLog.setMethod(joinPoint.getSignature().getName());
			operatorLog.setOperator(getControllerMethodDescription(joinPoint));
			operatorLog.setParams(params);
			operatorLog.setUpdateAt(new Date());

			operatorLogMapper.insertSelective(operatorLog);

		}catch (Exception e){
			//记录本地异常日志
			log.error("==前置通知异常==");
			log.error("异常信息：",e);
		}
	}


	/**
	 * 异常通知 用于拦截service层记录异常日志
	 * @param joinPoint j
	 * @param e Exception
	 */
	@AfterThrowing(pointcut = "serviceAspect()",throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		UserInfo userInfo = ApplicationUtils.getUserInfo();
		//获取请求ip
		String ip = IpAddressUtils.getIpAdrress(request);

		String params = getParams(joinPoint);

		try{
			/*========控制台输出=========*/
			System.out.println("=====异常通知开始=====");
			System.out.println("异常代码:" + e.getClass().getName());
			System.out.println("异常信息:" + e.getMessage());
			System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			System.out.println("方法描述:" + getServiceMethodDescription(joinPoint));
			System.out.println("请求人:" + userInfo.getAccount());
			System.out.println("请求IP:" + ip);
			System.out.println("请求参数:" + params);
			/*==========数据库日志=========*/
			// todo save to db 目前异常信息不保存数据库，往后可保存
		}catch (Exception ex){
			//记录本地异常日志
			log.error("==异常通知异常==");
			log.error("异常信息:", ex);
		}
	}

	/**
	 *  service 异常记录
	 * @param joinPoint j
	 * @return r
	 * @throws Exception e
	 */
	private  String getServiceMethodDescription(JoinPoint joinPoint)throws Exception{
		String targetName = joinPoint.getTarget().getClass().getName();
		Class targetClass = Class.forName(targetName);
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method:methods) {
			if (method.getName().equals(methodName)){
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length==arguments.length){
					description = method.getAnnotation(ServiceExceptionLog.class).describe();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中的describe
	 * @param joinPoint j
	 * @return 注解的描述
	 * @throws Exception e
	 */
	private  String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		// 类名
		String targetName = joinPoint.getTarget().getClass().getName();
		// 方法名
		String methodName = joinPoint.getSignature().getName();
		// 参数
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method:methods) {
			if (method.getName().equals(methodName)){
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length==arguments.length){
					description = method.getAnnotation(OperatorLogController.class).describe();

				}
			}
		}
		return description;
	}


	private String getParams(JoinPoint joinPoint){
		String params = "";
		if (joinPoint.getArgs()!=null&&joinPoint.getArgs().length>0){
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				params += joinPoint.getArgs()[i]+";";
			}
		}
		return params;
	}

}
