/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.fotoherfert4school_admin.others;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 20100226
 */
public class SessionPhaseListener implements PhaseListener {

//    private static final String homepageSchueler = "xhtml/schueler/schuelerLogin.xhtml";
    private static final String homepageAdmin = "adminLogin.xhtml";

    @Override
    public void afterPhase(PhaseEvent event) {
        // Do nothing
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        ExternalContext ext = context.getExternalContext();

        HttpServletRequest rq = (HttpServletRequest) ext.getRequest();
        String ref = rq.getHeader("referer");
        HttpSession session = (HttpSession) ext.getSession(false);
        boolean newSession = (session == null) || (session.isNew());
        if (newSession && ref != null) {
            Application app = context.getApplication();
            ViewHandler viewHandler = app.getViewHandler();

            String homepage = "";
//            String homepage = homepageSchueler;
            if (ref.contains("admin")) {
                homepage = homepageAdmin;
            }
            UIViewRoot view = viewHandler.createView(context, "/" + homepage);
            context.setViewRoot(view);
            context.renderResponse();
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
