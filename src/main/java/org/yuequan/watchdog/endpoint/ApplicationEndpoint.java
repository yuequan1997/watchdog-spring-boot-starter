package org.yuequan.watchdog.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.yuequan.watchdog.client.Application;
import org.yuequan.watchdog.client.ApplicationService;
import org.yuequan.watchdog.configuration.ResourceServerConfiguration;
import org.yuequan.watchdog.endpoint.support.ApplicationParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author yuequan
 * @since
 **/
@FrameworkEndpoint
@ResponseBody
public class ApplicationEndpoint {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping(value = {"${watchdog.application.prefix:}/applications" , "${watchdog.application.prefix:}/applications/", "${watchdog.application.prefix:}/applications/index"})
    public List<Application> index(){
        return applicationService.findAll();
    }

    @PostMapping(value = {"${watchdog.application.prefix:}/applications" , "${watchdog.application.prefix:}/applications/"})
    public ClientDetails create(@RequestBody ApplicationParam param){
        param.populateDefault();

        Application application = new Application(
                UUID.randomUUID().toString(),
                ResourceServerConfiguration.RESOURCE_NAME,
                param.getScope(),
                param.getRedirectUri(),
                param.getName()
                );
        applicationService.addClientDetails(application);
        return applicationService.loadClientByClientId(application.getClientId());
    }


    @GetMapping(value = {"${watchdog.application.prefix:}/applications/{clientId}"})
    public ClientDetails show(@PathVariable String clientId){
        return applicationService.loadClientByClientId(clientId);
    }

    @PutMapping(value = {"${watchdog.application.prefix:}/applications/{clientId}"})
    public ClientDetails update(@PathVariable String clientId, @RequestBody ApplicationParam param){
        Optional<Application> application = applicationService.findByClientId(clientId);
        if(!application.isPresent()){
            throw new NoSuchClientException("Not Found The Client.");
        }
        application.ifPresent(app -> {
            param.populateDefault();
            if(!StringUtils.isEmpty(param.getName())){
                app.setName(param.getName());
            }
            if(param.getRedirectUri() != null){
                app.setRegisteredRedirectUri(param.getRedirectUri());
            }

            if(param.getScope() != null){
                app.setScope(param.getScope());
            }
        });
        applicationService.updateClientDetails(application.get());
        return application.get();
    }

    @DeleteMapping(value = {"${watchdog.application.prefix:}/applications/{clientId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String clientId){
        applicationService.removeClientDetails(clientId);
    }

}
