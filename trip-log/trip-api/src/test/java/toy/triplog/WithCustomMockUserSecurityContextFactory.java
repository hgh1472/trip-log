package toy.triplog;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import toy.triplog.domain.auth.ApiUser;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        long id = annotation.id();
        String role = annotation.role();

        ApiUser apiUser = new ApiUser(id);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(apiUser, null, List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
        return context;
    }

}
