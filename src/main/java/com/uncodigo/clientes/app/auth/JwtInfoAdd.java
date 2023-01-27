package com.uncodigo.clientes.app.auth;

import com.uncodigo.clientes.app.models.entity.User;
import com.uncodigo.clientes.app.models.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtInfoAdd implements TokenEnhancer {

    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        User user = usuarioService.findByUsername(authentication.getName());

        Map<String, Object> info = new HashMap<>();
        info.put("id", user.getId() );
        info.put("email", user.getEmail() );
        info.put("username", user.getUsername() );

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);

        return accessToken;
    }
}
