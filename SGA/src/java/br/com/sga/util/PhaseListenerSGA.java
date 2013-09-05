/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.util;

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
        System.out.println("Entrou no PhaseListener...");
        if (phase.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            FacesContextUtil.setRequestSession(session);
            System.out.println("Abriu a sessão e setou no FacesContext.");
        }
    }

    @Override
    public void afterPhase(PhaseEvent phase) {
        System.out.println("Entrou no PhaseListener (depois)...");
        if (phase.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
            Session session = FacesContextUtil.getRequestSession();

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
