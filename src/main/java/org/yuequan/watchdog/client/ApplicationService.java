package org.yuequan.watchdog.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Application 服务操作
 * @see ClientDetailsService
 * @see ClientRegistrationService
 * @author yuequan
 * @since 1.0
 **/
public class ApplicationService implements ClientDetailsService, ClientRegistrationService {

    @Autowired
    private ApplicationRepository<Application> applicationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Optional<Application> application = applicationRepository.findByClientId(clientId);
        if(application.isEmpty()){
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return applicationRepository.findByClientId(clientId).get();
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        if(applicationRepository.findByClientId(clientDetails.getClientId()).isPresent()){
            throw new ClientAlreadyExistsException("The client already exists");
        }
        applicationRepository.save(clientDetails);
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        if(applicationRepository.findByClientId(clientDetails.getClientId()).isPresent()){
            applicationRepository.update(clientDetails);
        }else{
            throw new NoSuchClientException("Not Found The Client.");
        }
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        Optional<Application> application = applicationRepository.findByClientId(clientId);
        if(application.isPresent()){
            application.get().setClientSecret(passwordEncoder.encode(secret));
            applicationRepository.update(application.get());
        }else{
            throw new NoSuchClientException("Not Found The Client.");
        }
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        applicationRepository.delete(clientId);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        List<ClientDetails> clientDetails = new ArrayList<>();
        applicationRepository.findAll().forEach(application -> clientDetails.add(application));
        return clientDetails;
    }

    public List<Application> findAll(){
        return applicationRepository.findAll();
    }

    public Optional<Application> findByClientId(String clientId){
        return applicationRepository.findByClientId(clientId);
    }
}
