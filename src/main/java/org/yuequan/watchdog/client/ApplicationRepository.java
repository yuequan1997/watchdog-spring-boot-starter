package org.yuequan.watchdog.client;

import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.List;
import java.util.Optional;

/**
 * 对{@link Application}持久化管理的接口定义
 * @author yuequan
 * @since 1.0
 **/
public interface ApplicationRepository<T extends ClientDetails> {
    T save(ClientDetails t);
    Optional<T> findByClientId(String clientId);
    T update(ClientDetails t);
    List<T> findAll();
    List<T> findAll(int pageNo, int pageSize);
    void delete(String clientId);
}
