package controllers.account.settings;


import java.net.MalformedURLException;

import models.User;
import play.Logger;
import play.i18n.Messages;
import play.mvc.Result;
import play.mvc.Security;

import com.ecommerce.model.dao.TokenDao;

import controllers.Application;
import controllers.Secured;

/**
 * @author samir
 */
@Security.Authenticated(Secured.class)
public class Password extends Application {


    /**
     * Send a mail with the reset link.
     *
     * @return password page with flash error or success
     */
    public static Result runPassword() {
        User user = User.findByEmail(request().username());
        try {
            TokenDao.sendMailResetPassword(user);
            flash("success", Messages.get("resetpassword.mailsent"));
            return jsonResponse(Messages.get("resetpassword.mailsent"),200);
        } catch (MalformedURLException e) {
            Logger.error("Cannot validate URL", e);
            flash("error", Messages.get("error.technical"));
        }
        return jsonResponse(Messages.get("error.technical"),200);
    }
}
