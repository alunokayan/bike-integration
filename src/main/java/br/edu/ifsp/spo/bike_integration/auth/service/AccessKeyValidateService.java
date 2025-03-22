package br.edu.ifsp.spo.bike_integration.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;

@Service
public class AccessKeyValidateService {

    @Value("${x.access-key}")
    private String accessKey;

    public boolean isValid(String accessKey, RoleType... roles) {
        return this.accessKey.equals(accessKey) && roles.length > 0 && roles[0].equals(RoleType.ADMIN);
    }

}
