package ruslan.simakov.resourceserver.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruslan.simakov.resourceserver.model.UserRest;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/status")
    public String getStatus() {
        return "Working...";
    }

    @PreAuthorize("hasAuthority('ROLE_KILLER') or #id == #jwt.subject")
//    @Secured("ROLE_KILLER")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id,
                             @AuthenticationPrincipal Jwt jwt) {
        return "User deleted: " + id + " and JWT subject: " + jwt.getSubject();
    }

    @PostAuthorize("returnObject.id == #jwt.subject")
    @GetMapping("/{id}")
    public UserRest getUser(@PathVariable String id,
                             @AuthenticationPrincipal Jwt jwt) {
        return new UserRest(id);
    }
}
