/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.hibernate.Session;

/**
 *
 * @author DIGITACAOFUND
 */
public class PhaseListenerSGA implements PhaseListener {

    @Override
    public void beforePhase(PhaseEvent phase) {
        if (phase.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("session", session);
        }
    }

    @Override
    public void afterPhase(PhaseEvent phase) {
        if (phase.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
            Session session = (Session) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("session");

            if (session.isOpen()) {
                try {
                    session.getTransaction().commit();
                } catch (Exception e) {
                    if (session.getTransaction().isActive()) {
                        session.getTransaction().rollback();
                    }
                } finally {
                    if (session.isOpen()) {
                        session.close();
                    }
                }
            }
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
