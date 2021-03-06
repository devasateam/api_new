package controllers;

import java.util.Map;

import com.accounts.service.AuthenticationService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import models.account.utils.AppException;
import play.Logger;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.UrlConstants;

/**
 * Login and Logout.
 * @author samir
 */
public class Application extends Controller {

    public static Result GO_HOME = redirect(
    			UrlConstants.HOME
    );

    public static Result GO_DASHBOARD = redirect(
            UrlConstants.DASH_BOARD
    );

    /**
     * Display the login page or dashboard if connected
     *
     * @return login page or dashboard
     */
    public static Result index() {
        // Check that the email matches a confirmed user before we redirect
        String email = ctx().session().get("email");
        if (email != null) {
            User user = User.findByEmail(email);
            if (user != null && user.validated) {
                return GO_DASHBOARD;
            } else {
                Logger.debug("Clearing invalid session credentials");
                session().clear();
            }
        }

        return GO_HOME;
    }

    /**
     * Handle login form submission.
     *
     * @return Dashboard if auth OK or login form if auth KO
     * @throws AppException 
     */
    public static Result authenticate(String email , String password) throws AppException {
    	Map<String, String[]> parameters = request().body().asFormUrlEncoded();
//    	String email = parameters.get("email")[0];
//        String password = parameters.get("password")[0];
    	ObjectNode result = Json.newObject();
    	AuthenticationService authenticationService = new AuthenticationService();
        if (email==null || password==null) {
            return ok(result.put("message",authenticationService.authenticateUser(email, password)));
        } else {
            session("email",email);
            return ok(UrlConstants.DASH_BOARD);
        }
    }

    /**
     * Logout and clean the session.
     *
     * @return Index page
     */
    public static Result logout() {
        session().clear();
        flash("success", Messages.get("youve.been.logged.out"));
        return GO_HOME;
    }
    
}