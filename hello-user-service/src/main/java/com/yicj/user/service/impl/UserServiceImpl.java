package com.yicj.user.service.impl;

import com.yicj.common.exception.AppException;
import com.yicj.common.model.vo.UserVO;
import com.yicj.common.utils.CommonUtil;
import com.yicj.user.model.entity.UserEntity;
import com.yicj.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * @author yicj
 * @date 2023年09月03日 7:45
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * 用户实体集合
     */
    private static List<UserEntity> USER_LIST = new CopyOnWriteArrayList<>() ;

    /**
     * 登录用户map
     */
    private static Map<String, UserVO> LOGIN_USER_MAP = new ConcurrentHashMap<>() ;

    /**
     * 用户id 与用户token 的关系记录
     */
    private static Map<String, String> LOGIN_USERID_MAP = new ConcurrentHashMap<>() ;


    @Override
    public Mono<String> login(String username, String password) {
        return Flux.fromIterable(USER_LIST)
            .filter(item -> equalsUserFunc(username, password).apply(item))
            .next()
            .switchIfEmpty(Mono.error(new AppException("1003","用户名密码不正确")))
            // 清理之前的的登录状态
            .flatMap(item -> {
                log.info("[Service] 清理旧的登录状态..");
                String userId = item.getId();
                String token = LOGIN_USERID_MAP.get(userId);
                if (token != null) {
                    LOGIN_USERID_MAP.remove(userId);
                    LOGIN_USER_MAP.remove(token);
                }
                return Mono.just(item);
            })
            // 生成新的登录token并返回
            .map(item -> {
                log.info("[Service] 生成token并保存用户登录状态..");
                String userId = item.getId();
                String token = CommonUtil.uuid();
                LOGIN_USERID_MAP.put(userId, token);
                //
                UserVO vo = new UserVO();
                vo.setId(userId);
                vo.setUsername(item.getUsername());
                vo.setAddress(item.getAddress());
                LOGIN_USER_MAP.put(token, vo);
                //
                return token;
            });
    }

    @Override
    public Mono<String> register(String username, String password, String address) {
        return this.checkUseNameDuplicate(username)
            .then(Mono.defer(() -> {
                log.info("[Service] 用户信息保存入库...");
                String userId = CommonUtil.uuid();
                // 1. 保存入库
                UserEntity entity = new UserEntity() ;
                entity.setId(userId);
                entity.setUsername(username);
                entity.setPassword(password);
                entity.setAddress(address);
                USER_LIST.add(entity) ;
                return Mono.just(entity);
            })).flatMap(entity ->{
                log.info("[Service] 生成token并保存用户登录状态..");
                String userId = entity.getId() ;
                // 2. 生成token
                String token = CommonUtil.uuid();
                UserVO vo = new UserVO() ;
                vo.setId(userId); ;
                vo.setUsername(entity.getUsername());
                vo.setAddress(entity.getAddress());
                LOGIN_USER_MAP.put(token, vo) ;
                LOGIN_USERID_MAP.put(userId, token) ;
                return Mono.zip(Mono.just(token), Mono.just(vo)) ;
            }).map(Tuple2::getT1) ;
    }

    @Override
    public Mono<UserVO> findByToken(String token) {
        return Mono.fromSupplier(() -> {
            log.info("[Service] 根据token 查找用户信息...");
            return LOGIN_USER_MAP.get(token) ;
        }).switchIfEmpty(Mono.error(new AppException("1002", "非法token，请检查！")));
    }


    /**
     * 校验用户名是否重复
     * @return
     */
    private Mono<Boolean> checkUseNameDuplicate(String username){
        return Flux.fromIterable(USER_LIST)
            .filter(userEntity -> username.equals(userEntity.getUsername()))
            .next()
            .map(item -> false)
            .switchIfEmpty(Mono.just(true))
            .flatMap(flag -> {
                if (!flag){
                    return Mono.error(new AppException("1001","用户名【"+username+"】已经存在！")) ;
                }
                return Mono.just(true) ;
            }) ;
    }


    private Function<UserEntity, Boolean> equalsUserFunc(String username, String password){
        return userEntity -> {
            if (userEntity == null){
                return false ;
            }
            return username.equals(userEntity.getUsername())
                    && password.equals(userEntity.getPassword()) ;
        } ;
    }
}
