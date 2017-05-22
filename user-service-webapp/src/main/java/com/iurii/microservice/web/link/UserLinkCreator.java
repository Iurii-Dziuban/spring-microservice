package com.iurii.microservice.web.link;

import com.iurii.microservice.web.UserController;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by iurii.dziuban on 22/05/2017.
 */
@Service
public class UserLinkCreator {
    public Link getLink(String id) {
        return linkTo(methodOn(UserController.class).get(id)).withSelfRel();
    }
}
